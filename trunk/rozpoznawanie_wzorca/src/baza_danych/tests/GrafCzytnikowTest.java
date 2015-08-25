package baza_danych.tests;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import baza_danych.model.GrafCzytnikow;
import baza_danych.model.ParaCzytnikow;

public class GrafCzytnikowTest {
  List<ParaCzytnikow> paryCzytnikow;
  GrafCzytnikow graf;

  @Before
  public void setUp() throws Exception {
    paryCzytnikow = new ArrayList<ParaCzytnikow>();
  }

  private ParaCzytnikow przygotujPareCzytnikow(int pierwszy, int drugi) {
    ParaCzytnikow wynik = new ParaCzytnikow(pierwszy, drugi);
    return wynik;
  }

  private void przygotujListe(ParaCzytnikow... pary) {
    for (ParaCzytnikow para : pary) {
      paryCzytnikow.add(para);
    }
  }

  @Test
  public void testJednaPara() {
    przygotujListe(przygotujPareCzytnikow(1, 2));

    graf = new GrafCzytnikow(paryCzytnikow);

    assertTrue(graf.czyDozwolonePrzejscie(1, 2));
    assertTrue(graf.czyDozwolonePrzejscie(2, 1));
    assertFalse(graf.czyDozwolonePrzejscie(1, 3));
    assertFalse(graf.czyDozwolonePrzejscie(3, 1));
  }

  @Test
  public void testDwiePary() {
    przygotujListe(przygotujPareCzytnikow(1, 2), przygotujPareCzytnikow(1, 3));

    graf = new GrafCzytnikow(paryCzytnikow);

    assertTrue(graf.czyDozwolonePrzejscie(1, 3));
    assertTrue(graf.czyDozwolonePrzejscie(3, 1));
    assertTrue(graf.czyDozwolonePrzejscie(1, 2));
    assertTrue(graf.czyDozwolonePrzejscie(2, 1));
    assertFalse(graf.czyDozwolonePrzejscie(2, 3));
    assertFalse(graf.czyDozwolonePrzejscie(3, 2));
  }

  @Test
  public void testPiecPar() {
    przygotujListe(przygotujPareCzytnikow(1, 2), przygotujPareCzytnikow(2, 3),
        przygotujPareCzytnikow(1, 3), przygotujPareCzytnikow(5, 1),
        przygotujPareCzytnikow(4, 5));

    graf = new GrafCzytnikow(paryCzytnikow);

    assertTrue(graf.czyDozwolonePrzejscie(1, 3));
    assertTrue(graf.czyDozwolonePrzejscie(3, 1));
    assertTrue(graf.czyDozwolonePrzejscie(1, 2));
    assertTrue(graf.czyDozwolonePrzejscie(2, 1));
    assertTrue(graf.czyDozwolonePrzejscie(2, 3));
    assertTrue(graf.czyDozwolonePrzejscie(3, 2));
    assertTrue(graf.czyDozwolonePrzejscie(1, 5));
    assertTrue(graf.czyDozwolonePrzejscie(5, 1));
    assertFalse(graf.czyDozwolonePrzejscie(1, 4));
    assertFalse(graf.czyDozwolonePrzejscie(4, 1));
    assertFalse(graf.czyDozwolonePrzejscie(1, 10));
  }

  @Test
  public void testTakieSame() {
    przygotujListe(przygotujPareCzytnikow(1, 1), przygotujPareCzytnikow(2, 2));

    graf = new GrafCzytnikow(paryCzytnikow);

    assertTrue(graf.czyDozwolonePrzejscie(1, 1));
    assertTrue(graf.czyDozwolonePrzejscie(2, 2));
    assertFalse(graf.czyDozwolonePrzejscie(1, 2));
    assertFalse(graf.czyDozwolonePrzejscie(3, 3));
  }

  @Test
  public void testPrzygotujListeJednaPara() {
    przygotujListe(przygotujPareCzytnikow(1, 2));

    graf = new GrafCzytnikow(paryCzytnikow);

    List<ParaCzytnikow> wynik = graf.przygotujParyCzytnikow();
    assertEquals(paryCzytnikow, wynik);
  }

  @Test
  public void testPrzygotujListeDwiePary() {
    przygotujListe(przygotujPareCzytnikow(1, 2), przygotujPareCzytnikow(3, 1));

    graf = new GrafCzytnikow(paryCzytnikow);

    List<ParaCzytnikow> wynik = graf.przygotujParyCzytnikow();
    assertEquals(paryCzytnikow, wynik);
  }

  @Test
  public void testPrzygotujListeTrzyParyOdwrotnaKolejnosc() {
    przygotujListe(przygotujPareCzytnikow(3, 2), przygotujPareCzytnikow(3, 1),
        przygotujPareCzytnikow(1, 2));

    graf = new GrafCzytnikow(paryCzytnikow);

    List<ParaCzytnikow> wynik = graf.przygotujParyCzytnikow();
    sortujListe();
    assertEquals(paryCzytnikow, wynik);
  }

  private void sortujListe() {
    Comparator<ParaCzytnikow> comparator = new Comparator<ParaCzytnikow>() {

      @Override
      public int compare(ParaCzytnikow o1, ParaCzytnikow o2) {
        int wynik = Integer.compare(o1.pierwszyCzytnik, o2.pierwszyCzytnik);
        if (wynik == 0) {
          return Integer.compare(o1.drugiCzytnik, o2.drugiCzytnik);
        }
        return wynik;
      }
    };
    Collections.sort(paryCzytnikow, comparator);
  }

  @Test
  public void testWczytajZPlikuKilkaPar() throws IOException {
    BufferedReader reader = Mockito.mock(BufferedReader.class);
    Mockito.when(reader.readLine()).thenReturn("1;1").thenReturn("1;2")
        .thenReturn("1 ; 3").thenReturn("3 ;2").thenReturn("2; 2")
        .thenReturn(null);
    graf = new GrafCzytnikow(reader);
    przygotujListe(przygotujPareCzytnikow(1, 1), przygotujPareCzytnikow(1, 2),
        przygotujPareCzytnikow(1, 3), przygotujPareCzytnikow(3, 2),
        przygotujPareCzytnikow(2, 2));
    sortujListe();
    assertEquals(paryCzytnikow, graf.przygotujParyCzytnikow());
  }

  @Test
  public void testWczytajZPlikuJednaPara() throws IOException {
    BufferedReader reader = Mockito.mock(BufferedReader.class);
    Mockito.when(reader.readLine()).thenReturn("1;1").thenReturn(null);
    graf = new GrafCzytnikow(reader);
    przygotujListe(przygotujPareCzytnikow(1, 1));
    assertEquals(paryCzytnikow, graf.przygotujParyCzytnikow());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testWczytajZPlikuNiepoprawnyFormat() throws IOException {
    BufferedReader reader = Mockito.mock(BufferedReader.class);
    Mockito.when(reader.readLine()).thenReturn("1.1;1").thenReturn(null);
    graf = new GrafCzytnikow(reader);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testWczytajZPlikuNiepoprawnyFormat2() throws IOException {
    BufferedReader reader = Mockito.mock(BufferedReader.class);
    Mockito.when(reader.readLine()).thenReturn("1,1").thenReturn(null);
    graf = new GrafCzytnikow(reader);
  }

}
