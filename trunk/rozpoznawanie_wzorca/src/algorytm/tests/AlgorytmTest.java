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
import baza_danych.model.Uzytkownik;

public class AlgorytmTest {

  private Algorytm algorytm;
  private HashUzytkownika hashDoSprawdzenia;
  private List<Uzytkownik> listaUzytkownikow;
  private double threshold = 2.0;

  @Before
  public void setUp() throws Exception {
    algorytm = new Algorytm(threshold);
    hashDoSprawdzenia = Mockito.mock(HashUzytkownika.class);
    listaUzytkownikow = new ArrayList<Uzytkownik>();

  }

  @Test(expected = IllegalStateException.class)
  public void testZnajdzNajpodobniejszyPustaLista() {
    algorytm.znajdzNajpodobniejszegoUzytkownika(hashDoSprawdzenia,
        listaUzytkownikow);
  }

  @Test(expected = NoSuchElementException.class)
  public void testBezNajpodobniejszegoJedenElement() {
    dodajUzytkownikaDoListy(threshold + 1);

    algorytm.znajdzNajpodobniejszegoUzytkownika(hashDoSprawdzenia,
        listaUzytkownikow);
  }

  @Test(expected = NoSuchElementException.class)
  public void testBezNajpodobniejszegoKilkaElementow() {
    dodajWieluUzytkownikowDoListy(3.0, 3.5, 2.01, 2.000001, 5.0);
    algorytm.znajdzNajpodobniejszegoUzytkownika(hashDoSprawdzenia,
        listaUzytkownikow);
  }

  @Test
  public void testJedenNajpodobniejszy() {
    dodajUzytkownikaDoListy(1.5);
    assertEquals(listaUzytkownikow.get(0),
        algorytm.znajdzNajpodobniejszegoUzytkownika(hashDoSprawdzenia,
            listaUzytkownikow));
  }

  @Test
  public void testJedenNajpodobniejszyWieleNiepodobnych() {
    dodajWieluUzytkownikowDoListy(3, 2, 1.5, 4, 5, 6);
    assertEquals(listaUzytkownikow.get(2),
        algorytm.znajdzNajpodobniejszegoUzytkownika(hashDoSprawdzenia,
            listaUzytkownikow));
  }

  @Test
  public void testKilkaPasujacychBezNiepodobnych() {
    dodajWieluUzytkownikowDoListy(1, 0.5, 1.9, 0.001, 0.01);
    assertEquals(listaUzytkownikow.get(3),
        algorytm.znajdzNajpodobniejszegoUzytkownika(hashDoSprawdzenia,
            listaUzytkownikow));
  }

  @Test
  public void testKilkaPasujacychKilkaNiepasujacych() {
    dodajWieluUzytkownikowDoListy(1, 3, 0.5, 1.9, 5, 2, 0.001, 11, 0.01);
    assertEquals(listaUzytkownikow.get(6),
        algorytm.znajdzNajpodobniejszegoUzytkownika(hashDoSprawdzenia,
            listaUzytkownikow));
  }

  private void dodajUzytkownikaDoListy(double odleglosc) {
    Uzytkownik uzytkownik = new Uzytkownik(0);
    HashUzytkownika hash = mock(HashUzytkownika.class);
    when(hash.wyliczOdleglosc(hashDoSprawdzenia)).thenReturn(odleglosc);
    when(hashDoSprawdzenia.wyliczOdleglosc(hash)).thenReturn(odleglosc);
    uzytkownik.dodajHash(hash);
    listaUzytkownikow.add(uzytkownik);
  }

  private void dodajWieluUzytkownikowDoListy(double... wartosci) {
    for (double wartosc : wartosci) {
      dodajUzytkownikaDoListy(wartosc);
    }
  }
}
