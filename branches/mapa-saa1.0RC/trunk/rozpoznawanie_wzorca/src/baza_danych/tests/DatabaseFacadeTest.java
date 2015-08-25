package baza_danych.tests;

import java.sql.Connection;
import java.sql.SQLException;

import junit.framework.Assert;

import static org.junit.Assert.*;

import static org.mockito.Matchers.*;

import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mockito;

import algorytm.HashUzytkownikaFactory;
import baza_danych.ConnectionFactory;
import baza_danych.DatabaseFacade;

import com.mysql.jdbc.PreparedStatement;

public class DatabaseFacadeTest {

  private ConnectionFactory connectionFactory;
  private HashUzytkownikaFactory hashFactory;
  private DatabaseFacade db;
  private PreparedStatement preparedStatement;
  private Connection connection;
  private String sqlQuery;

  @Before
  public void setUp() throws Exception {
    prepareConnectionFactory();
    hashFactory = mock(HashUzytkownikaFactory.class);
    db = new DatabaseFacade(connectionFactory, hashFactory);
  }

  private void prepareConnectionFactory() throws SQLException {
    sqlQuery = "Query";
    connectionFactory = mock(ConnectionFactory.class);
    connection = mock(Connection.class);
    preparedStatement = mock(PreparedStatement.class);
    when(connectionFactory.getConnection()).thenReturn(connection);
    when(connection.prepareStatement(eq(sqlQuery))).thenReturn(
        preparedStatement);
  }

  @Test
  public void testWyslijKwerendeUpdate() {
    int result = db.wyslijKwerendeUpdate(sqlQuery);
    try {
      verify(preparedStatement).executeUpdate();
      assertEquals(0, result);
      verify(connection).close();
    } catch (SQLException e) {
      failAfterException(e);
    }
  }

  private void failAfterException(Exception e) {
    fail("Exception: " + e.getLocalizedMessage());
  }

  @Test
  public void testWyslijKwerendeUpdateWyjatekNiePolaczono() {
    try {
      when(connectionFactory.getConnection()).thenThrow(new SQLException());
    } catch (SQLException e) {
      failAfterException(e);
    }
    int result = db.wyslijKwerendeUpdate(sqlQuery);
    assertEquals(0, result);
  }

  @Test
  public void testWyslijKwerendeUpdateWyjatekNieWyslanoStatement() {
    try {
      when(preparedStatement.executeUpdate()).thenThrow(new SQLException());
    } catch (SQLException e) {
      failAfterException(e);
    }
    int result = db.wyslijKwerendeUpdate(sqlQuery);
    try {
      verify(connection).close();
    } catch (SQLException e) {
      failAfterException(e);
    }
    assertEquals(0, result);
  }

  @Test
  public void testWyslijKwerendeUpdateWyjatekClose() {
    try {
      doThrow(new SQLException()).when(connection).close();
    } catch (SQLException e1) {
      failAfterException(e1);
    }
    db.wyslijKwerendeUpdate(sqlQuery);
    try {
      verify(connection).close();
    } catch (SQLException e) {
      failAfterException(e);
    }
  }

  @Test
  public void testWyslijKwerendeUpdateWyjatekNieStworzonoStatement() {
    try {
      when(connection.prepareStatement(anyString())).thenThrow(
          new SQLException());
    } catch (SQLException e) {
      failAfterException(e);
    }
    int result = db.wyslijKwerendeUpdate(sqlQuery);
    try {
      verify(connection).close();
    } catch (SQLException e) {
      failAfterException(e);
    }
    assertEquals(0, result);
  }

  @Test
  public void testWyslijKwerende() {
    boolean result = db.wyslijKwerende(sqlQuery);
    try {
      Mockito.verify(preparedStatement).execute();
      Assert.assertFalse(result);
    } catch (SQLException e) {
      failAfterException(e);
    }
  }

  @Ignore
  @Test
  public void testPobierzUzytkownikow() {
    fail("Not yet implemented"); // TODO
  }

  @Ignore
  @Test
  public void testCzyKwerendaZwrocilaWyniki() {
    fail("Not yet implemented"); // TODO
  }

  @Ignore
  @Test
  public void testPobierzIdUzytkownika() {
    fail("Not yet implemented"); // TODO
  }

  @Ignore
  @Test
  public void testCreateSqlHandler() {
    fail("Not yet implemented"); // TODO
  }

}
