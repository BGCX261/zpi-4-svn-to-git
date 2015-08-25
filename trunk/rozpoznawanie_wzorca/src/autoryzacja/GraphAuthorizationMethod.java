package autoryzacja;

import baza_danych.DatabaseFacade;
import baza_danych.model.GrafCzytnikow;
import baza_danych.model.Uzytkownik;

import com.google.inject.Inject;

import czytnik.DaneCzytnika;

public class GraphAuthorizationMethod implements AuthorizationMethod {
  DatabaseFacade db;

  @Inject
  public GraphAuthorizationMethod(DatabaseFacade db) {
    this.db = db;
  }

  @Override
  public boolean verify(Uzytkownik hash, DaneCzytnika czytnik) {
    GrafCzytnikow graf = db.pobierzGrafCzytnikow();
    if (graf == null)
      return false;
    int poprzedniCzytnik = db.znajdzPoprzedniCzytnik(hash);
    if (poprzedniCzytnik == 0)
      return true;
    return graf.czyDozwolonePrzejscie(czytnik.getId(), poprzedniCzytnik);
  }

  @Override
  public String getErrorMessage() {
    return "Niedozwolone przejście między czytnikami.";
  }

}
