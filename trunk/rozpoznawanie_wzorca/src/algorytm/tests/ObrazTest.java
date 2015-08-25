package algorytm.tests;

import static org.junit.Assert.*;

import org.junit.Test;

import algorytm.Obraz;

/**
 * Testy dla klasy obraz
 * 
 * @author elistan
 * 
 */
public class ObrazTest {

  /**
   * Test dla konstruktora
   */
  @Test
  public void testConstructor() {
    int[][] tab = { { 1, 2, 3 }, { 1, 2, 3 }, { 4, 5, 6 }, { 7, 8, 9 } };
    Obraz obraz = new Obraz(tab);
    for (int i = 0; i < tab.length; i++) {
      for (int j = 0; j < tab[0].length; j++) {
        assertEquals("Double: i = " + i + " j=" + j, tab[i][j],
            obraz.getPikselAt(i, j), 0.001);
      }
    }
    double[][] doubleTab = { { 0.0, 0.0, 0.0 }, { 1.2, 2.1222, 3.12 },
        { 1 / 3.0, 2.0, 3.1 } };
    obraz = new Obraz(doubleTab);
    for (int i = 0; i < doubleTab.length; i++) {
      for (int j = 0; j < doubleTab[0].length; j++) {
        assertEquals("Double: i = " + i + " j=" + j, doubleTab[i][j],
            obraz.getPikselAt(i, j), 0.001);
      }
    }
  }

  /**
   * Rozne przypadki przycinania rozmiaru obrazu
   */
  @Test
  public void testPrzytnijDoWymiaru() {
    int[][] tab = { { 1, 2, 3 }, { 2, 3, 4 }, { 4, 5, 6 }, { 7, 8, 9 } };
    Obraz obraz = new Obraz(tab);
    obraz.przytnijDoWymiaru(1);
    assertEquals(5.0, obraz.getPikselAt(0, 0), 0.0001);
    obraz = new Obraz(tab);
    obraz.przytnijDoWymiaru(2);
    assertEquals(2.0, obraz.getPikselAt(0, 0), 0.0001);
    assertEquals(4.0, obraz.getPikselAt(1, 0), 0.0001);
    assertEquals(3.0, obraz.getPikselAt(0, 1), 0.0001);
    assertEquals(5.0, obraz.getPikselAt(1, 1), 0.0001);
    obraz = new Obraz(tab);
    obraz.przytnijDoWymiaru(3);
    assertEquals(2.0, obraz.getPikselAt(0, 0), 0.0001);
    assertEquals(3.0, obraz.getPikselAt(0, 1), 0.0001);
    assertEquals(4.0, obraz.getPikselAt(0, 2), 0.0001);
    assertEquals(4.0, obraz.getPikselAt(1, 0), 0.0001);
    assertEquals(5.0, obraz.getPikselAt(1, 1), 0.0001);
    assertEquals(6.0, obraz.getPikselAt(1, 2), 0.0001);
    assertEquals(7.0, obraz.getPikselAt(2, 0), 0.0001);
    assertEquals(8.0, obraz.getPikselAt(2, 1), 0.0001);
    assertEquals(9.0, obraz.getPikselAt(2, 2), 0.0001);
    obraz = new Obraz(tab);
    obraz.przytnijDoWymiaru(4);
    assertEquals(0.0, obraz.getPikselAt(0, 0), 0.0001);
    assertEquals(1.0, obraz.getPikselAt(0, 1), 0.0001);
    assertEquals(2.0, obraz.getPikselAt(0, 2), 0.0001);
    assertEquals(3.0, obraz.getPikselAt(0, 3), 0.0001);
    assertEquals(0.0, obraz.getPikselAt(1, 0), 0.0001);
    assertEquals(2.0, obraz.getPikselAt(1, 1), 0.0001);
    assertEquals(3.0, obraz.getPikselAt(1, 2), 0.0001);
    assertEquals(4.0, obraz.getPikselAt(1, 3), 0.0001);
    assertEquals(0.0, obraz.getPikselAt(2, 0), 0.0001);
    assertEquals(4.0, obraz.getPikselAt(2, 1), 0.0001);
    assertEquals(5.0, obraz.getPikselAt(2, 2), 0.0001);
    assertEquals(6.0, obraz.getPikselAt(2, 3), 0.0001);
    assertEquals(0.0, obraz.getPikselAt(3, 0), 0.0001);
    assertEquals(7.0, obraz.getPikselAt(3, 1), 0.0001);
    assertEquals(8.0, obraz.getPikselAt(3, 2), 0.0001);
    assertEquals(9.0, obraz.getPikselAt(3, 3), 0.0001);
    int tab2[][] = { { 0, 1 }, { 2, 3 } };
    obraz = new Obraz(tab2);
    obraz.przytnijDoWymiaru(2);
    assertEquals(0.0, obraz.getPikselAt(0, 0), 0.0001);
    assertEquals(1.0, obraz.getPikselAt(0, 1), 0.0001);
    assertEquals(2.0, obraz.getPikselAt(1, 0), 0.0001);
    assertEquals(3.0, obraz.getPikselAt(1, 1), 0.0001);
    obraz.przytnijDoWymiaru(4);
    assertEquals(0.0, obraz.getPikselAt(0, 0), 0.0001);
    assertEquals(0.0, obraz.getPikselAt(0, 1), 0.0001);
    assertEquals(0.0, obraz.getPikselAt(0, 2), 0.0001);
    assertEquals(0.0, obraz.getPikselAt(0, 3), 0.0001);
    assertEquals(0.0, obraz.getPikselAt(1, 0), 0.0001);
    assertEquals(0.0, obraz.getPikselAt(1, 1), 0.0001);
    assertEquals(1.0, obraz.getPikselAt(1, 2), 0.0001);
    assertEquals(0.0, obraz.getPikselAt(1, 3), 0.0001);
    assertEquals(0.0, obraz.getPikselAt(2, 0), 0.0001);
    assertEquals(2.0, obraz.getPikselAt(2, 1), 0.0001);
    assertEquals(3.0, obraz.getPikselAt(2, 2), 0.0001);
    assertEquals(0.0, obraz.getPikselAt(2, 3), 0.0001);
    assertEquals(0.0, obraz.getPikselAt(3, 0), 0.0001);
    assertEquals(0.0, obraz.getPikselAt(3, 1), 0.0001);
    assertEquals(0.0, obraz.getPikselAt(3, 2), 0.0001);
    assertEquals(0.0, obraz.getPikselAt(3, 3), 0.0001);
    int tab3[][] = { { 1, 2, 3 }, { 4, 5, 6 }, { 7, 8, 9 } };
    obraz = new Obraz(tab3);
    obraz.przytnijDoWymiaru(1);
    assertEquals(5, obraz.getPikselAt(0, 0), 0.0001);
  }

}
