package core;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import javax.swing.SwingUtilities;

import socket.SocketModule;
import socket.SocketServer;
import uslugi.AuthorizationService;
import uslugi.ServicesModule;
import algorytm.Algorytm;
import algorytm.AlgorytmModule;
import algorytm.HashUzytkownika;
import autoryzacja.AuthorizationModule;
import baza_danych.BazaDanychModule;
import baza_danych.DatabaseFacade;
import baza_danych.model.Uzytkownik;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.name.Named;

import czytnik.CzytnikAutoryzujacyModule;
import czytnik.DaneCzytnika;
import czytnik.ObserwatorCzytnika;

/**
 * Klasa pozwalajaca na uruchomienie aplikacji klienckiej w trybie autoryzacji
 * 
 * @author elistan
 * 
 */
public class AuthorizationMain {
  ObserwatorCzytnika obserwator;
  Algorytm algorytm;
  AuthorizationService authorizationService;
  private final Logger LOGGER;
  private DatabaseFacade db;
  private Ramka ramka;

  /**
   * 
   * @param obserwator
   *          Obserwator czytnika linii papilarnych
   * @param service
   *          Serwis odczytu z db dla metod autoryzacji
   * @param algorytm
   *          Algorytm przetwarzania wzorca
   * @param authService
   *          Serwis autoryzujacy
   * @param log
   *          Usługa logowania
   */
  @Inject
  public AuthorizationMain(ObserwatorCzytnika obserwator, Algorytm algorytm,
      AuthorizationService authService,
      @Named("SystemAccessLogger") Logger sal, DatabaseFacade db, Ramka ramka) {
    this.obserwator = obserwator;
    this.algorytm = algorytm;
    this.authorizationService = authService;
    this.LOGGER = sal;
    this.db = db;
    this.ramka = ramka;
  }

  /**
   * Uruchomienie serwisu
   * 
   * @param args
   *          Argumenty konsolowe - na tym etapie pomijane
   * @throws InterruptedException
   *           Przerwanie zablokowanego odczytu. Na tym poziomie mozna wylaczyc
   *           aplikacje
   */
  public static void main(String[] args) throws InterruptedException,
      InvocationTargetException {

    Injector injector = Guice.createInjector(new AuthorizationModule(),
        new CzytnikAutoryzujacyModule(), new SocketModule(),
        new AlgorytmModule(), new ServicesModule(), new BazaDanychModule());
    SocketServer serwer = injector.getInstance(SocketServer.class);
    Thread t = new Thread(serwer);
    t.setDaemon(true);
    t.start();
    final AuthorizationMain am = injector.getInstance(AuthorizationMain.class);
    SwingUtilities.invokeAndWait(new Runnable() {

      @Override
      public void run() {
        am.ramka.uruchom();
      }
    });
    while (true) {
      try {
        System.out.println(am.oczekuj() ? "Acces granted!" : "Acces denied!");
      } catch (InterruptedException e) {
        System.out.println("Brak autoryzacji - wyjatek");
      }
    }
  }

  /**
   * Oczekuje na nadchodzace odciski palca i przekazuje je do weryfikacji
   * 
   * @return Informacja czy autoryzacja jest pomyslna
   * @throws InterruptedException
   *           Przerwano oczekiwanie
   */
  public boolean oczekuj() throws InterruptedException {
    HashUzytkownika hash = obserwator.getHash();
    DaneCzytnika dane = obserwator.getDane();
    // nadpisanie danych czytnika danymi z ramki.
    dane.setId(ramka.pobierzWybraneIdCzytnika());
    List<Uzytkownik> uzytkownicy = db.pobierzUzytkownikow();
    Uzytkownik uzytkownik;
    try {
      uzytkownik = algorytm.znajdzNajpodobniejszegoUzytkownika(hash,
          uzytkownicy);
    } catch (NoSuchElementException e) {
      logIllegaLaccess(dane);
      return false;
    }
    boolean wynik = authorizationService.autoryzuj(dane, uzytkownik);
    ramka.uaktualnijStatus(wynik,
        authorizationService.pobierzOstatniKomunikat());
    return wynik;
  }

  private void logIllegaLaccess(DaneCzytnika dane) {
    Object[] params = new Object[2];
    params[0] = null;
    params[1] = dane.getId();
    final String komunikat = "ACCESS DENIED; CAUSE: matching fingerprint not found";
    LogRecord logRec = new LogRecord(Level.WARNING, komunikat);
    logRec.setParameters(params);
    LOGGER.log(logRec);
    ramka.uaktualnijStatus(false, komunikat);
  }
}
