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
import uslugi.ReadService;
import algorytm.AlgorytmModule;
import algorytm.HashUzytkownika;
import autoryzacja.AuthorizationMethod;
import autoryzacja.AuthorizationModule;

import com.google.inject.Guice;
import com.google.inject.Injector;

import czytnik.DaneCzytnika;

/**
 * @author liszewsk
 * 
 */
public class AuthorizationServiceTest {
  private HashUzytkownika hash;
  private DaneCzytnika dane;
  private Logger logger;
  private Set<AuthorizationMethod> metody;
  private ReadService readService;

  @Before
  public void setUp() {
    hash = mock(HashUzytkownika.class);
    dane = mock(DaneCzytnika.class);
    logger = mock(Logger.class);
    metody = new HashSet<AuthorizationMethod>();
    readService = mock(ReadService.class);
  }

  /**
   * Test method for
   * {@link uslugi.AuthorizationService#autoryzuj(czytnik.DaneCzytnika, algorytm.HashUzytkownika)}
   * .
   */
  @Test
  public void testAutoryzujTrueJednaMetoda() {
    przygotujJednaMetodeAutoryzacji(true);
    AuthorizationService aut = new AuthorizationService(metody, logger,
        readService);
    assertTrue(aut.autoryzuj(dane, hash));
  }

  @Test
  public void testAutoryzujFalseJednaMetoda() {
    przygotujJednaMetodeAutoryzacji(false);
    AuthorizationService aut = new AuthorizationService(metody, logger,
        readService);
    assertFalse(aut.autoryzuj(dane, hash));
  }

  private void przygotujJednaMetodeAutoryzacji(boolean wynik) {
    AuthorizationMethod metoda = mock(AuthorizationMethod.class);
    Mockito.when(
        metoda.verify((HashUzytkownika) Matchers.any(),
            (DaneCzytnika) Matchers.any())).thenReturn(wynik);
    metody.add(metoda);
  }

  @Test
  public void testAutoryzujFalseDwieRozneMetody() {
    przygotujWieleMetodAutoryzacji(true, false);
    AuthorizationService aut = new AuthorizationService(metody, logger,
        readService);
    assertFalse(aut.autoryzuj(dane, hash));
  }

  @Test
  public void testAutoryzujFalseDwieTakieSameMetody() {
    przygotujWieleMetodAutoryzacji(false, false);
    AuthorizationService aut = new AuthorizationService(metody, logger,
        readService);
    assertFalse(aut.autoryzuj(dane, hash));
  }

  @Test
  public void testAutoryzujFalsePiecMetod() {
    przygotujWieleMetodAutoryzacji(false, false, true, false, true);
    AuthorizationService aut = new AuthorizationService(metody, logger,
        readService);
    assertFalse(aut.autoryzuj(dane, hash));
  }

  @Test
  public void testAutoryzujFalsePiecMetodJedenFalse() {
    przygotujWieleMetodAutoryzacji(true, true, true, false, true);
    AuthorizationService aut = new AuthorizationService(metody, logger,
        readService);
    assertFalse(aut.autoryzuj(dane, hash));
  }

  @Test
  public void testAutoryzujTruePiecMetodJedenFalse() {
    przygotujWieleMetodAutoryzacji(true, true, true, true, true);
    AuthorizationService aut = new AuthorizationService(metody, logger,
        readService);
    assertTrue(aut.autoryzuj(dane, hash));
  }

  private void przygotujWieleMetodAutoryzacji(boolean... wyniki) {
    for (boolean wynik : wyniki) {
      przygotujJednaMetodeAutoryzacji(wynik);
    }
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
