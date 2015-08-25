package algorytm;

import com.google.inject.Inject;
import com.google.inject.name.Named;

import edu.emory.mathcs.jtransforms.fft.DoubleFFT_2D;

/**
 * Klasa przetwarzajaca obraz - wymaga solidnej refaktoryzacji
 * 
 * @author elistan
 * 
 */
public class FeatureExtractor {
  private ProviderTransformacji provider;
  private int docelowyRozmiarObrazu;
  private HashUzytkownikaFactory hashFactory;
  private ObrazFactory fabrykaObrazow;
  private SektoryzatorObrazu sektoryzator;

  /**
   * 
   * @param provider
   *          Klasa dostarczajaca metody transformacji fouriera obrazu
   * @param factory
   *          Klasa tworzaca hashe uzytkownika
   * @param magnitudeSize
   *          Rozmiar widma brany pod uwage do analizy
   * @param fabrykaObrazow
   *          Fabryka tworzaca obrazy na podstawie tablic pikseli
   * @param sektor
   *          Metoda podzialu obrazu na sektory
   */
  @Inject
  public FeatureExtractor(ProviderTransformacji provider,
      HashUzytkownikaFactory factory, @Named("Magnitude") int magnitudeSize,
      ObrazFactory fabrykaObrazow, SektoryzatorObrazu sektor) {
    this.provider = provider;
    this.hashFactory = factory;
    this.docelowyRozmiarObrazu = magnitudeSize;
    this.fabrykaObrazow = fabrykaObrazow;
    this.sektoryzator = sektor;
  }

  /**
   * Oblicza moc widma na podstawie transformacji fouriera obrazu
   * 
   * @param obrazPoTransformacji
   * 
   * @return Wyliczony obraz widma mocy
   */
  public Obraz calculateMagnitude(Obraz obrazPoTransformacji) {
    double[][] mocWidmaObrazu = new double[obrazPoTransformacji.getWysokosc()][obrazPoTransformacji
        .getSzerokosc() / 2];
    for (int i = 0; i < obrazPoTransformacji.getWysokosc(); i++) {
      for (int j = 0; j < obrazPoTransformacji.getSzerokosc() / 2; j++) {
        double pikselRzeczywisty = obrazPoTransformacji.getPikselAt(i, 2 * j);
        double pikselUrojony = obrazPoTransformacji.getPikselAt(i, 2 * j + 1);
        mocWidmaObrazu[i][j] = Math
            .sqrt((pikselRzeczywisty * pikselRzeczywisty)
                + (pikselUrojony * pikselUrojony));
        mocWidmaObrazu[i][j] = 10 * Math.log10(1 + mocWidmaObrazu[i][j]);
      }
    }
    return fabrykaObrazow.generujObraz(mocWidmaObrazu);
  }

  /**
   * Tworzy tablice cech wyekstrahowanych z obrazu
   * 
   * @param obraz
   *          Obraz z ktorego wygenerowane beda cechy
   * @return zbior cech
   */
  public double[] generateFeatures(Obraz obraz) {
    int sectorCounts[] = new int[17];
    double[] cechy = new double[17];
    for (int i = 0; i < obraz.getWysokosc(); i++) {
      for (int j = 0; j < obraz.getSzerokosc(); j++) {
        int sektor = sektoryzator.getSector(i, j);
        cechy[sektor] += obraz.getPikselAt(i, j);
        sectorCounts[sektor]++;
      }
    }
    for (int i = 0; i < 17; i++) {
      if (sectorCounts[i] != 0)
        cechy[i] /= sectorCounts[i];
    }
    return cechy;
  }

  /**
   * Przeksztalca obraz za pomoca transformaty fouriera
   * 
   * @param obrazDoTransformacji
   *          Obraz ktory bedzie przetransformowany
   * 
   * @return Obraz po transformacji fouriera
   */
  public Obraz performFourierTransform(Obraz obrazDoTransformacji) {
    DoubleFFT_2D transformacja = provider
        .provide(obrazDoTransformacji.getWysokosc(),
            obrazDoTransformacji.getSzerokosc());
    double[][] pikselePoTransformacji = new double[obrazDoTransformacji
        .getWysokosc()][2 * obrazDoTransformacji.getSzerokosc()];
    for (int i = 0; i < obrazDoTransformacji.getWysokosc(); i++) {
      for (int j = 0; j < obrazDoTransformacji.getSzerokosc(); j++) {
        pikselePoTransformacji[i][2 * j] = obrazDoTransformacji.getPikselAt(i,
            j);
      }
    }
    transformacja.complexForward(pikselePoTransformacji);
    return fabrykaObrazow.generujObraz(pikselePoTransformacji);
  }

  /**
   * Ekstrahuje cechy z obrazu wykorzystujac transformate fouriera
   * 
   * @param dane
   *          Tablica z obrazem w formacie pgm
   * @return Wyekstrahowane cechy
   */
  public HashUzytkownika przetworzObraz(int[][] dane) {
    Obraz przetransformowany = performFourierTransform(fabrykaObrazow
        .generujObraz(dane));
    Obraz mocWidma = calculateMagnitude(przetransformowany);
    przesunZerowaCzestotliwoscNaSrodek(mocWidma);
    mocWidma.przytnijDoWymiaru(docelowyRozmiarObrazu);
    double[] cechy = generateFeatures(mocWidma);
    return hashFactory.createHashUzytkownika(cechy);
  }

  /**
   * Przesuwa zerowa czestotliwosc na obrazie na srodek obrazu
   * 
   * @param obraz
   *          Obraz do przetransformowania
   */
  public void przesunZerowaCzestotliwoscNaSrodek(Obraz obraz) {
    for (int y = 0; y < obraz.getWysokosc(); y++) {
      for (int x = 0; x < obraz.getSzerokosc() / 2; x++) {
        zamienPikselNaPrzeciwlelgy(obraz, y, x);
      }
    }
  }

  private void zamienPikselNaPrzeciwlelgy(Obraz obraz, int y, int x) {
    double staraWartosc = obraz.getPikselAt(y, x);
    int przeciwleglaWspolrzednaY;
    if (y < obraz.getWysokosc() / 2)
      przeciwleglaWspolrzednaY = y + obraz.getWysokosc() / 2;
    else
      przeciwleglaWspolrzednaY = y - obraz.getWysokosc() / 2;
    int przeciwleglaWspolrzednaX = x + obraz.getSzerokosc() / 2;
    double nowaWartosc = obraz.getPikselAt(przeciwleglaWspolrzednaY,
        przeciwleglaWspolrzednaX);
    obraz.setPikselAt(y, x, nowaWartosc);
    obraz.setPikselAt(przeciwleglaWspolrzednaY, przeciwleglaWspolrzednaX,
        staraWartosc);
  }

}
