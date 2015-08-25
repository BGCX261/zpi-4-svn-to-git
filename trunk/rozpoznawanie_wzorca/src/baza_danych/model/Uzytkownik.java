package baza_danych.model;

import java.util.HashSet;
import java.util.Set;

import algorytm.HashUzytkownika;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

public class Uzytkownik {
  private int id;
  private Set<HashUzytkownika> hashe;

  @Inject
  public Uzytkownik(@Assisted int id) {
    this.id = id;
    hashe = new HashSet<HashUzytkownika>();
  }

  public void dodajHash(HashUzytkownika e) {
    hashe.add(e);
  }

  public int getId() {
    return id;
  }

  public Set<HashUzytkownika> pobierzHashe() {
    return hashe;
  }
}
