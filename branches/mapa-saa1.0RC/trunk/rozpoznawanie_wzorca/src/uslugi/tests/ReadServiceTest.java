package uslugi.tests;

import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;

import static org.mockito.Matchers.*;

import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;

import uslugi.ReadService;
import uslugi.ReadingStrategy;
import algorytm.HashUzytkownika;
import baza_danych.DatabaseFacade;

public class ReadServiceTest {

  private DatabaseFacade db;
  private ReadingStrategy strategia;
  private ReadService readService;
  private HashUzytkownika hash;

  @Before
  public void setUp() throws Exception {
    db = mock(DatabaseFacade.class);
    strategia = mock(ReadingStrategy.class);
    readService = new ReadService(strategia, db);
    hash = mock(HashUzytkownika.class);
  }

  @Test
  public void testPobierzUzytkownikowWywolanie() {
    readService.pobierzUzytkownikow();
    verify(strategia).generujSql();
    verify(db).pobierzUzytkownikow(anyString());
  }

  @Test
  public void testPobierzUzytkownikowZwracanaWartosc() {
    List<HashUzytkownika> spodziewanyWynik = Collections
        .<HashUzytkownika> emptyList();
    when(strategia.generujSql()).thenReturn("kwerenda");
    when(db.pobierzUzytkownikow(eq("kwerenda"))).thenReturn(spodziewanyWynik);
    List<HashUzytkownika> wynik = readService.pobierzUzytkownikow();
    assertEquals(spodziewanyWynik, wynik);
  }

  @Test
  public void testPobierzIdUzytkownikaWywolanie() {

    readService.pobierzIdUzytkownika(hash);
    verify(strategia).generujSql(eq(hash));
    verify(db).pobierzIdUzytkownika(anyString());
  }

  @Test
  public void testPobierzIdUzytkownikowZwracanaWartosc() {
    int spodziewanyWynik = 1;
    when(strategia.generujSql(hash)).thenReturn("kwerenda");
    when(db.pobierzIdUzytkownika(eq("kwerenda"))).thenReturn(spodziewanyWynik);
    int wynik = readService.pobierzIdUzytkownika(hash);
    assertEquals(spodziewanyWynik, wynik);
  }
}
