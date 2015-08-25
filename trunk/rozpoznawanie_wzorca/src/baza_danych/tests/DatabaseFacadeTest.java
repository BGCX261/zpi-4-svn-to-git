package baza_danych.tests;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

import static org.mockito.Matchers.*;

import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;

import algorytm.HashUzytkownika;
import baza_danych.ConnectionFactory;
import baza_danych.DatabaseFacade;
import baza_danych.model.GrafCzytnikow;
import baza_danych.model.TabelaGrafow;
import baza_danych.model.Uzytkownicy;
import baza_danych.model.Uzytkownik;

import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.ResultSet;

import czytnik.DaneCzytnika;

public class DatabaseFacadeTest {

  private static final String KWERENDA_OSTATNI_DOSTEP = "SELECT czytnik_id FROM log_aktywnosci WHERE uzytkownik_id = ? ORDER BY czas DESC LIMIT 1";
  private ConnectionFactory connectionFactory;
  private DatabaseFacade db;
  private PreparedStatement preparedStatement;
  private Connection connection;
  private DaneCzytnika daneCzytnika;
  private Uzytkownik uzytkownik;
  private int idCzytnika;
  private Uzytkownicy uzytkownicy;
  private int idUzytkownika;
  private HashUzytkownika hash;
  private TabelaGrafow tabela;

  @Before
  public void setUp() throws Exception {
    prepareConnectionFactory();
    uzytkownicy = mock(Uzytkownicy.class);
    tabela = mock(TabelaGrafow.class);
    db = new DatabaseFacade(connectionFactory, uzytkownicy, tabela);
    idCzytnika = 1;
    idUzytkownika = 2;
    daneCzytnika = new DaneCzytnika(idCzytnika);
    uzytkownik = mock(Uzytkownik.class);
    when(uzytkownik.getId()).thenReturn(idUzytkownika);
    hash = mock(HashUzytkownika.class);
    when(hash.toString()).thenReturn("HASH");
  }

  private void prepareConnectionFactory() throws SQLException {
    connectionFactory = mock(ConnectionFactory.class);
    connection = mock(Connection.class);
    preparedStatement = mock(PreparedStatement.class);
    when(connectionFactory.getConnection()).thenReturn(connection);
  }

  private void failAfterException(Exception e) {
    fail("Exception: " + e.getLocalizedMessage());
  }

  @Test
  public void sprawdzDostepUzytkownikZCzytnikiemPassTestPositive() {
    String posrednieZapytanie = spodziewaneZapytanieSzukajaceUzytkownikaICzytnikId();
    ResultSet resultSet = mock(ResultSet.class);
    try {
      when(connection.prepareStatement(posrednieZapytanie)).thenReturn(
          preparedStatement);
      when(preparedStatement.executeQuery()).thenReturn(resultSet);
      when(resultSet.first()).thenReturn(true);
    } catch (SQLException e) {
      failAfterException(e);
    }
    boolean result = db.sprawdzDostepUzytkownikZCzytnikiem(uzytkownik,
        daneCzytnika);
    assertTrue(result);
    try {
      verify(preparedStatement).setInt(1, idUzytkownika);
      verify(preparedStatement).setInt(2, idCzytnika);
      verify(connection).close();
    } catch (SQLException e) {
      failAfterException(e);
    }
    sprawdzZamknieciePolaczenia();
  }

  @Test
  public void sprawdzDostepUzytkownikZCzytnikiemPassTestNegative() {
    String posrednieZapytanie = spodziewaneZapytanieSzukajaceUzytkownikaICzytnikId();
    ResultSet resultSet = mock(ResultSet.class);
    try {
      when(connection.prepareStatement(posrednieZapytanie)).thenReturn(
          preparedStatement);
      when(preparedStatement.executeQuery()).thenReturn(resultSet);
      when(resultSet.first()).thenReturn(false);
    } catch (SQLException e) {
      failAfterException(e);
    }
    boolean result = db.sprawdzDostepUzytkownikZCzytnikiem(uzytkownik,
        daneCzytnika);
    assertFalse(result);
    try {
      verify(preparedStatement).setInt(1, idUzytkownika);
      verify(preparedStatement).setInt(2, idCzytnika);
      verify(connection).close();
    } catch (SQLException e) {
      failAfterException(e);
    }
    sprawdzZamknieciePolaczenia();

  }

  private String spodziewaneZapytanieSzukajaceUzytkownikaICzytnikId() {
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

  @Test
  public void sprawdzDostepUzytkownikZCzytnikiemFailPolaczenieNull() {
    try {
      when(connectionFactory.getConnection()).thenReturn(null);
    } catch (SQLException e) {
      failAfterException(e);
    }
    boolean result = db.sprawdzDostepUzytkownikZCzytnikiem(uzytkownik,
        daneCzytnika);
    assertFalse(result);
    verifyZeroInteractions(preparedStatement);
    verifyZeroInteractions(connection);
    verifyZeroInteractions(uzytkownik);

  }

  @Test
  public void sprawdzDostepUzytkownikZCzytnikiemFailPolaczenieException() {
    try {
      when(connectionFactory.getConnection()).thenThrow(new SQLException());
    } catch (SQLException e) {
      failAfterException(e);
    }
    boolean result = db.sprawdzDostepUzytkownikZCzytnikiem(uzytkownik,
        daneCzytnika);
    assertFalse(result);
    verifyZeroInteractions(preparedStatement);
    verifyZeroInteractions(connection);
    verifyZeroInteractions(uzytkownik);

  }

  @Test
  public void sprawdzDostepUzytkownikZCzytnikiemFailPrepareStatementExceptionPoSetInt1() {
    try {
      when(connection.prepareStatement(anyString())).thenReturn(
          preparedStatement);
      doThrow(new SQLException()).when(preparedStatement).setInt(eq(1),
          anyInt());
    } catch (SQLException e) {
      failAfterException(e);
    }
    boolean result = db.sprawdzDostepUzytkownikZCzytnikiem(uzytkownik,
        daneCzytnika);
    assertFalse(result);
    sprawdzZamknieciePolaczenia();
  }

  @Test
  public void sprawdzDostepUzytkownikZCzytnikiemFailPrepareStatementExceptionPoSetInt2() {
    try {
      when(connection.prepareStatement(anyString())).thenReturn(
          preparedStatement);
      doThrow(new SQLException()).when(preparedStatement).setInt(eq(2),
          anyInt());
    } catch (SQLException e) {
      failAfterException(e);
    }
    boolean result = db.sprawdzDostepUzytkownikZCzytnikiem(uzytkownik,
        daneCzytnika);
    assertFalse(result);
  }

  @Test
  public void sprawdzDostepUzytkownikZCzytnikiemFailPrepareStatementExceptionPoExecute() {
    try {
      when(connection.prepareStatement(anyString())).thenReturn(
          preparedStatement);
      when(preparedStatement.executeQuery()).thenThrow(new SQLException());
    } catch (SQLException e) {
      failAfterException(e);
    }
    boolean result = db.sprawdzDostepUzytkownikZCzytnikiem(uzytkownik,
        daneCzytnika);
    assertFalse(result);
    sprawdzZamknieciePolaczenia();
  }

  private void sprawdzZamknieciePolaczenia() {
    try {
      verify(connection).close();
    } catch (SQLException e) {
      failAfterException(e);
    }
  }

  @Test
  public void pobierzUzytkownikowTest() throws SQLException {
    @SuppressWarnings("unchecked")
    List<Uzytkownik> uzytkownicyList = mock(List.class);
    when(uzytkownicy.pobierzUzytkownikow()).thenReturn(uzytkownicyList);
    db.pobierzUzytkownikow();
    verify(uzytkownicy).wypelnijUzytkownikowHashami(eq(uzytkownicyList));
    verifyZeroInteractions(uzytkownicyList);
  }

  @Test
  public void pobierzUzytkownikowTestWyjatekPoPobierz() throws SQLException {
    when(uzytkownicy.pobierzUzytkownikow()).thenThrow(new SQLException());
    List<Uzytkownik> uzytkownicyList = db.pobierzUzytkownikow();
    verify(uzytkownicy, times(0)).wypelnijUzytkownikowHashami(
        eq(uzytkownicyList));
    assertNotNull(uzytkownicyList);
    assertTrue(uzytkownicyList.isEmpty());
  }

  @Test
  public void pobierzUzytkownikowTestWyjatekPoWypelnij() throws SQLException {
    List<Uzytkownik> uzytkownicyList = new ArrayList<Uzytkownik>();
    when(uzytkownicy.pobierzUzytkownikow()).thenReturn(uzytkownicyList);
    when(uzytkownicy.wypelnijUzytkownikowHashami(eq(uzytkownicyList)))
        .thenThrow(new SQLException());
    List<Uzytkownik> zwroconaLista = db.pobierzUzytkownikow();
    assertFalse(uzytkownicyList == zwroconaLista);
    assertNotNull(zwroconaLista);
    assertTrue(zwroconaLista.isEmpty());
  }

  @Test
  public void dodajUzytkownikowiMetodeAutoryzacjiPassTest() throws SQLException {
    when(connection.prepareStatement(spodziewanaKwerendaDodajOdcisk()))
        .thenReturn(preparedStatement);
    when(preparedStatement.executeUpdate()).thenReturn(1);
    assertTrue(db.dodajUzytkownikowiMetodeAutoryzacji(idUzytkownika, hash));
    verify(connection).prepareStatement(spodziewanaKwerendaDodajOdcisk());
    verify(preparedStatement).setString(1, "HASH");
    verify(preparedStatement).setInt(2, idUzytkownika);
    sprawdzZamknieciePolaczenia();
  }

  @Test
  public void dodajUzytkownikowiMetodeAutoryzacjiWeryfikacjaOKWyjatePoClose()
      throws SQLException {
    when(connection.prepareStatement(spodziewanaKwerendaDodajOdcisk()))
        .thenReturn(preparedStatement);
    when(preparedStatement.executeUpdate()).thenReturn(1);
    doThrow(new SQLException()).when(connection).close();
    assertTrue(db.dodajUzytkownikowiMetodeAutoryzacji(idUzytkownika, hash));
    verify(connection).prepareStatement(spodziewanaKwerendaDodajOdcisk());
    verify(preparedStatement).setString(1, "HASH");
    verify(preparedStatement).setInt(2, idUzytkownika);
    sprawdzZamknieciePolaczenia();
  }

  @Test
  public void dodajUzytkownikowiMetodeAutoryzacjiNieudaneDodanie()
      throws SQLException {
    when(connection.prepareStatement(spodziewanaKwerendaDodajOdcisk()))
        .thenReturn(preparedStatement);
    when(preparedStatement.executeUpdate()).thenReturn(0);
    assertFalse(db.dodajUzytkownikowiMetodeAutoryzacji(idUzytkownika, hash));
    verify(connection).prepareStatement(spodziewanaKwerendaDodajOdcisk());
    verify(preparedStatement).setString(1, "HASH");
    verify(preparedStatement).setInt(2, idUzytkownika);
    sprawdzZamknieciePolaczenia();
  }

  @Test
  public void dodajUzytkownikowiMetodeAutoryzacjiBrakPolaczenia()
      throws SQLException {
    when(connectionFactory.getConnection()).thenThrow(new SQLException());
    assertFalse(db.dodajUzytkownikowiMetodeAutoryzacji(idUzytkownika, hash));
  }

  @Test
  public void dodajUzytkownikowiMetodeAutoryzacjiWyjatekPoPrepareStatement()
      throws SQLException {
    when(connection.prepareStatement(spodziewanaKwerendaDodajOdcisk()))
        .thenThrow(new SQLException());
    assertFalse(db.dodajUzytkownikowiMetodeAutoryzacji(idUzytkownika, hash));
    sprawdzZamknieciePolaczenia();
  }

  @Test
  public void dodajUzytkownikowiMetodeAutoryzacjiWyjatekPoExecute()
      throws SQLException {
    when(connection.prepareStatement(spodziewanaKwerendaDodajOdcisk()))
        .thenReturn(preparedStatement);
    when(preparedStatement.executeUpdate()).thenThrow(new SQLException());
    assertFalse(db.dodajUzytkownikowiMetodeAutoryzacji(idUzytkownika, hash));
    sprawdzZamknieciePolaczenia();
  }

  @Test
  public void dodajUzytkownikowiMetodeAutoryzacjiWyjatekPoSetInt()
      throws SQLException {
    when(connection.prepareStatement(spodziewanaKwerendaDodajOdcisk()))
        .thenReturn(preparedStatement);
    doThrow(new SQLException()).when(preparedStatement).setInt(anyInt(),
        anyInt());
    assertFalse(db.dodajUzytkownikowiMetodeAutoryzacji(idUzytkownika, hash));
    sprawdzZamknieciePolaczenia();
  }

  private String spodziewanaKwerendaDodajOdcisk() {
    return "INSERT INTO odcisk_palca(hash, uzytkownik_id) VALUES(?, ?);";
  }

  @Test
  public void testPobierzGraf() throws SQLException {
    db.pobierzGrafCzytnikow();
    verify(tabela).pobierzGraf();
  }

  @Test(expected = RuntimeException.class)
  public void testPobierzGrafException() throws SQLException {
    when(tabela.pobierzGraf()).thenThrow(new SQLException());
    db.pobierzGrafCzytnikow();
  }

  @Test
  public void testPobierzOstatniCzytnikPass() throws SQLException {
    when(connection.prepareStatement(KWERENDA_OSTATNI_DOSTEP)).thenReturn(
        preparedStatement);
    ResultSet resultSet = mock(ResultSet.class);
    when(preparedStatement.executeQuery()).thenReturn(resultSet);
    when(resultSet.first()).thenReturn(true);

    db.znajdzPoprzedniCzytnik(uzytkownik);

    verify(preparedStatement).setInt(1, uzytkownik.getId());
    verify(resultSet).getInt(1);
    verify(connection).close();
  }

  @Test
  public void testPobierzOstatniCzytnikBrakCzytnikow() throws SQLException {
    when(connection.prepareStatement(KWERENDA_OSTATNI_DOSTEP)).thenReturn(
        preparedStatement);
    ResultSet resultSet = mock(ResultSet.class);
    when(preparedStatement.executeQuery()).thenReturn(resultSet);
    when(resultSet.first()).thenReturn(false);

    assertEquals(0, db.znajdzPoprzedniCzytnik(uzytkownik));

    verify(preparedStatement).setInt(1, uzytkownik.getId());
    verify(resultSet, times(0)).getInt(anyInt());
    verify(connection).close();
  }

  @Test(expected = RuntimeException.class)
  public void testPobierzOstatniCzytnikWyjatek() throws SQLException {
    when(connection.prepareStatement(KWERENDA_OSTATNI_DOSTEP)).thenReturn(
        preparedStatement);
    ResultSet resultSet = mock(ResultSet.class);
    when(preparedStatement.executeQuery()).thenThrow(new SQLException());
    try {
      db.znajdzPoprzedniCzytnik(uzytkownik);
    } catch (RuntimeException e) {
      verify(connection).close();
      throw e;
    }
  }

  @Test
  public void testZapiszGrafDoBazyPass() throws SQLException {
    when(tabela.dodajGraf((GrafCzytnikow) any())).thenReturn(true);
    assertTrue(db.zapiszGrafDoBazy(mock(GrafCzytnikow.class)));
  }

  @Test
  public void testZapiszGrafDoBazyFail() throws SQLException {
    when(tabela.dodajGraf((GrafCzytnikow) any())).thenReturn(false);
    assertFalse(db.zapiszGrafDoBazy(mock(GrafCzytnikow.class)));
  }

  @Test
  public void testZapiszGrafDoBazyWyjatek() throws SQLException {
    when(tabela.dodajGraf((GrafCzytnikow) any())).thenThrow(new SQLException());
    assertFalse(db.zapiszGrafDoBazy(mock(GrafCzytnikow.class)));
  }
}
