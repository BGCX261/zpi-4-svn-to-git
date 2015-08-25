package baza_danych.model;

import java.sql.BatchUpdateException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import baza_danych.ConnectionFactory;

import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class TabelaGrafow {
  private ConnectionFactory connectionFactory;
  private GrafCzytnikowFactory grafFactory;

  @Inject
  public TabelaGrafow(ConnectionFactory connectionFactory,
      GrafCzytnikowFactory grafFactory) {
    this.connectionFactory = connectionFactory;
    this.grafFactory = grafFactory;
  }

  public GrafCzytnikow pobierzGraf() throws SQLException {
    List<ParaCzytnikow> pary = new ArrayList<ParaCzytnikow>();
    Connection connection = connectionFactory.getConnection();
    PreparedStatement statement = connection
        .prepareStatement(przygotujZapytanie());
    ResultSet wyniki = statement.executeQuery();
    while (wyniki.next()) {
      int pierwszyCzytnik = wyniki.getInt(1);
      int drugiCzytnik = wyniki.getInt(2);
      ParaCzytnikow para = new ParaCzytnikow(pierwszyCzytnik, drugiCzytnik);
      pary.add(para);
    }
    return grafFactory.utworzGraf(pary);
  }

  private String przygotujZapytanie() {
    return "SELECT * from graf_czytnikow;";
  }

  public boolean dodajGraf(GrafCzytnikow graf) throws SQLException {
    Connection connection = connectionFactory.getConnection();
    connection.setAutoCommit(false);
    PreparedStatement statement = connection
        .prepareStatement("DELETE from graf_czytnikow;");
    statement.executeUpdate();
    statement.close();
    statement = connection
        .prepareStatement("INSERT INTO graf_czytnikow (czytnik1, czytnik2) VALUES (?, ?);");
    List<ParaCzytnikow> pary = graf.przygotujParyCzytnikow();
    for (ParaCzytnikow para : pary) {
      statement.setInt(1, para.pierwszyCzytnik);
      statement.setInt(2, para.drugiCzytnik);
      statement.addBatch();
    }
    try {
      statement.executeBatch();
    } catch (BatchUpdateException e) {
      return false;
    }
    connection.commit();
    connection.close();
    return true;
  }
}
