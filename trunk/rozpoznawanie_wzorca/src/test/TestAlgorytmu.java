package test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.logging.Level;

import logging.BasicLogger;
import socket.SocketModule;
import socket.SocketServer;
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

import czytnik.CzytnikAutoryzujacyModule;
import czytnik.ObserwatorCzytnika;

public class TestAlgorytmu {
  Algorytm algorytm;
  ObserwatorCzytnika obserwator;
  private List<Uzytkownik> hashe;
  private DatabaseFacade db;

  @Inject
  public TestAlgorytmu(Algorytm algorytm, ObserwatorCzytnika obserwator,
      DatabaseFacade db) {
    this.algorytm = algorytm;
    this.obserwator = obserwator;
    this.db = db;
  }

  private void testuj(PrintWriter writer) throws InterruptedException {
    HashUzytkownika hash = obserwator.getHash();

    Uzytkownik uzytkownik = null;
    int idUzytkownika;
    try {
      hashe = db.pobierzUzytkownikow();
      uzytkownik = algorytm.znajdzNajpodobniejszegoUzytkownika(hash, hashe);
      idUzytkownika = uzytkownik.getId();
    } catch (NoSuchElementException e) {
      idUzytkownika = -1;
    }
    writer.println(idUzytkownika);
    writer.flush();
  }

  public static void main(String args[]) throws FileNotFoundException,
      InterruptedException {
    double threshold = args.length > 0 ? Double.parseDouble(args[0]) : 2.0;
    Injector injector = Guice.createInjector(new AuthorizationModule(),
        new CzytnikAutoryzujacyModule(), new SocketModule(),
        new AlgorytmModule(threshold), new ServicesModule(),
        new BazaDanychModule());
    SocketServer serwer = injector.getInstance(SocketServer.class);
    Thread t = new Thread(serwer);
    t.setDaemon(true);
    t.start();
    TestAlgorytmu test = injector.getInstance(TestAlgorytmu.class);
    BasicLogger.applicationLogger.setLevel(Level.ALL);
    PrintWriter resultWriter = new PrintWriter(new File("Results.txt"));
    while (true) {
      test.testuj(resultWriter);
    }
  }
}
