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

import czytnik.ModulCzytnikaAutoryzujacy;
import czytnik.ObserwatorCzytnika;

public class TestAlgorytmu {
  ReadService service;
  Algorytm algorytm;
  ObserwatorCzytnika obserwator;
  private List<HashUzytkownika> hashe;

  @Inject
  public TestAlgorytmu(ReadService service, Algorytm algorytm,
      ObserwatorCzytnika obserwator) {
    this.algorytm = algorytm;
    this.service = service;
    this.obserwator = obserwator;
    hashe = service.pobierzUzytkownikow();
  }

  private void testuj(PrintWriter writer) throws InterruptedException {
    HashUzytkownika hash = obserwator.getHash();

    int index = 0;
    try {
      index = algorytm.znajdzNajpodobniejszy(hash, hashe);
      index = service.pobierzIdUzytkownika(hashe.get(index));
    } catch (NoSuchElementException e) {
      index = -1;
    }
    writer.println("Dopasowano - " + index);
    writer.flush();
  }

  public static void main(String args[]) throws FileNotFoundException,
      InterruptedException {
    double threshold = args.length > 0 ? Double.parseDouble(args[0]) : 2.0;
    Injector injector = Guice.createInjector(new AuthorizationModule(),
        new ModulCzytnikaAutoryzujacy(), new SocketModule(),
        new AlgorytmModule(threshold), new ServicesModule(),
        new ModulBazyDanych());
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
