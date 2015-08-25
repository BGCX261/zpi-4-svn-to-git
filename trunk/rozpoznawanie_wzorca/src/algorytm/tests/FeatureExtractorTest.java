package algorytm.tests;

import static org.junit.Assert.*;

import static org.mockito.Matchers.any;

import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import algorytm.FeatureExtractor;
import algorytm.HashUzytkownika;
import algorytm.HashUzytkownikaFactory;
import algorytm.Obraz;
import algorytm.ObrazFactory;
import algorytm.ProviderTransformacji;
import algorytm.SektoryzatorObrazu;
import edu.emory.mathcs.jtransforms.fft.DoubleFFT_2D;

/**
 * Test klasy ekstrahujacej cechy z obrazu
 * 
 * @author elistan
 * 
 */
public class FeatureExtractorTest {
  FeatureExtractor extractor;
  ProviderTransformacji provider;
  HashUzytkownikaFactory factory;
  int magnitudeSize;
  ObrazFactory fabrykaObrazow;

  /**
   * Przygotowanie przed kazdym testem
   */
  @Before
  public void setUp() {
    provider = mock(ProviderTransformacji.class);
    factory = mock(HashUzytkownikaFactory.class);
    magnitudeSize = 2;
    fabrykaObrazow = mock(ObrazFactory.class);
    when(fabrykaObrazow.generujObraz((double[][]) any())).then(
        new Answer<Obraz>() {

          @Override
          public Obraz answer(InvocationOnMock invocation) throws Throwable {
            return new Obraz((double[][]) invocation.getArguments()[0]);
          }
        });
    when(fabrykaObrazow.generujObraz((int[][]) any())).then(
        new Answer<Obraz>() {

          @Override
          public Obraz answer(InvocationOnMock invocation) throws Throwable {
            return new Obraz((int[][]) invocation.getArguments()[0]);
          }
        });
    extractor = new FeatureExtractor(provider, factory, magnitudeSize,
        fabrykaObrazow, new SektoryzatorObrazu(4));
  }

  /**
   * Sprawdza poprawnosc wyliczenia mocy widma
   */
  @Test
  public void testCalculateMagnitude() {
    double[][] daneObrazu = { { 1, 0, 0, 1, 1, 1 }, { 0, 0, 2, 2, 10, 10 },
        { 9, 9, 1, 1, 0, 0 } };
    Obraz obrazDoWyliczenia = new Obraz(daneObrazu);
    Obraz wynik = extractor.calculateMagnitude(obrazDoWyliczenia);
    assertEquals(wynik.getPikselAt(0, 0), wynik.getPikselAt(0, 1), 0.0001);
    double[][] spodziewaneWyniki = {
        { calculateMagnitude(1, 0), calculateMagnitude(0, 1),
            calculateMagnitude(1, 1) },
        { calculateMagnitude(0, 0), calculateMagnitude(2, 2),
            calculateMagnitude(10, 10) },
        { calculateMagnitude(9, 9), calculateMagnitude(1, 1),
            calculateMagnitude(0, 0) } };
    porownajOrazZTablica(wynik, spodziewaneWyniki);
  }

  private void porownajOrazZTablica(Obraz wynik, double[][] spodziewaneWyniki) {
    for (int i = 0; i < spodziewaneWyniki.length; i++) {
      for (int j = 0; j < spodziewaneWyniki[i].length; j++) {
        assertEquals("i=" + i + " j=" + j, spodziewaneWyniki[i][j],
            wynik.getPikselAt(i, j), 0.00001);
      }
    }
  }

  // Wykorzystany wzor
  // 10 * log(1 + sqrt(im(x)^2+re(x)^2))
  private double calculateMagnitude(double real, double imaginary) {
    return 10 * Math.log10(1 + Math.sqrt((imaginary * imaginary)
        + (real * real)));
  }

  /**
   * Testuje generowanie cech z obrazu
   */
  @Test
  public void testGenerateFeatures() {
    int tab[][] = { { 0, 1, 2, 3 }, { 4, 5, 6, 7 }, { 8, 9, 10, 11 },
        { 12, 13, 14, 15 } };
    double[] wynik = extractor.generateFeatures(new Obraz(tab));
    double[] spodziewany = { 4, 0, 0, 1, 2, 3, 0, 7, 11, 15, 0, 14, 13, 0, 12,
        8, 7.5 };
    assertArrayEquals(spodziewany, wynik, 0.0001);
  }

  /**
   * Sprawdza czy algorytm poprawnie wywoluje metode z biblioteki do
   * transformacji fouriera
   */
  @Test
  public void testPerformFourierTransform() {
    int tab[][] = { { 1, 1 }, { 1, 1 } };
    double[][] zwroconyObraz = { { 3, 3, 1, 1 }, { 2, 3, 1, 2 } };
    Obraz obrazDoTransformacji = new Obraz(tab);
    DoubleFFT_2D transformMock = mock(DoubleFFT_2D.class);
    when(provider.provide(2, 2)).thenReturn(transformMock);
    doAnswer(new Answer<Void>() {

      @Override
      public Void answer(InvocationOnMock invocation) throws Throwable {
        double[][] obrazDoTransformacji = (double[][]) invocation
            .getArguments()[0];
        double[][] oczekiwany = { { 1, 0, 1, 0 }, { 1, 0, 1, 0 } };
        assertArrayEquals(oczekiwany[0], obrazDoTransformacji[0], 0.001);
        assertArrayEquals(oczekiwany[1], obrazDoTransformacji[1], 0.001);
        obrazDoTransformacji[0][0] = 3;
        obrazDoTransformacji[0][1] = 3;
        obrazDoTransformacji[0][2] = 1;
        obrazDoTransformacji[0][3] = 1;
        obrazDoTransformacji[1][0] = 2;
        obrazDoTransformacji[1][1] = 3;
        obrazDoTransformacji[1][2] = 1;
        obrazDoTransformacji[1][3] = 2;
        return null;
      }
    }).when(transformMock).complexForward((double[][]) any());
    Obraz wynik = extractor.performFourierTransform(obrazDoTransformacji);
    porownajOrazZTablica(wynik, zwroconyObraz);
  }

  /**
   * Sprawdza czy
   */
  @Test
  public void testPrzetworzObraz() {
    int[][] dane = { { 9, 0, 99, 0 }, { 0, 0, 0, 0 }, { 9, 99, 99, 9 },
        { 0, 0, 0, 0 } };

    DoubleFFT_2D transformMock = mock(DoubleFFT_2D.class);
    when(provider.provide(4, 4)).thenReturn(transformMock);
    doAnswer(new Answer<Void>() {

      @Override
      public Void answer(InvocationOnMock invocation) throws Throwable {
        double[][] obrazDoTransformacji = (double[][]) invocation
            .getArguments()[0];
        for (int i = 0; i < obrazDoTransformacji.length; i++) {
          for (int j = 0; j < obrazDoTransformacji[i].length; j++) {
            boolean rzeczywista = j % 2 == 0;
            obrazDoTransformacji[i][j] = rzeczywista ? 9 : 0;
          }
        }
        return null;
      }
    }).when(transformMock).complexForward((double[][]) any());
    when(factory.createHashUzytkownika((double[]) any())).thenAnswer(
        new Answer<HashUzytkownika>() {

          @Override
          public HashUzytkownika answer(InvocationOnMock invocation)
              throws Throwable {

            return new HashUzytkownika((double[]) invocation.getArguments()[0],
                17);
          }
        });
    HashUzytkownika wynik = extractor.przetworzObraz(dane);
    double[] spodziewanyWynik = { 10.0, 0.0, 10.0, 10.0, 0.0, 0.0, 0.0, 0.0,
        0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 10.0 };
    assertArrayEquals(spodziewanyWynik, wynik.getData(), 0.001);
  }

  /**
   * Sprawdza poprawnosc przesuniecia zerowej czestotliwosci na srodek ukladu
   */
  @Test
  public void testShiftZeroFrequencyToCenter() {
    int tab[][] = { { 1, 2, 3, 4 }, { 5, 6, 7, 8 }, { 9, 10, 11, 12 },
        { 13, 14, 15, 16 } };
    double wynik[][] = { { 11, 12, 9, 10 }, { 15, 16, 13, 14 }, { 3, 4, 1, 2 },
        { 7, 8, 5, 6 } };
    Obraz doPrzetworzenia = new Obraz(tab);
    extractor.przesunZerowaCzestotliwoscNaSrodek(doPrzetworzenia);
    porownajOrazZTablica(doPrzetworzenia, wynik);
  }
}
