package baza_danych.tests;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import baza_danych.model.ParaCzytnikow;

public class ParaCzytnikowTest {
  int wiekszyCzytnik;
  int mniejszyCzytnik;
  private ParaCzytnikow para;

  @Before
  public void setUp() throws Exception {
    wiekszyCzytnik = 0;
    mniejszyCzytnik = 0;
  }

  @Test
  public void testPierwszyWiekszy() {
    wiekszyCzytnik = 1;
    mniejszyCzytnik = 0;
    para = new ParaCzytnikow(wiekszyCzytnik, mniejszyCzytnik);
    assertEquals(mniejszyCzytnik, para.pierwszyCzytnik);
    assertEquals(wiekszyCzytnik, para.drugiCzytnik);
  }

  @Test
  public void testDrugiWiekszy() {
    wiekszyCzytnik = 1;
    mniejszyCzytnik = 0;
    para = new ParaCzytnikow(mniejszyCzytnik, wiekszyCzytnik);
    assertEquals(mniejszyCzytnik, para.pierwszyCzytnik);
    assertEquals(wiekszyCzytnik, para.drugiCzytnik);
  }

  @Test
  public void testTakieSame() {
    wiekszyCzytnik = 1;
    mniejszyCzytnik = 1;
    para = new ParaCzytnikow(mniejszyCzytnik, wiekszyCzytnik);
    assertEquals(mniejszyCzytnik, para.pierwszyCzytnik);
    assertEquals(wiekszyCzytnik, para.drugiCzytnik);
  }

  @Test
  public void testToString() {
    String pierwszy = "(1, 2)", drugi = "(1, 1)";
    para = new ParaCzytnikow(1, 2);
    assertEquals(pierwszy, para.toString());
    para = new ParaCzytnikow(2, 1);
    assertEquals(pierwszy, para.toString());
    para = new ParaCzytnikow(1, 1);
    assertEquals(drugi, para.toString());

  }
}
