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
  public void testZbytWczesnyOdczytHasha() throws InterruptedException {
    HashDaneCzytnikaPair pair = stworzHash(1, 2, 3, 4);
    weryfikator.update(observable, pair);
    verify(semafor).release();
    weryfikator.getHash();
    verify(semafor).acquire(iloscPowtorzen);
  }

  @Test
  public void testUpdateJedenHash() throws InterruptedException {
    HashDaneCzytnikaPair pair = stworzHash(1, 2, 3, 4);
    double[] spodziewane = { 1, 2, 3, 4 };
    weryfikator.update(observable, pair);
    HashUzytkownika wynik = weryfikator.getHash();
    assertArrayEquals(spodziewane, wynik.getData(), 0.0001);

  }

  @Test
  public void testUpdateDwaHashe() throws InterruptedException {

    HashDaneCzytnikaPair pair = stworzHash(1, 2, 3, 4);

    weryfikator.update(observable, pair);
    pair = stworzHash(7, 7, 7, 7);
    weryfikator.update(observable, pair);
    double[] pierwszeSpodziewane = { 1, 2, 3, 4 };
    double[] drugieSpodziewane = { 7, 7, 7, 7 };
    HashUzytkownika wynik = weryfikator.getHash();
    assertArrayEquals(pierwszeSpodziewane, wynik.getData(), 0.0001);
    wynik = weryfikator.getHash();
    assertArrayEquals(drugieSpodziewane, wynik.getData(), 0.0001);

  }

  @Test
  public void testGetDane() {
    HashDaneCzytnikaPair pair = stworzHash(1, 2, 3, 4);
    weryfikator.update(observable, pair);
    DaneCzytnika dane = weryfikator.getDane();
    assertEquals(this.dane, dane);
  }

  @Test(expected = IllegalStateException.class)
  public void testBrakHashy() throws InterruptedException {
    weryfikator.getHash();
  }

  @Test(expected = IllegalStateException.class)
  public void testInvalid() throws InterruptedException {
    HashDaneCzytnikaPair pair = stworzHash(1, 2, 3, 4);
    pair.valid = false;
    weryfikator.update(observable, pair);
    weryfikator.getHash();
  }

  @Test
  public void testInvalidValid() throws InterruptedException {
    HashDaneCzytnikaPair pair = stworzHash(1, 2, 3, 4);
    pair.valid = false;
    double[] spodziewane = { 4, 3, 2, 1 };
    weryfikator.update(observable, pair);
    pair = stworzHash(4, 3, 2, 1);
    weryfikator.update(observable, pair);
    HashUzytkownika wynik = weryfikator.getHash();
    assertArrayEquals(spodziewane, wynik.getData(), 0.0001);
  }

  @Test
  public void testValidInvalid() throws InterruptedException {
    HashDaneCzytnikaPair pair = stworzHash(1, 2, 3, 4);
    double[] spodziewane = { 1, 2, 3, 4 };
    weryfikator.update(observable, pair);
    pair = stworzHash(4, 3, 2, 1);
    pair.valid = false;
    weryfikator.update(observable, pair);
    HashUzytkownika wynik = weryfikator.getHash();
    assertArrayEquals(spodziewane, wynik.getData(), 0.0001);
  }
}
