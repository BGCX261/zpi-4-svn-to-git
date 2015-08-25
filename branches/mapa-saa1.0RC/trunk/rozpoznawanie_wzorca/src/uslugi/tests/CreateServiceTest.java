package uslugi.tests;

import static org.junit.Assert.*;

import static org.mockito.Matchers.*;

import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;

import uslugi.AddingStrategy;
import uslugi.CreateService;
import baza_danych.DatabaseFacade;

public class CreateServiceTest {
  CreateService service;
  private DatabaseFacade db;
  private AddingStrategy strategia;

  @Before
  public void setUp() throws Exception {
    db = mock(DatabaseFacade.class);
    strategia = mock(AddingStrategy.class);
    service = new CreateService(strategia, db);
  }

  @Test
  public void testDodaj() {
    int id = 0;
    service.dodaj(id);
    try {
      verify(strategia).generujSql(eq(id));
      verify(db).wyslijKwerendeUpdate((String) any());
    } catch (InterruptedException e) {
      fail("Exception" + e);
    }
  }

  @Test
  public void testZwracaniaWartosci() {
    int id = 0;
    try {
      when(strategia.generujSql(eq(id))).thenReturn("kwerenda");
    } catch (InterruptedException e) {
      fail("Exception" + e);
    }
    when(db.wyslijKwerendeUpdate(eq("kwerenda"))).thenReturn(1);
    assertTrue(service.dodaj(id));
    assertFalse(service.dodaj(id + 1));
  }
}
