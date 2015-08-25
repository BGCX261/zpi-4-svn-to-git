package algorytm;

import com.google.inject.Inject;

/**
 * Klasa dzielaca obraz na sektory
 * @author elistan
 *
 */
public class SektoryzatorObrazu {
  private static final int SEKTOR_SRODEK_UKLADU = 16;
  private int magnitudeSize;
  private double srodek;
  /**
   * @param rozmiar Rozmiar obrazu
   */
  @Inject
  public SektoryzatorObrazu(int rozmiar){
    magnitudeSize = rozmiar;
    srodek = (magnitudeSize - 1.0) / 2.0;
  }
  /**
   * Podaje do jakiego sektora obrazu naleza podane indeksy w tablicy. 
   * Centrum ukladu wspolrzednych znajduje sie na srodku obrazu.
   * @param i
   * @param j
   * @return Znaleziony sektor od 0 - 16
   */
  public int getSector(int i, int j) {
    double y = srodek - i;
    double x = j - srodek;
    if (Math.sqrt(x * x + y * y) < 1)
      return SEKTOR_SRODEK_UKLADU;
    int cwiartka = ktoraCwiartka(x, y);
    switch (cwiartka) {
    case 0:
      if (y < -0.5 * x)
        return 0;
      if (y < -x)
        return 1;
      if (y < -2 * x)
        return 2;
      return 3;
    case 1:
      if (y >= 2 * x)
        return 4;
      if (y >= x)
        return 5;
      if (y >= 0.5 * x)
        return 6;
      return 7;
    case 2:
      if (y >= -0.5 * x)
        return 8;
      if (y >= -x)
        return 9;
      if (y >= -2 * x)
        return 10;
      return 11;
    case 3:
      if (y < 2 * x)
        return 12;
      if (y < x)
        return 13;
      if (y < 0.5 * x)
        return 14;
      return 15;
    }
    return SEKTOR_SRODEK_UKLADU;
  }

  private int ktoraCwiartka(double x, double y) {
    if (x < 0 && y >= 0)
      return 0;
    if (x >= 0 && y >= 0)
      return 1;
    if (x >= 0 && y < 0)
      return 2;
    if (x < 0 && y < 0)
      return 3;
    return SEKTOR_SRODEK_UKLADU;
  }
}
