package baza_danych.tests;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

import static org.mockito.Matchers.anyString;

import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.stubbing.OngoingStubbing;

import algorytm.HashUzytkownika;
import algorytm.HashUzytkownikaFactory;
import baza_danych.ConnectionFactory;
import baza_danych.model.Uzytkownicy;
import baza_danych.model.Uzytkownik;
import baza_danych.model.UzytkownikFactory;

import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.ResultSet;

public class UzytkownicyTest {
  private ConnectionFactory connectionFactory;
  private HashUzytkownikaFactory hashFactory;
  private Uzytkownicy tabelaUzytkownikow;
  private PreparedStatement preparedStatement;
  private Connection connection;
  private ResultSet resultSet;
  private UzytkownikFactory uzytkownikBuilder;
  private String zapytanie;

  @Before
  public void setUp() throws Exception {
    prepareConnectionFactory();
    prepareUzytkownikFactory();
    hashFactory = mock(HashUzytkownikaFactory.class);
    resultSet = mock(ResultSet.class);
    tabelaUzytkownikow = new Uzytkownicy(connectionFactory, uzytkownikBuilder,
        hashFactory);
  }

  private void prepareUzytkownikFactory() {
    uzytkownikBuilder = new UzytkownikFactory() {
      @Override
      public Uzytkownik stworzUzytkownika(int id) {
        return new Uzytkownik(id);
      }
    };
  }

  private void prepareConnectionFactory() throws SQLException {
    connectionFactory = mock(ConnectionFactory.class);
    connection = mock(Connection.class);
    preparedStatement = mock(PreparedStatement.class);
    when(connectionFactory.getConnection()).thenReturn(connection);
  }

  @Test
  public void testPobierzUzytkownikowBrakUzytkownikow() throws SQLException {
    przygotujSpodziewaneZapytanie("SELECT id FROM uzytkownik WHERE aktywny = 1 AND usuniety = 0");
    prepareStatement();
    when(resultSet.first()).thenReturn(false);
    List<Uzytkownik> uzytkownicy = tabelaUzytkownikow.pobierzUzytkownikow();
    assertTrue(uzytkownicy.isEmpty());
    verify(preparedStatement).executeQuery();
    verify(resultSet).first();
    verify(connection).close();
  }

  private void prepareStatement() throws SQLException {
    when(preparedStatement.executeQuery()).thenReturn(resultSet);
  }

  private void przygotujSpodziewaneZapytanie(String zapytanie)
      throws SQLException {
    this.zapytanie = zapytanie;
    when(connection.prepareStatement(zapytanie)).thenReturn(preparedStatement);
  }

  private void failAfterException(Exception e) {
    fail("Exception " + e.getMessage());
  }

  @Test
  public void testPobierzUzytkownikowJedenUzytkownik() throws SQLException {
    przygotujSpodziewaneZapytanie("SELECT id FROM uzytkownik WHERE aktywny = 1 AND usuniety = 0");
    int spodziewanaLiczbaUzytkownikow = 1;
    prepareStatement();
    prepareResultSet(spodziewanaLiczbaUzytkownikow);
    List<Uzytkownik> uzytkownicy = tabelaUzytkownikow.pobierzUzytkownikow();
    assertEquals(spodziewanaLiczbaUzytkownikow, uzytkownicy.size());
    verify(preparedStatement).executeQuery();
    verify(resultSet).first();
    verify(resultSet).next();
    verify(resultSet).getInt(1);
    verify(connection).close();

  }

  private void prepareResultSet(int iloscUzytkownikow) throws SQLException {
    prepareResults(iloscUzytkownikow);
    prepareResultCounts(iloscUzytkownikow);
  }

  private void prepareResultCounts(int iloscUzytkownikow) throws SQLException {
    if (iloscUzytkownikow == 0)
      when(resultSet.first()).thenReturn(false);
    else if (iloscUzytkownikow == 1) {
      when(resultSet.first()).thenReturn(true);
      when(resultSet.next()).thenReturn(false);
    } else {
      when(resultSet.first()).thenReturn(true);
      OngoingStubbing<Boolean> stubbing = when(resultSet.next()).thenReturn(
          true);
      for (int i = 2; i < iloscUzytkownikow; i++) {
        stubbing = stubbing.thenReturn(true);
      }
      stubbing.thenReturn(false);
    }
  }

  private void prepareResults(int iloscUzytkownikow) throws SQLException {
    if (iloscUzytkownikow > 0) {
      OngoingStubbing<Integer> ongoingStubbingInt = when(resultSet.getInt(1))
          .thenReturn(0);
      for (int i = 1; i < iloscUzytkownikow; i++) {
        ongoingStubbingInt = ongoingStubbingInt.thenReturn(i);
      }
    }
  }

  @Test
  public void pobierzUzytkownikowTrzechUzytkownikow() throws SQLException {
    przygotujSpodziewaneZapytanie("SELECT id FROM uzytkownik WHERE aktywny = 1 AND usuniety = 0");
    int spodziewanaLiczbaUzytkownikow = 3;

    prepareStatement();
    prepareResultSet(3);

    List<Uzytkownik> uzytkownicy = tabelaUzytkownikow.pobierzUzytkownikow();
    assertEquals(spodziewanaLiczbaUzytkownikow, uzytkownicy.size());
    for (int i = 0; i < 3; i++) {
      assertEquals(i, uzytkownicy.get(i).getId());
    }
    verify(preparedStatement).executeQuery();
    verify(resultSet).first();
    verify(resultSet, times(3)).next();
    verify(resultSet, times(3)).getInt(1);
    verify(connection).close();

  }

  @Test
  public void testWypelnijHashamiBrakUzytkownikow() {
    List<Uzytkownik> uzytkownicy = new ArrayList<Uzytkownik>();

    List<Uzytkownik> wynik;
    try {
      wynik = tabelaUzytkownikow.wypelnijUzytkownikowHashami(uzytkownicy);
      assertEquals(0, wynik.size());
      verifyZeroInteractions(connectionFactory);
    } catch (SQLException e) {
      failAfterException(e);
    }

  }

  @Test
  public void testWypelnijHashamiJedenUzytkownikBrakHashy() throws SQLException {
    przygotujSpodziewaneZapytanie("SELECT uzytkownik_id, hash FROM odcisk_palca WHERE aktywny = 1 AND uzytkownik_id in (?) ORDER BY uzytkownik_id;");
    int idUzytkownika = 1;
    List<Uzytkownik> uzytkownicy = new ArrayList<Uzytkownik>();
    uzytkownicy.add(new Uzytkownik(idUzytkownika));
    prepareStatement();
    prepareResultSet(0);

    List<Uzytkownik> wynik = tabelaUzytkownikow
        .wypelnijUzytkownikowHashami(uzytkownicy);
    assertEquals(0, wynik.size());
    verify(preparedStatement).setInt(idUzytkownika, 1);
    verify(preparedStatement).executeQuery();

  }

  @Test
  public void testPoprawnoscZapytaniaJedenUzytkownik() throws SQLException {
    przygotujSpodziewaneZapytanie("SELECT uzytkownik_id, hash FROM odcisk_palca WHERE aktywny = 1 AND uzytkownik_id in (?) ORDER BY uzytkownik_id;");
    List<Uzytkownik> uzytkownicy = new ArrayList<Uzytkownik>();
    Uzytkownik uzytkownik = mock(Uzytkownik.class);
    uzytkownicy.add(uzytkownik);
    prepareStatement();
    tabelaUzytkownikow.wypelnijUzytkownikowHashami(uzytkownicy);
    verify(preparedStatement).setInt(1, 0);
    verify(connection).prepareStatement(zapytanie);
  }

  @Test
  public void testPoprawnoscZapytaniaDwochUzytkownikow() throws SQLException {
    przygotujSpodziewaneZapytanie("SELECT uzytkownik_id, hash FROM odcisk_palca WHERE aktywny = 1 AND uzytkownik_id in (?, ?) ORDER BY uzytkownik_id;");
    List<Uzytkownik> uzytkownicy = new ArrayList<Uzytkownik>();
    Uzytkownik uzytkownik = mock(Uzytkownik.class);
    when(uzytkownik.getId()).thenReturn(5).thenReturn(6);
    uzytkownicy.add(uzytkownik);
    uzytkownicy.add(uzytkownik);

    prepareStatement();
    tabelaUzytkownikow.wypelnijUzytkownikowHashami(uzytkownicy);
    verify(connection).prepareStatement(zapytanie);
    verify(preparedStatement).setInt(1, 5);
    verify(preparedStatement).setInt(2, 6);

  }

  @Test
  public void testPoprawnoscZapytaniaPiecUzytkownikow() throws SQLException {
    przygotujSpodziewaneZapytanie("SELECT uzytkownik_id, hash FROM odcisk_palca WHERE aktywny = 1 AND uzytkownik_id in (?, ?, ?, ?, ?) ORDER BY uzytkownik_id;");
    List<Uzytkownik> uzytkownicy = new ArrayList<Uzytkownik>();
    Uzytkownik uzytkownik = mock(Uzytkownik.class);
    when(uzytkownik.getId()).thenReturn(5).thenReturn(6).thenReturn(7)
        .thenReturn(8).thenReturn(9);
    for (int i = 1; i <= 5; i++) {
      uzytkownicy.add(uzytkownik);
    }
    prepareStatement();
    tabelaUzytkownikow.wypelnijUzytkownikowHashami(uzytkownicy);
    verify(connection).prepareStatement(zapytanie);
    verify(preparedStatement).setInt(1, 5);
    verify(preparedStatement).setInt(2, 6);
    verify(preparedStatement).setInt(3, 7);
    verify(preparedStatement).setInt(4, 8);
    verify(preparedStatement).setInt(5, 9);

  }

  @Test
  public void testWypelnijHashamiJedenUzytkownikJedenHash() throws SQLException {
    int idUzytkownika = 1;
    List<Uzytkownik> uzytkownicy = new ArrayList<Uzytkownik>();
    Uzytkownik uzytkownik = new Uzytkownik(idUzytkownika);
    uzytkownicy.add(uzytkownik);

    when(connection.prepareStatement(anyString()))
        .thenReturn(preparedStatement);
    prepareStatement();
    przygotujResultSetZHashami(1, idUzytkownika);
    List<Uzytkownik> wynik = tabelaUzytkownikow
        .wypelnijUzytkownikowHashami(uzytkownicy);
    assertEquals(1, wynik.size());
    assertEquals(1, wynik.get(0).pobierzHashe().size());

    verify(hashFactory).createHashUzytkownika("HASH");
    sprawdzZamknieciePolaczenia();
  }

  private void sprawdzZamknieciePolaczenia() {
    try {
      verify(connection).close();
    } catch (SQLException e) {
      failAfterException(e);
    }
  }

  private void przygotujResultSetZHashami(int ileHashy, int... idUzytkownikow)
      throws SQLException {
    prepareResultCounts(ileHashy);
    prepareResultsHashUzytkownika(idUzytkownikow);
  }

  private void prepareResultsHashUzytkownika(int... idUzytkownikow)
      throws SQLException {
    if (idUzytkownikow.length > 0) {
      OngoingStubbing<String> stubbing = when(resultSet.getString(2))
          .thenReturn("HASH");
      for (int i = 1; i < idUzytkownikow.length; i++) {
        stubbing = stubbing.thenReturn("HASH");
      }
      OngoingStubbing<Integer> intStubbing = when(resultSet.getInt(1))
          .thenReturn(idUzytkownikow[0]);
      for (int i = 1; i < idUzytkownikow.length; i++) {
        intStubbing = intStubbing.thenReturn(idUzytkownikow[i]);
      }
    }
  }

  @Test
  public void testWypelnijHashamiJedenUzytkownikTrzyHashe() {
    int idUzytkownika = 1;
    List<Uzytkownik> uzytkownicy = new ArrayList<Uzytkownik>();
    Uzytkownik uzytkownik = new Uzytkownik(idUzytkownika);
    uzytkownicy.add(uzytkownik);
    try {
      when(connection.prepareStatement(anyString())).thenReturn(
          preparedStatement);
      prepareStatement();
      przygotujResultSetZHashami(3, idUzytkownika, idUzytkownika, idUzytkownika);
      when(hashFactory.createHashUzytkownika("HASH"))
          .thenReturn(mock(HashUzytkownika.class))
          .thenReturn(mock(HashUzytkownika.class))
          .thenReturn(mock(HashUzytkownika.class));
      List<Uzytkownik> wynik = tabelaUzytkownikow
          .wypelnijUzytkownikowHashami(uzytkownicy);
      assertEquals(1, wynik.size());
      assertEquals(3, wynik.get(0).pobierzHashe().size());
    } catch (SQLException e) {
      failAfterException(e);
    }

    verify(hashFactory, times(3)).createHashUzytkownika("HASH");
    sprawdzZamknieciePolaczenia();
  }

  @Test
  public void testWypelnijHashamiDwochUzytkownikowJedenHash() {
    int idUzytkownika = 1, idDrugiegoUzytkownika = 2;
    List<Uzytkownik> uzytkownicy = new ArrayList<Uzytkownik>();
    Uzytkownik uzytkownik = new Uzytkownik(idUzytkownika);
    uzytkownicy.add(uzytkownik);
    uzytkownik = new Uzytkownik(idDrugiegoUzytkownika);
    uzytkownicy.add(uzytkownik);
    try {
      when(connection.prepareStatement(anyString())).thenReturn(
          preparedStatement);
      prepareStatement();
      przygotujResultSetZHashami(1, idDrugiegoUzytkownika);
      when(hashFactory.createHashUzytkownika("HASH")).thenReturn(
          mock(HashUzytkownika.class));
      List<Uzytkownik> wynik = tabelaUzytkownikow
          .wypelnijUzytkownikowHashami(uzytkownicy);
      assertEquals(1, wynik.size());
      assertEquals(1, wynik.get(0).pobierzHashe().size());
      assertEquals(idDrugiegoUzytkownika, wynik.get(0).getId());
    } catch (SQLException e) {
      failAfterException(e);
    }
    verify(hashFactory).createHashUzytkownika("HASH");
    sprawdzZamknieciePolaczenia();
  }

  @Test
  public void testWypelnijHashamiDwochUzytkownikowTrzyHashe() {
    int idUzytkownika = 1, idDrugiegoUzytkownika = 2;
    List<Uzytkownik> uzytkownicy = new ArrayList<Uzytkownik>();
    Uzytkownik uzytkownik = new Uzytkownik(idUzytkownika);
    uzytkownicy.add(uzytkownik);
    uzytkownik = new Uzytkownik(idDrugiegoUzytkownika);
    uzytkownicy.add(uzytkownik);
    try {
      when(connection.prepareStatement(anyString())).thenReturn(
          preparedStatement);
      prepareStatement();
      przygotujResultSetZHashami(3, idUzytkownika, idDrugiegoUzytkownika,
          idDrugiegoUzytkownika);
      when(hashFactory.createHashUzytkownika("HASH"))
          .thenReturn(mock(HashUzytkownika.class))
          .thenReturn(mock(HashUzytkownika.class))
          .thenReturn(mock(HashUzytkownika.class));
      List<Uzytkownik> wynik = tabelaUzytkownikow
          .wypelnijUzytkownikowHashami(uzytkownicy);
      assertEquals(2, wynik.size());
      assertEquals(1, wynik.get(0).pobierzHashe().size());
      assertEquals(2, wynik.get(1).pobierzHashe().size());
      assertEquals(idUzytkownika, wynik.get(0).getId());
      assertEquals(idDrugiegoUzytkownika, wynik.get(1).getId());
    } catch (SQLException e) {
      failAfterException(e);
    }

    verify(hashFactory, times(3)).createHashUzytkownika("HASH");
    sprawdzZamknieciePolaczenia();
  }
}
