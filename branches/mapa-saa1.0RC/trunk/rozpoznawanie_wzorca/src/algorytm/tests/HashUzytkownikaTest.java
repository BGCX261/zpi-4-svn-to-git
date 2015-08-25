package algorytm.tests;

import static org.junit.Assert.assertEquals;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import algorytm.AlgorytmModule;
import algorytm.HashUzytkownika;
import algorytm.HashUzytkownikaFactory;

import com.google.inject.Guice;
import com.google.inject.Injector;

public class HashUzytkownikaTest {
  String testString = "1 2 2.0 3 4 5 6.1 7 8 9 10 11 12.01 13 14 15 16";
  double[] testData = { 16, 15, 14, 13, 12, 11.11, 10.01, 9, 8, 7, 6, 5, 4, 3,
      2, 1 };

  @Before
  public void setUp() throws Exception {
  }

  @Test
  public void testHashUzytkownikaString() {
    HashUzytkownika hash = new HashUzytkownika(testString, 17);
    double[] spodziewane = { 1, 2, 2, 3, 4, 5, 6.1, 7, 8, 9, 10, 11, 12.01, 13,
        14, 15, 16 };
    Assert.assertArrayEquals(spodziewane, hash.getData(), 0.0001);
  }

  @Test
  public void testWyliczOdlegloscTakiSamHash() {
    HashUzytkownika hash = new HashUzytkownika(testString, 17);
    assertEquals(0, hash.wyliczOdleglosc(hash), 0.0001);
  }

  @Test
  public void testZwrotnosci() {
    HashUzytkownika hash = new HashUzytkownika(testString, 17);
    HashUzytkownika drugiHash = new HashUzytkownika(testData, 17);
    assertEquals(hash.wyliczOdleglosc(drugiHash),
        drugiHash.wyliczOdleglosc(hash), 0.0001);
  }

  @Test
  public void testObliczen() {
    double[] dane = { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0 };
    double[] drugieDane = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
    HashUzytkownika hash = new HashUzytkownika(dane, 17);
    HashUzytkownika zerowy = new HashUzytkownika(drugieDane, 17);
    assertEquals(4, hash.wyliczOdleglosc(zerowy), 0.0001);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testZaKrotkiString() {
    String zaKrotki = testString.substring(5);
    new HashUzytkownika(zaKrotki, 17);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testStringZeZlymZnakiem() {
    String zaKrotki = testString.replace('1', 'x');
    new HashUzytkownika(zaKrotki, 17);
  }

  @Test
  public void testGetRozmiar() {
    HashUzytkownika hash = new HashUzytkownika(testData, testData.length);
    assertEquals(testData.length, hash.getRozmiar());
    hash = new HashUzytkownika("1 1 1", 3);
    assertEquals(3, hash.getRozmiar());
  }

  @Test
  public void guiceCreateInstancePassTest() {
    Injector inject = Guice.createInjector(new AlgorytmModule());
    HashUzytkownikaFactory hashFactory = inject
        .getInstance(HashUzytkownikaFactory.class);
    HashUzytkownika hash = hashFactory.createHashUzytkownika(testString);
    Assert.assertNotNull(hash);
    assertEquals(17, hash.getData().length);
  }
}
