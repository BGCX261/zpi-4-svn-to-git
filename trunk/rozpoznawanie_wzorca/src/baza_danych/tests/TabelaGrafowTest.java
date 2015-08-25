package baza_danych.tests;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.Matchers.eq;

import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;

import baza_danych.ConnectionFactory;
import baza_danych.model.GrafCzytnikow;
import baza_danych.model.GrafCzytnikowFactory;
import baza_danych.model.ParaCzytnikow;
import baza_danych.model.TabelaGrafow;

public class TabelaGrafowTest {
  private static final String KWERENDA_INSERT = "INSERT INTO graf_czytnikow (czytnik1, czytnik2) VALUES (?, ?);";
  private static final String KWERENDA_USUWAJACA = "DELETE from graf_czytnikow;";
  private static final String KWERENDA_POBIERAJACA_GRAF = "SELECT * from graf_czytnikow;";
  ConnectionFactory connectionFactory;
  PreparedStatement preparedStatement;
  Connection connection;
  ResultSet resultSet;
  TabelaGrafow tabela;
  GrafCzytnikowFactory grafFactory;
  List<ParaCzytnikow> lista;

  @Before
  public void setUp() throws Exception {
    createMocks();
    lista = new ArrayList<ParaCzytnikow>();
    when(connectionFactory.getConnection()).thenReturn(connection);
  }

  private void createMocks() {
    connectionFactory = mock(ConnectionFactory.class);
    connection = mock(Connection.class);
    preparedStatement = mock(PreparedStatement.class);
    resultSet = mock(ResultSet.class);
    grafFactory = mock(GrafCzytnikowFactory.class);
  }

  @Test
  public void testPobierzGrafPassJednaPara() throws SQLException {
    when(connection.prepareStatement(KWERENDA_POBIERAJACA_GRAF)).thenReturn(
        preparedStatement);
    when(preparedStatement.executeQuery()).thenReturn(resultSet);
    when(resultSet.next()).thenReturn(true).thenReturn(false);
    when(resultSet.getInt(eq(1))).thenReturn(1);
    when(resultSet.getInt(eq(2))).thenReturn(3);
    lista.add(new ParaCzytnikow(1, 3));
    tabela = new TabelaGrafow(connectionFactory, grafFactory);

    tabela.pobierzGraf();
    verify(grafFactory).utworzGraf(eq(lista));
  }

  @Test
  public void testZapiszGrafJednaPara() throws SQLException {
    GrafCzytnikow graf = new GrafCzytnikow(
        Collections.singletonList(new ParaCzytnikow(1, 3)));
    when(connection.prepareStatement(KWERENDA_USUWAJACA)).thenReturn(
        preparedStatement);
    PreparedStatement statementInsert = mock(PreparedStatement.class);
    when(connection.prepareStatement(KWERENDA_INSERT)).thenReturn(
        statementInsert);
    tabela = new TabelaGrafow(connectionFactory, grafFactory);
    tabela.dodajGraf(graf);

    verify(preparedStatement).executeUpdate();
    verify(connection).setAutoCommit(false);
    verify(connection).commit();
    verify(statementInsert).setInt(1, 1);
    verify(statementInsert).setInt(2, 3);
    verify(statementInsert).addBatch();
    verify(statementInsert).executeBatch();
    verify(connection).close();
  }

  @Test
  public void testZapiszGrafTrzyPary() throws SQLException {
    GrafCzytnikow graf = new GrafCzytnikow(Arrays.asList(
        new ParaCzytnikow(1, 3), new ParaCzytnikow(2, 2), new ParaCzytnikow(4,
            5)));
    when(connection.prepareStatement(KWERENDA_USUWAJACA)).thenReturn(
        preparedStatement);
    PreparedStatement statementInsert = mock(PreparedStatement.class);
    when(connection.prepareStatement(KWERENDA_INSERT)).thenReturn(
        statementInsert);
    tabela = new TabelaGrafow(connectionFactory, grafFactory);
    tabela.dodajGraf(graf);

    verify(preparedStatement).executeUpdate();
    verify(connection).setAutoCommit(false);
    verify(connection).commit();
    verify(statementInsert).setInt(1, 1);
    verify(statementInsert).setInt(2, 3);
    verify(statementInsert).setInt(1, 2);
    verify(statementInsert).setInt(2, 2);
    verify(statementInsert).setInt(1, 4);
    verify(statementInsert).setInt(2, 5);
    verify(statementInsert, times(3)).addBatch();
    verify(statementInsert).executeBatch();
    verify(connection).close();
  }
}
