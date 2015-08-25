package baza_danych.model;

import java.io.BufferedReader;
import java.util.List;

public interface GrafCzytnikowFactory {
  GrafCzytnikow utworzGraf(List<ParaCzytnikow> pary);

  GrafCzytnikow wczytajPlik(BufferedReader plik);
}
