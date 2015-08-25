package algorytm.tests;

import static org.junit.Assert.*;

import org.junit.Test;

import algorytm.SektoryzatorObrazu;

/**
 * Testy klasy dzielacej na sektory
 * @author elistan
 *
 */
public class SektoryzatorObrazuTest {

  /**
   * Testy srodkowego sektora
   */
  @Test
  public void testSektorSrodek() {
    int rozmiar = 10;
    SektoryzatorObrazu sek = new SektoryzatorObrazu(rozmiar);
    assertEquals(16, sek.getSector(5, 5));
    assertEquals(16, sek.getSector(5, 4));
    assertEquals(16, sek.getSector(4, 5));
    assertEquals(16, sek.getSector(4, 4));
  }
  /**
   * Testy pierwszej cwiartki ukladu wspolrzednych
   */
  @Test
  public void testSektorCwiartka1() {
    int rozmiar = 10;
    SektoryzatorObrazu sek = new SektoryzatorObrazu(rozmiar);
    assertEquals(0, sek.getSector(3, 1));
    assertEquals(0, sek.getSector(3, 0));
    assertEquals(0, sek.getSector(4, 3));
    assertEquals(0, sek.getSector(4, 2));
    assertEquals(0, sek.getSector(4, 1));
    assertEquals(0, sek.getSector(4, 0));
    assertEquals(1, sek.getSector(1, 0));
    assertEquals(1, sek.getSector(2, 0));
    assertEquals(1, sek.getSector(2, 1));
    assertEquals(1, sek.getSector(3, 2));
    assertEquals(2, sek.getSector(0, 0));
    assertEquals(2, sek.getSector(1, 1));
    assertEquals(2, sek.getSector(2, 2));
    assertEquals(2, sek.getSector(3, 3));
    assertEquals(2, sek.getSector(0, 1));
    assertEquals(3, sek.getSector(0, 3));
  }
  /**
   * Testy drugiej cwiartki ukladu wspolrzednych
   */
  @Test
  public void testSektorCwiartka2() {
    int rozmiar = 10;
    SektoryzatorObrazu sek = new SektoryzatorObrazu(rozmiar);
    assertEquals(4, sek.getSector(0, 6));
    assertEquals(5, sek.getSector(2, 7));
    assertEquals(6, sek.getSector(2, 8));
    assertEquals(7, sek.getSector(3, 8));
  }
  /**
   * Testy trzeciej cwiartki ukladu wspolrzednych
   */
  @Test
  public void testSektorCwiartka3() {
    int rozmiar = 10;
    SektoryzatorObrazu sek = new SektoryzatorObrazu(rozmiar);
    assertEquals(8, sek.getSector(5, 9));
    assertEquals(9, sek.getSector(8, 8));
    assertEquals(10, sek.getSector(8, 7));
    assertEquals(11, sek.getSector(9, 5));
  }
  /**
   * Testy czwartej cwiartki
   */
  @Test
  public void testSektorCwiartka4() {
    int rozmiar = 10;
    SektoryzatorObrazu sek = new SektoryzatorObrazu(rozmiar);
    assertEquals(12, sek.getSector(9, 4));
    assertEquals(13, sek.getSector(9, 1));
    assertEquals(14, sek.getSector(7, 0));
    assertEquals(15, sek.getSector(5, 0));
  }
}
