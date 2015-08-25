package core;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import socket.SocketModule;
import uslugi.ServicesModule;
import algorytm.AlgorytmModule;
import autoryzacja.AuthorizationModule;
import baza_danych.BazaDanychModule;
import baza_danych.DatabaseFacade;
import baza_danych.model.GrafCzytnikow;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;

import czytnik.CzytnikAutoryzujacyModule;

public class WczytywanieRegul {
  DatabaseFacade db;
  GrafCzytnikow graf;

  @Inject
  public WczytywanieRegul(DatabaseFacade db) {
    this.db = db;
  }

  void wczytajPlikDoGrafu(String plik) throws IOException {
    BufferedReader reader = new BufferedReader(new FileReader(new File(plik)));
    graf = new GrafCzytnikow(reader);
    reader.close();
  }

  void zapiszDoBazy() {
    db.zapiszGrafDoBazy(graf);
  }

  /**
   * @param args
   */
  public static void main(String[] args) throws IOException {
    String plik;
    if (args.length == 1)
      plik = args[0];
    else
      plik = "czytniki.txt";
    Injector injector = Guice.createInjector(new AuthorizationModule(),
        new CzytnikAutoryzujacyModule(), new SocketModule(),
        new AlgorytmModule(), new ServicesModule(), new BazaDanychModule());
    WczytywanieRegul reguly = injector.getInstance(WczytywanieRegul.class);
    reguly.wczytajPlikDoGrafu(plik);
    reguly.zapiszDoBazy();
  }

}
