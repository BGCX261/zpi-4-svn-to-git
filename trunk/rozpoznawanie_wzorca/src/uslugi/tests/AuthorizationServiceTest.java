/**
 * 
 */
package uslugi.tests;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

import junit.framework.Assert;
import logging.SystemAccessLogger;

import static org.junit.Assert.*;

import static org.mockito.Mockito.mock;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mockito;

import uslugi.AuthorizationService;
import algorytm.AlgorytmModule;
import autoryzacja.AuthorizationMethod;
import autoryzacja.AuthorizationModule;
import baza_danych.model.Uzytkownik;

import com.google.inject.Guice;
import com.google.inject.Injector;

import czytnik.DaneCzytnika;

/**
 * @author liszewsk
 * 
 */
public class AuthorizationServiceTest {
  private Uzytkownik uzytkownik;
  private DaneCzytnika dane;
  private Logger logger;
  private Set<AuthorizationMethod> metody;

  @Before
  public void setUp() {
    uzytkownik = mock(Uzytkownik.class);
    dane = mock(DaneCzytnika.class);
    logger = mock(Logger.class);
    metody = new HashSet<AuthorizationMethod>();
  }

  /**
   * Test method for
   * {@link uslugi.AuthorizationService#autoryzuj(czytnik.DaneCzytnika, algorytm.HashUzytkownika)}
   * .
   */
  @Test
  public void testAutoryzujTrueJednaMetoda() {
    przygotujJednaMetodeAutoryzacji(true);
    AuthorizationService aut = new AuthorizationService(metody, logger);
    assertTrue(aut.autoryzuj(dane, uzytkownik));
  }

  @Test
  public void testAutoryzujFalseJednaMetoda() {
    przygotujJednaMetodeAutoryzacji(false);
    AuthorizationService aut = new AuthorizationService(metody, logger);
    assertFalse(aut.autoryzuj(dane, uzytkownik));
  }

  private void przygotujJednaMetodeAutoryzacji(boolean wynik) {
    AuthorizationMethod metoda = mock(AuthorizationMethod.class);
    Mockito.when(
        metoda.verify((Uzytkownik) Matchers.any(),
            (DaneCzytnika) Matchers.any())).thenReturn(wynik);
    metody.add(metoda);
  }

  @Test
  public void testAutoryzujFalseDwieRozneMetody() {
    przygotujWieleMetodAutoryzacji(true, false);
    AuthorizationService aut = new AuthorizationService(metody, logger);
    assertFalse(aut.autoryzuj(dane, uzytkownik));
  }

  @Test
  public void testAutoryzujFalseDwieTakieSameMetody() {
    przygotujWieleMetodAutoryzacji(false, false);
    AuthorizationService aut = new AuthorizationService(metody, logger);
    assertFalse(aut.autoryzuj(dane, uzytkownik));
  }

  @Test
  public void testAutoryzujFalsePiecMetod() {
    przygotujWieleMetodAutoryzacji(false, false, true, false, true);
    AuthorizationService aut = new AuthorizationService(metody, logger);
    assertFalse(aut.autoryzuj(dane, uzytkownik));
  }

  @Test
  public void testAutoryzujFalsePiecMetodJedenFalse() {
    przygotujWieleMetodAutoryzacji(true, true, true, false, true);
    AuthorizationService aut = new AuthorizationService(metody, logger);
    assertFalse(aut.autoryzuj(dane, uzytkownik));
  }

  @Test
  public void testAutoryzujTruePiecMetodJedenFalse() {
    przygotujWieleMetodAutoryzacji(true, true, true, true, true);
    AuthorizationService aut = new AuthorizationService(metody, logger);
    assertTrue(aut.autoryzuj(dane, uzytkownik));
  }

  private void przygotujWieleMetodAutoryzacji(boolean... wyniki) {
    for (boolean wynik : wyniki) {
      przygotujJednaMetodeAutoryzacji(wynik);
    }
  }

  @Test
  public void testOstatniaWiadomosc() {
    przygotujJednaMetodeAutoryzacji(false);

    Mockito.when(metody.iterator().next().getErrorMessage()).thenReturn(
        "Error message");
    AuthorizationService aut = new AuthorizationService(metody, logger);
    aut.autoryzuj(dane, uzytkownik);

    assertEquals("ACCESS DENIED; CAUSE: Error message",
        aut.pobierzOstatniKomunikat());
  }

  @Ignore(value = "To nie jest test unitowy - tylko integracyjny. Potrzeba stworzenia klasy z testami integracyjnymi")
  @Test
  public void guiceInjectPassTest() {
    Injector inject = Guice.createInjector(new AuthorizationModule(),
        new AlgorytmModule());
    AuthorizationService service = inject
        .getInstance(AuthorizationService.class);
    Assert.assertNotNull(service);
    try {
      Field f = service.getClass().getDeclaredField("LOGGER");
      f.setAccessible(true);
      Logger l = (Logger) f.get(service);
      assertEquals(SystemAccessLogger.loggerName, l.getName());
    } catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }
}
