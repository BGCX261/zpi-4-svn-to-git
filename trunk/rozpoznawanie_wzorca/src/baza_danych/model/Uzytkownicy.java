package baza_danych.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import algorytm.HashUzytkownika;
import algorytm.HashUzytkownikaFactory;
import baza_danych.ConnectionFactory;

import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class Uzytkownicy {
  private ConnectionFactory connectionFactory;
  private UzytkownikFactory uzytkownikBuilder;
  private HashUzytkownikaFactory hashFactory;

  @Inject
  public Uzytkownicy(ConnectionFactory connectionFactory,
      UzytkownikFactory uzytkownikBuilder, HashUzytkownikaFactory hashFactory) {
    this.connectionFactory = connectionFactory;
    this.uzytkownikBuilder = uzytkownikBuilder;
    this.hashFactory = hashFactory;
  }

  public List<Uzytkownik> pobierzUzytkownikow() throws SQLException {
    List<Uzytkownik> uzytkownicy = new ArrayList<Uzytkownik>();
    String zapytaniePobierzUzytkownikow = "SELECT id FROM uzytkownik WHERE aktywny = 1 AND usuniety = 0";
    Connection connection = connectionFactory.getConnection();
    PreparedStatement pobieranieUzytkownikow = connection
        .prepareStatement(zapytaniePobierzUzytkownikow);
    ResultSet znalezieniUzytkownicy = pobieranieUzytkownikow.executeQuery();
    if (!znalezieniUzytkownicy.first()) {
      connection.close();
      return uzytkownicy;
    }
    do {
      Uzytkownik uzytkownik = uzytkownikBuilder
          .stworzUzytkownika(znalezieniUzytkownicy.getInt(1));
      uzytkownicy.add(uzytkownik);
    } while (znalezieniUzytkownicy.next());
    connection.close();
    return uzytkownicy;
  }

  public List<Uzytkownik> wypelnijUzytkownikowHashami(
      List<Uzytkownik> uzytkownicy) throws SQLException {
    List<Uzytkownik> wypelnieniUzytkownicy = new ArrayList<Uzytkownik>();
    if (uzytkownicy.size() == 0)
      return wypelnieniUzytkownicy;
    Connection connection = null;
    connection = connectionFactory.getConnection();
    if (connection == null)
      return wypelnieniUzytkownicy;
    PreparedStatement wyrazenieZbierajaceHashe = przygotujKwerendeSzukajacaHashyDlaUzytkownikow(
        connection, uzytkownicy);
    ResultSet wyniki = wyrazenieZbierajaceHashe.executeQuery();
    if (!wyniki.first())
      return wypelnieniUzytkownicy;
    do {
      int indeksUzytkownika = wyniki.getInt(1) - 1;
      HashUzytkownika hash = hashFactory.createHashUzytkownika(wyniki
          .getString(2));
      uzytkownicy.get(indeksUzytkownika).dodajHash(hash);
    } while (wyniki.next());
    for (Uzytkownik uzytkownik : uzytkownicy) {
      if (!uzytkownik.pobierzHashe().isEmpty())
        wypelnieniUzytkownicy.add(uzytkownik);
    }
    connection.close();
    return wypelnieniUzytkownicy;
  }

  private PreparedStatement przygotujKwerendeSzukajacaHashyDlaUzytkownikow(
      Connection connection, List<Uzytkownik> uzytkownicy) throws SQLException {
    String podstawa = "SELECT uzytkownik_id, hash FROM odcisk_palca WHERE aktywny = 1 AND uzytkownik_id in (";
    String sortowanie = ") ORDER BY uzytkownik_id;";
    int liczbaUzytkownikow = uzytkownicy.size();
    StringBuilder zapytanie = new StringBuilder(podstawa.length()
        + sortowanie.length() + liczbaUzytkownikow * 3);
    zapytanie.append(podstawa);
    for (int i = 0; i < liczbaUzytkownikow; i++) {
      zapytanie.append("?, ");
    }
    zapytanie.replace(zapytanie.length() - 2, zapytanie.length(), "");
    zapytanie.append(sortowanie);
    PreparedStatement wyrazenie = connection.prepareStatement(zapytanie
        .toString());
    for (int i = 1; i <= liczbaUzytkownikow; i++) {
      Uzytkownik u = uzytkownicy.get(i - 1);
      wyrazenie.setInt(i, u.getId());
    }
    return wyrazenie;
  }
}
