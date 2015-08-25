package czytnik.tests;

import java.util.Observable;
import java.util.concurrent.Semaphore;

import static org.junit.Assert.assertArrayEquals;

import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;

import socket.HashDaneCzytnikaPair;
import algorytm.HashUzytkownika;
import czytnik.DaneCzytnika;
import czytnik.DodawaczOdciskow;

public class DodawaczOdciskowTest extends ObserwatorCzytnikaTest {
  DodawaczOdciskow dodawacz;
  private Semaphore semafor;
  int iloscPowtorzen;

  @Before
  public void setUp() throws Exception {
    prepareHashFactory();
    iloscPowtorzen = 3;
    semafor = mock(Semaphore.class);
    dodawacz = new DodawaczOdciskow(hashFactory, semafor, iloscPowtorzen);
    dane = mock(DaneCzytnika.class);
    observable = mock(Observable.class);

  }

  @Test(expected = UnsupportedOperationException.class)
  public void testGetDane() {
    dodawacz.getDane();
  }

  @Test
  public void testWywolaniaSemafora() {
    HashDaneCzytnikaPair pair = stworzHash(1, 2, 3, 4, 5);
    dodawacz.update(observable, pair);
    verify(semafor).release();
    try {
      dodawacz.getHash();
      verify(semafor).acquire(iloscPowtorzen);

    } catch (InterruptedException e) {
      failPoWyjatku(e);
    }
  }

  @Test
  public void testJedenHash() {
    HashDaneCzytnikaPair pair = stworzHash(1, 2, 3, 4, 5);
    dodawacz.update(observable, pair);
    try {
      HashUzytkownika wynik = dodawacz.getHash();
      assertArrayEquals(pair.hash.getData(), wynik.getData(), 0.0001);
    } catch (InterruptedException e) {
      failPoWyjatku(e);
    }
  }

  @Test
  public void testTrzyHashe() {
    HashDaneCzytnikaPair pierwszy = stworzHash(1, 1, 1, 1);
    HashDaneCzytnikaPair drugi = stworzHash(2, 2, 2, 2);
    HashDaneCzytnikaPair trzeci = stworzHash(0, 1, 2, 1.5);
    double[] spodziewanyWynik = { 1, 1.333333, 1.66666, 1.5 };
    dodawacz.update(observable, pierwszy);
    dodawacz.update(observable, drugi);
    dodawacz.update(observable, trzeci);
    try {
      HashUzytkownika hashWynikowy = dodawacz.getHash();
      assertArrayEquals(spodziewanyWynik, hashWynikowy.getData(), 0.001);
    } catch (InterruptedException e) {
      failPoWyjatku(e);
    }
  }
}
