package autoryzacja.tests;

import static org.junit.Assert.*;

import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;

import autoryzacja.GroupAuthorizationMethod;
import baza_danych.DatabaseFacade;
import baza_danych.model.Uzytkownik;
import czytnik.DaneCzytnika;

/**
 * @author elistan
 * 
 */
public class GroupAuthorizationMethodTest {
  DatabaseFacade db;
  GroupAuthorizationMethod authMethod;
  Uzytkownik uzytkownik;
  DaneCzytnika dane;

  /**
   * Ustawia mocki
   */
  @Before
  public void setUp() {
    db = mock(DatabaseFacade.class);
    uzytkownik = mock(Uzytkownik.class);
    dane = mock(DaneCzytnika.class);
    authMethod = new GroupAuthorizationMethod(db);
  }

  /**
   * Testuje weryfikacje
   */
  @Test
  public void testPassVerify() {
    when(db.sprawdzDostepUzytkownikZCzytnikiem(uzytkownik, dane)).thenReturn(
        true);
    assertTrue(authMethod.verify(uzytkownik, dane));
  }

  /**
   * Negatywna weryfikacja z parametrem null
   */
  @Test
  public void testNullVerify() {
    assertFalse(authMethod.verify(null, null));
    assertFalse(authMethod.verify(uzytkownik, null));
    assertFalse(authMethod.verify(null, dane));
  }

  /**
   * Odpowiedz false gdy z bazy przychodzi false
   */
  @Test
  public void verifyFailTest() {
    when(db.sprawdzDostepUzytkownikZCzytnikiem(uzytkownik, dane)).thenReturn(
        false);
    assertFalse(authMethod.verify(uzytkownik, dane));
  }

  @Test
  public void testGetErrorMessage() {
    String error = "próba uzyskania nieautoryzowanego dostępu do zasobu";
    assertEquals(error, authMethod.getErrorMessage());
  }
}
