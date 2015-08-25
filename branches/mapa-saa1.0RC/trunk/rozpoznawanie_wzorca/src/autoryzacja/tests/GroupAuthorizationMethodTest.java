package autoryzacja.tests;

import static org.junit.Assert.*;

import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;

import algorytm.HashUzytkownika;
import autoryzacja.GroupAuthorizationMethod;
import baza_danych.DatabaseFacade;
import czytnik.DaneCzytnika;

/**
 * @author elistan
 * 
 */
public class GroupAuthorizationMethodTest {
  DatabaseFacade db;
  GroupAuthorizationMethod authMethod;
  HashUzytkownika hash;
  DaneCzytnika dane;

  /**
   * Ustawia mocki
   */
  @Before
  public void setUp() {
    db = mock(DatabaseFacade.class);
    hash = mock(HashUzytkownika.class);
    dane = mock(DaneCzytnika.class);
    authMethod = new GroupAuthorizationMethod(db);
  }

  /**
   * Testuje weryfikacje
   */
  @Test
  public void testPassVerify() {
    when(db.czyKwerendaZwrocilaWyniki()).thenReturn(true);
    when(hash.toString()).thenReturn("hash");
    when(dane.getId()).thenReturn(1);
    assertTrue(authMethod.verify(hash, dane));
  }

  /**
   * Negatywna weryfikacja z parametrem null
   */
  @Test
  public void testNullVerify() {
    assertFalse(authMethod.verify(null, null));
    assertFalse(authMethod.verify(hash, null));
    assertFalse(authMethod.verify(null, dane));
  }

  /**
   * Odpowiedz false gdy z bazy przychodzi false
   */
  @Test
  public void verifyFailTest() {
    when(db.czyKwerendaZwrocilaWyniki()).thenReturn(false);
    when(hash.toString()).thenReturn("hash");
    when(dane.getId()).thenReturn(1);
    assertFalse(authMethod.verify(hash, dane));
  }

  /**
   * Sprawdza poprawnosc wygenerowanej kwerendy
   */
  @Test
  public void testQueryCreation() {
    when(db.czyKwerendaZwrocilaWyniki()).thenReturn(true);
    when(hash.toString()).thenReturn("hash");
    when(dane.getId()).thenReturn(1);
    assertTrue(authMethod.verify(hash, dane));
    verify(db).wyslijKwerende(expectedQuery());
  }

  private String expectedQuery() {
    StringBuilder sb = new StringBuilder();
    sb.append("SELECT ")
        .append("    uzy.imie        status, ")
        .append("    uzy.nazwisko    uzytkownik, ")
        .append("    odc.hash        HASH, ")
        .append("    czy.id          czytnik ")
        .append("FROM        odcisk_palca        odc ")
        .append(
            "INNER JOIN  uzytkownik          uzy ON  uzy.id = odc.uzytkownik_id ")
        .append("                                        AND uzy.aktywny = 1 ")
        .append("                                        AND uzy.usuniety = 0 ")
        .append("                                        AND odc.aktywny = 1 ")
        .append(
            "LEFT JOIN   grupa_uzytkownik    gu  ON  gu.uzytkownik_id = uzy.id ")
        .append("LEFT JOIN   grupa               gru ON  gru.id = gu.grupa_id ")
        .append("                                        AND gru.aktywna = 1 ")
        .append(
            "LEFT JOIN   grupa_czytnik       gc  ON  gc.grupa_id = gu.grupa_id ")
        .append(
            "INNER JOIN  czytnik             czy ON  czy.id = gc.czytnik_id ")
        .append("    AND czy.aktywny = 1 ").append("    AND czy.id NOT IN ( ")
        .append("        SELECT czytnik_id cid ")
        .append("        FROM czytnik_uzytkownik ").append("        WHERE ")
        .append("            uzytkownik_id = uzy.id ")
        .append("            AND blacklist = 1 ").append("    ) ")
        .append("    OR czy.id IN ( ").append("        SELECT czytnik_id cid ")
        .append("        FROM czytnik_uzytkownik ").append("        WHERE ")
        .append("            uzytkownik_id = uzy.id ")
        .append("            AND blacklist = 0 ").append("    ) ")
        .append("WHERE ").append("    odc.hash = \"").append("hash\"")
        .append(" AND czy.id = \"").append(1).append("\";");
    return sb.toString();
  }
}
