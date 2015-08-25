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
  public void testWywolaniaSemafora() throws InterruptedException {
    HashDaneCzytnikaPair pair = stworzHash(1, 2, 3, 4, 5);
    dodawacz.update(observable, pair);
    verify(semafor).release();
    dodawacz.getHash();
    verify(semafor).acquire(iloscPowtorzen);

  }

  @Test
  public void testJedenHash() throws InterruptedException {
    HashDaneCzytnikaPair pair = stworzHash(1, 2, 3, 4, 5);
    dodawacz.update(observable, pair);
    HashUzytkownika wynik = dodawacz.getHash();
    assertArrayEquals(pair.hash.getData(), wynik.getData(), 0.0001);
  }

  @Test
  public void testTrzyHashe() throws InterruptedException {
    HashDaneCzytnikaPair pierwszy = stworzHash(1, 1, 1, 1);
    HashDaneCzytnikaPair drugi = stworzHash(2, 2, 2, 2);
    HashDaneCzytnikaPair trzeci = stworzHash(0, 1, 2, 1.5);
    double[] spodziewanyWynik = { 1, 1.333333, 1.66666, 1.5 };
    dodawacz.update(observable, pierwszy);
    dodawacz.update(observable, drugi);
    dodawacz.update(observable, trzeci);
    HashUzytkownika hashWynikowy = dodawacz.getHash();
    assertArrayEquals(spodziewanyWynik, hashWynikowy.getData(), 0.001);
  }

  @Test(expected = IllegalStateException.class)
  public void testBrakHashy() throws InterruptedException {
    dodawacz.getHash();
  }

  @Test(expected = IllegalStateException.class)
  public void testInvalid() throws InterruptedException {
    HashDaneCzytnikaPair pair = stworzHash(1, 2, 3, 4, 5);
    pair.valid = false;
    dodawacz.update(observable, pair);
    dodawacz.getHash();
  }

  @Test
  public void testInvalidValid() throws InterruptedException {
    HashDaneCzytnikaPair pair = stworzHash(1, 2, 3, 4, 5);
    pair.valid = false;
    dodawacz.update(observable, pair);
    pair = stworzHash(5, 4, 3, 2, 1);
    dodawacz.update(observable, pair);
    HashUzytkownika wynik = dodawacz.getHash();
    assertArrayEquals(pair.hash.getData(), wynik.getData(), 0.0001);
  }

  @Test
  public void testValidInvalid() throws InterruptedException {
    HashDaneCzytnikaPair pair = stworzHash(1, 2, 3, 4, 5);
    double[] spodziewany = pair.hash.getData();
    dodawacz.update(observable, pair);
    pair = stworzHash(5, 4, 3, 2, 1);
    pair.valid = false;
    dodawacz.update(observable, pair);
    HashUzytkownika wynik = dodawacz.getHash();
    assertArrayEquals(spodziewany, wynik.getData(), 0.0001);
  }
}
