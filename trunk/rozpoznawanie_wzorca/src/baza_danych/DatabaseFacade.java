package baza_danych;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Handler;
import java.util.logging.Logger;

import logging.BasicLogger;
import logging.MySqlHandler;
import logging.MySqlLogFormatter;
import algorytm.HashUzytkownika;
import baza_danych.model.GrafCzytnikow;
import baza_danych.model.TabelaGrafow;
import baza_danych.model.Uzytkownicy;
import baza_danych.model.Uzytkownik;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import czytnik.DaneCzytnika;

/**
 * Fasada dajaca ujednolicony dostep do bazy danych i ukrywajaca wewnetrzna
 * implementacje java.sql
 * 
 * @author elistan
 * 
 */
@Singleton
public class DatabaseFacade {
  private ConnectionFactory connFactory;
  private Logger logger = BasicLogger.applicationLogger;
  private Uzytkownicy uzytkownicy;
  private TabelaGrafow tabelaGrafow;

  @Inject
  public DatabaseFacade(ConnectionFactory factory, Uzytkownicy uzytkownicy,
      TabelaGrafow tabelaGrafow) {
    this.connFactory = factory;
    this.uzytkownicy = uzytkownicy;
    this.tabelaGrafow = tabelaGrafow;
  }

  private void closeIgnoringException(Connection connection) {
    if (connection != null) {
      try {
        connection.close();
      } catch (SQLException e) {
      }
    }
  }

  public Handler createSqlHandler() {
    Connection connection;
    try {
      connection = connFactory.getConnection();
    } catch (SQLException e) {
      throw new RuntimeException("Nie mozna polaczyc sie z baza danych", e);
    }
    Handler handler = new MySqlHandler(connection);
    handler.setFormatter(new MySqlLogFormatter(connection));
    return handler;
  }

  public boolean sprawdzDostepUzytkownikZCzytnikiem(Uzytkownik uzytkownik,
      DaneCzytnika czytnik) {
    Connection connection = null;
    try {
      connection = connFactory.getConnection();
      if (connection == null)
        return false;
      PreparedStatement ps = connection
          .prepareStatement(przygotujZapytanieUzytkownikICzytnik());
      ps.setInt(1, uzytkownik.getId());
      ps.setInt(2, czytnik.getId());
      ResultSet set = ps.executeQuery();
      return set.first();
    } catch (SQLException e) {
      final String sourceMethod = "SprawdzDostepHashZCzytnikiem";
      logException(e, sourceMethod);
    } finally {
      closeIgnoringException(connection);
    }
    return false;
  }

  private void logException(SQLException e, final String sourceMethod) {
    logger.throwing(this.getClass().getCanonicalName(), sourceMethod, e);
  }

  private String przygotujZapytanieUzytkownikICzytnik() {
    StringBuilder sb = new StringBuilder();
    sb.append("SELECT * FROM uzytkownik uzy ")
        .append("LEFT JOIN grupa_uzytkownik gu ON gu.uzytkownik_id = uzy.id ")
        .append("LEFT JOIN grupa gru ON gru.id = gu.grupa_id ")
        .append("AND gru.aktywna = 1 ")
        .append("LEFT JOIN grupa_czytnik gc ON gc.grupa_id = gu.grupa_id ")
        .append("INNER JOIN czytnik czy ON czy.id = gc.czytnik_id ")
        .append("AND czy.aktywny = 1 ")
        .append(
            "AND czy.id NOT IN ( SELECT czytnik_id cid FROM czytnik_uzytkownik WHERE uzytkownik_id = uzy.id AND blacklist = 1) ")
        .append(
            "OR czy.id IN ( SELECT czytnik_id cid FROM czytnik_uzytkownik WHERE uzytkownik_id = uzy.id AND blacklist = 0) ")
        .append("where uzy.aktywny = 1 AND uzy.usuniety = 0 ")
        .append("AND uzy.id = ? AND czy.id = ?;");
    return sb.toString();
  }

  public List<Uzytkownik> pobierzUzytkownikow() {
    try {
      List<Uzytkownik> uzytkownicyBezHashy = uzytkownicy.pobierzUzytkownikow();
      List<Uzytkownik> gotowiUzytkownicy = uzytkownicy
          .wypelnijUzytkownikowHashami(uzytkownicyBezHashy);
      return gotowiUzytkownicy;
    } catch (SQLException e) {
      logException(e, "pobierzUzytkownikow");
      return new ArrayList<Uzytkownik>();
    }
  }

  public boolean dodajUzytkownikowiMetodeAutoryzacji(int idUzytkownika,
      HashUzytkownika hash) {
    Connection connection = null;
    try {
      connection = connFactory.getConnection();
      PreparedStatement statement = connection
          .prepareStatement(przygotujZapytanieDodajaceOdciskPalca());
      statement.setString(1, hash.toString());
      statement.setInt(2, idUzytkownika);
      int rowCount = statement.executeUpdate();
      return rowCount > 0;
    } catch (SQLException e) {
      logException(e, "dodajUzytkownikowiMetodeAutoryzacji");
    } finally {
      closeIgnoringException(connection);
    }
    return false;
  }

  private String przygotujZapytanieDodajaceOdciskPalca() {
    return "INSERT INTO odcisk_palca(hash, uzytkownik_id) VALUES(?, ?);";
  }

  public GrafCzytnikow pobierzGrafCzytnikow() {
    try {
      return tabelaGrafow.pobierzGraf();
    } catch (SQLException e) {
      logException(e, "pobierzGrafCzytnikow");
      throw new RuntimeException("Nieudane odczytanie grafu", e);
    }
  }

  public int znajdzPoprzedniCzytnik(Uzytkownik uzytkownik) {
    Connection connection = null;
    try {
      connection = connFactory.getConnection();
      String zapytanie = "SELECT czytnik_id FROM log_aktywnosci WHERE uzytkownik_id = ? ORDER BY czas DESC LIMIT 1";
      PreparedStatement statement = connection.prepareStatement(zapytanie);
      statement.setInt(1, uzytkownik.getId());
      ResultSet set = statement.executeQuery();
      if (!set.first())
        return 0;
      return set.getInt(1);
    } catch (SQLException e) {
      logException(e, "znajdzPoprzedniCzytnik");
      throw new RuntimeException("Nieudany odczyt z bazy danych", e);
    } finally {
      closeIgnoringException(connection);
    }
  }

  public boolean zapiszGrafDoBazy(GrafCzytnikow graf) {
    try {
      return tabelaGrafow.dodajGraf(graf);
    } catch (SQLException e) {
      logException(e, "zapiszGrafDoBazy");
      return false;
    }
  }
}
