package algorytm;

import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;

/**
 * Klasa reprezentujaca obraz w formacie pgm
 * @author elistan
 *
 */
public class Obraz {
  private double [][]piksele;
  private int wysokosc;
  private int szerokosc;
  /**
   * @param piksele Tablica pikseli obrazu
   */
  @AssistedInject
  public Obraz(@Assisted int[][] piksele){
    wysokosc = piksele.length;
    szerokosc = piksele[0].length;
    this.piksele = new double[wysokosc][szerokosc];
    for(int i = 0 ; i < piksele.length ; i++){
      for(int j = 0 ; j < piksele[i].length ; j++){
        this.piksele[i][j] = piksele[i][j];
      }
    }

  }
  /**
   * Tworzy obraz na podstawie pikseli w formacie liczb rzeczywistych.
   * @param piksele
   */
  @AssistedInject
  public Obraz(@Assisted double[][]piksele){
    wysokosc = piksele.length;
    szerokosc = piksele[0].length;
    this.piksele = new double[wysokosc][szerokosc];
    int numerWiersza = 0;
    for(double[] wiersz:piksele){
      System.arraycopy(wiersz,0, this.piksele[numerWiersza++], 0, wiersz.length);
    }
  }
  /**
   * Przycina obraz do zadanego rozmiaru.
   * @param wymiar
   */
  public void przytnijDoWymiaru(int wymiar){
    int szerokoscStart = szerokosc / 2 - wymiar / 2;
    int wysokoscStart = wysokosc / 2 - wymiar / 2;
    double[][] przycietyObraz = new double[wymiar][wymiar];
    for (int i = 0; i < wymiar; i++) {
      for (int j = 0; j < wymiar; j++) {
        double insert = 0;
        if (wysokoscStart + i >= 0 && wysokoscStart + i < wysokosc
            && szerokoscStart + j >= 0 && szerokoscStart + j < szerokosc)
          insert = piksele[wysokoscStart + i][szerokoscStart + j];
        przycietyObraz[i][j] = insert;
      }
    }
    wysokosc = wymiar;
    szerokosc = wymiar;
    piksele = przycietyObraz;
  }
  /**
   * 
   * @param wiersz
   * @param kolumna
   * @return Piksel na zadanym miejscu
   */
  public double getPikselAt(int wiersz, int kolumna){
    return piksele[wiersz][kolumna];
  }
  /**
   * Nadaje zadana wartosc pikselowi znajdujacemu sie w poszukiwanym miejscu
   * @param wiersz
   * @param kolumna
   * @param nowaWartosc
   */
  public void setPikselAt(int wiersz, int kolumna, double nowaWartosc){
    piksele[wiersz][kolumna] = nowaWartosc;
  }
  /**
   * @return Wysokosc obrazu
   */
  public int getWysokosc(){
    return wysokosc;
  }
  /**
   * @return Szerokosc obrazu
   */
  public int getSzerokosc(){
    return szerokosc;
  }
}
