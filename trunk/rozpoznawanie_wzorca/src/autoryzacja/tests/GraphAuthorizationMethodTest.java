package autoryzacja.tests;

import static org.junit.Assert.*;

import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;

import autoryzacja.GraphAuthorizationMethod;
import baza_danych.DatabaseFacade;
import baza_danych.model.GrafCzytnikow;
import baza_danych.model.Uzytkownik;
import czytnik.DaneCzytnika;

public class GraphAuthorizationMethodTest {
  GraphAuthorizationMethod grafMethod;
  DatabaseFacade db;
  GrafCzytnikow graf;
  Uzytkownik uzytkownik;
  DaneCzytnika czytnik;

  @Before
  public void setUp() throws Exception {
    db = mock(DatabaseFacade.class);
    graf = mock(GrafCzytnikow.class);
    uzytkownik = mock(Uzytkownik.class);
    czytnik = mock(DaneCzytnika.class);
    grafMethod = new GraphAuthorizationMethod(db);
    when(db.pobierzGrafCzytnikow()).thenReturn(graf);
  }

  @Test
  public void testVerifyPass() {
    int poprzedniCzytnik = 1;
    int aktualnyCzytnik = 5;
    when(czytnik.getId()).thenReturn(aktualnyCzytnik);
    when(db.znajdzPoprzedniCzytnik(uzytkownik)).thenReturn(poprzedniCzytnik);
    when(graf.czyDozwolonePrzejscie(aktualnyCzytnik, poprzedniCzytnik))
        .thenReturn(true);

    assertTrue(grafMethod.verify(uzytkownik, czytnik));
  }

  @Test
  public void testVerifyFail() {
    int poprzedniCzytnik = 1;
    int aktualnyCzytnik = 5;
    when(czytnik.getId()).thenReturn(aktualnyCzytnik);
    when(db.znajdzPoprzedniCzytnik(uzytkownik)).thenReturn(poprzedniCzytnik);
    when(graf.czyDozwolonePrzejscie(aktualnyCzytnik, poprzedniCzytnik))
        .thenReturn(false);

    assertFalse(grafMethod.verify(uzytkownik, czytnik));
  }

  @Test
  public void testVerifyBrakPoprzedniego() {
    int poprzedniCzytnik = 0;
    int aktualnyCzytnik = 5;
    when(czytnik.getId()).thenReturn(aktualnyCzytnik);
    when(db.znajdzPoprzedniCzytnik(uzytkownik)).thenReturn(poprzedniCzytnik);
    when(graf.czyDozwolonePrzejscie(aktualnyCzytnik, poprzedniCzytnik))
        .thenReturn(false);

    assertTrue(grafMethod.verify(uzytkownik, czytnik));
  }

  @Test
  public void testErrorMessage() {
    String errorMessage = "Niedozwolone przejście między czytnikami.";
    assertEquals(errorMessage, grafMethod.getErrorMessage());
  }

  @Test
  public void testBrakGrafu() {
    when(db.pobierzGrafCzytnikow()).thenReturn(null);
    assertFalse(graf.czyDozwolonePrzejscie(0, 0));
  }
}
