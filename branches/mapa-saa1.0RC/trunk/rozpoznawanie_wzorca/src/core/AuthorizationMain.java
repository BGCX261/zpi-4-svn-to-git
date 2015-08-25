package core;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import socket.SocketModule;
import socket.SocketServer;
import uslugi.AuthorizationService;
import uslugi.ReadService;
import uslugi.ServicesModule;
import algorytm.Algorytm;
import algorytm.AlgorytmModule;
import algorytm.HashUzytkownika;
import autoryzacja.AuthorizationModule;
import baza_danych.ModulBazyDanych;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.name.Named;

import czytnik.DaneCzytnika;
import czytnik.ModulCzytnikaAutoryzujacy;
import czytnik.ObserwatorCzytnika;

/**
 * Klasa pozwalajaca na uruchomienie aplikacji klienckiej w trybie autoryzacji
 * 
 * @author elistan
 * 
 */
public class AuthorizationMain {
  ObserwatorCzytnika obserwator;
  ReadService service;
  Algorytm algorytm;
  AuthorizationService ras;
  private final Logger LOGGER;

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
  public AuthorizationMain(ObserwatorCzytnika obserwator, ReadService service,
      Algorytm algorytm, AuthorizationService authService, @Named("SystemAccessLogger") Logger sal) {
    this.obserwator = obserwator;
    this.service = service;
    this.algorytm = algorytm;
    this.ras = authService;
    this.LOGGER = sal;
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
  public static void main(String[] args) throws InterruptedException {

    Injector injector = Guice.createInjector(new AuthorizationModule(),
        new ModulCzytnikaAutoryzujacy(), new SocketModule(),
        new AlgorytmModule(), new ServicesModule(), new ModulBazyDanych());
    SocketServer serwer = injector.getInstance(SocketServer.class);
    Thread t = new Thread(serwer);
    t.setDaemon(true);
    t.start();
    AuthorizationMain am = injector.getInstance(AuthorizationMain.class);
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
    List<HashUzytkownika> hashe = service.pobierzUzytkownikow();
    int index = -1;
    try {
      index = algorytm.znajdzNajpodobniejszy(hash, hashe);
    } catch (NoSuchElementException e) {
      Object[] params = new Object[2];
      params[0] = null;
      params[1] = dane.getId();
      System.out.println(dane.getId());
      LogRecord logRec = new LogRecord(Level.WARNING,
          "ACCESS DENIED; CAUSE: matching fingerprint not found");
      logRec.setParameters(params);
      LOGGER.log(logRec);
      return false;
    }
    return ras.autoryzuj(dane, hashe.get(index));
  }
}
