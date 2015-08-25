package czytnik.tests;

import java.util.Observable;
import java.util.concurrent.Semaphore;

import static org.junit.Assert.*;

import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;

import socket.HashDaneCzytnikaPair;
import algorytm.HashUzytkownika;
import czytnik.DaneCzytnika;
import czytnik.WeryfikatorOdciskow;

public class WeryfikatorOdciskowTest extends ObserwatorCzytnikaTest {
  WeryfikatorOdciskow weryfikator;
  Semaphore semafor;
  int iloscPowtorzen;

  @Before
  public void setUp() throws Exception {
    semafor = mock(Semaphore.class);
    iloscPowtorzen = 1;
    weryfikator = new WeryfikatorOdciskow(semafor, iloscPowtorzen);
    prepareHashFactory();
    observable = mock(Observable.class);
    dane = mock(DaneCzytnika.class);
  }

  @Test(expected = IllegalStateException.class)
  public void testZbytWczesnyOdczytDanychCzytnika() {
    weryfikator.getDane();
  }

  @Test
  public void testZbytWczesnyOdczytHasha() {
    HashDaneCzytnikaPair pair = stworzHash(1, 2, 3, 4);
    weryfikator.update(observable, pair);
    verify(semafor).release();
    try {
      weryfikator.getHash();
      verify(semafor).acquire(iloscPowtorzen);
    } catch (InterruptedException e) {
      failPoWyjatku(e);
    }
  }

  @Test
  public void testUpdateJedenHash() {
    HashDaneCzytnikaPair pair = stworzHash(1, 2, 3, 4);
    double[] spodziewane = { 1, 2, 3, 4 };
    weryfikator.update(observable, pair);
    try {
      HashUzytkownika wynik = weryfikator.getHash();
      assertArrayEquals(spodziewane, wynik.getData(), 0.0001);
    } catch (InterruptedException e) {
      failPoWyjatku(e);
    }

  }

  @Test
  public void testUpdateDwaHashe() {

    HashDaneCzytnikaPair pair = stworzHash(1, 2, 3, 4);

    weryfikator.update(observable, pair);
    pair = stworzHash(7, 7, 7, 7);
    weryfikator.update(observable, pair);
    double[] pierwszeSpodziewane = { 1, 2, 3, 4 };
    double[] drugieSpodziewane = { 7, 7, 7, 7 };
    try {
      HashUzytkownika wynik = weryfikator.getHash();
      assertArrayEquals(pierwszeSpodziewane, wynik.getData(), 0.0001);
      wynik = weryfikator.getHash();
      assertArrayEquals(drugieSpodziewane, wynik.getData(), 0.0001);
    } catch (InterruptedException e) {
      failPoWyjatku(e);
    }

  }

  @Test
  public void testGetDane() {
    HashDaneCzytnikaPair pair = stworzHash(1, 2, 3, 4);
    weryfikator.update(observable, pair);
    DaneCzytnika dane = weryfikator.getDane();
    assertEquals(this.dane, dane);
  }
}
