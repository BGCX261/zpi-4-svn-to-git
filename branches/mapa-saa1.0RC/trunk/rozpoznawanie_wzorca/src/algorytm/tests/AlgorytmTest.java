package algorytm.tests;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.Assert.assertEquals;

import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import algorytm.Algorytm;
import algorytm.HashUzytkownika;

public class AlgorytmTest {

  private Algorytm algorytm;
  private HashUzytkownika hashDoSprawdzenia;
  private List<HashUzytkownika> listaHashy;
  private double threshold = 2.0;

  @Before
  public void setUp() throws Exception {
    algorytm = new Algorytm(threshold);
    hashDoSprawdzenia = Mockito.mock(HashUzytkownika.class);
    listaHashy = new ArrayList<HashUzytkownika>();

  }

  @Test(expected = NoSuchElementException.class)
  public void testZnajdzNajpodobniejszyPustaLista() {
    algorytm.znajdzNajpodobniejszy(hashDoSprawdzenia, listaHashy);
  }

  @Test(expected = NoSuchElementException.class)
  public void testBezNajpodobniejszegoJedenElement() {
    dodajHashDoListy(threshold + 1);

    algorytm.znajdzNajpodobniejszy(hashDoSprawdzenia, listaHashy);
  }

  @Test(expected = NoSuchElementException.class)
  public void testBezNajpodobniejszegoKilkaElementow() {
    dodajWieleHashyDoListy(3.0, 3.5, 2.01, 2.000001, 5.0);
    algorytm.znajdzNajpodobniejszy(hashDoSprawdzenia, listaHashy);
  }

  @Test
  public void testJedenNajpodobniejszy() {
    dodajHashDoListy(1.5);
    assertEquals(0,
        algorytm.znajdzNajpodobniejszy(hashDoSprawdzenia, listaHashy));
  }

  @Test
  public void testJedenNajpodobniejszyWieleNiepodobnych() {
    dodajWieleHashyDoListy(3, 2, 1.5, 4, 5, 6);
    assertEquals(2,
        algorytm.znajdzNajpodobniejszy(hashDoSprawdzenia, listaHashy));
  }

  @Test
  public void testKilkaPasujacychBezNiepodobnych() {
    dodajWieleHashyDoListy(1, 0.5, 1.9, 0.001, 0.01);
    assertEquals(3,
        algorytm.znajdzNajpodobniejszy(hashDoSprawdzenia, listaHashy));
  }

  @Test
  public void testKilkaPasujacychKilkaNiepasujacych() {
    dodajWieleHashyDoListy(1, 3, 0.5, 1.9, 5, 2, 0.001, 11, 0.01);
    assertEquals(6,
        algorytm.znajdzNajpodobniejszy(hashDoSprawdzenia, listaHashy));
  }

  private void dodajHashDoListy(double odleglosc) {
    HashUzytkownika hash = mock(HashUzytkownika.class);
    when(hash.wyliczOdleglosc(hashDoSprawdzenia)).thenReturn(odleglosc);
    when(hashDoSprawdzenia.wyliczOdleglosc(hash)).thenReturn(odleglosc);
    listaHashy.add(hash);
  }

  private void dodajWieleHashyDoListy(double... wartosci) {
    for (double wartosc : wartosci) {
      dodajHashDoListy(wartosc);
    }
  }
}
