package socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.logging.Logger;

import logging.BasicLogger;
import algorytm.FeatureExtractor;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

import czytnik.DaneCzytnikaFactory;
import czytnik.ObserwatorCzytnika;

/**
 * Odczytuje obrazek ze strumienia i przekazuje go swojemu obserwatorowi
 * 
 * @author elistan
 * 
 */
public class ImageReader extends Observable implements Runnable {
  PrintWriter out;
  BufferedReader in;
  FeatureExtractor ekstraktorCech;
  DaneCzytnikaFactory daneCzytnikafactory;
  Logger logger = BasicLogger.applicationLogger;
  int szerokosc, wysokosc;
  int[][] dane;

  /**
   * @param out
   *          Strumien wyjsciowy socketa
   * @param in
   *          Strumien wejsciowy socketa
   * @param oc
   *          Obserwator czytnika pobierajacy dane o tym obrazku
   * @param o
   *          obiekt przetwarzajacy obraz
   * @param factory
   *          Wytworca danych o czytniku
   */
  @Inject
  public ImageReader(@Assisted PrintWriter out, @Assisted BufferedReader in,
      @Assisted ObserwatorCzytnika oc, FeatureExtractor ekstraktorCech,
      DaneCzytnikaFactory factory) {
    this.out = out;
    this.in = in;
    addObserver(oc);
    this.ekstraktorCech = ekstraktorCech;
    this.daneCzytnikafactory = factory;
  }

  @Override
  public void run() {
    String str = null;
    try {
      str = in.readLine();
      int id = Integer.parseInt(str);
      str = in.readLine();
      pobierzWymiary(str);
      dane = new int[wysokosc][szerokosc];
      pobierzDane();
      // Po pobraniu obrazka wysylamy hash obserwatorowi
      setChanged();
      notifyObservers(new HashDaneCzytnikaPair(
          ekstraktorCech.przetworzObraz(dane), daneCzytnikafactory.stworz(id)));
    } catch (IOException e) {
      logException(e);
    } catch (IllegalArgumentException e) {
      logException(e);

    }

  }

  private void logException(Exception e) {
    logger.throwing(ImageReader.class.getName(), "run", e);
    HashDaneCzytnikaPair pair = new HashDaneCzytnikaPair();
    pair.valid = false;
    setChanged();
    notifyObservers(pair);
    // TODO: wysylanie potwierdzenia odbioru
    // TODO: powiadomianie o nieudanym odbiorze
  }

  private void pobierzWymiary(String linia) {
    String[] splitted = linia.split(" ");
    if (splitted.length != 4)
      throw new IllegalArgumentException("Niepoprawny format naglowka PGM");
    szerokosc = Integer.parseInt(splitted[1]);
    wysokosc = Integer.parseInt(splitted[2]);
  }

  private void pobierzDane() throws IOException {
    List<String[]> linie = czytajLinie();
    zweryfikujLinie(linie);
    for (int i = 0; i < wysokosc; i++) {
      for (int j = 0; j < szerokosc; j++)
        dane[i][j] = Integer.parseInt(linie.get(i)[j]);
    }
  }

  private List<String[]> czytajLinie() throws IOException {
    String line;
    ArrayList<String[]> wynik = new ArrayList<String[]>();
    while ((line = in.readLine()) != null && !line.equals("EOF")) {
      wynik.add(line.split(" "));
    }
    return wynik;
  }

  private void zweryfikujLinie(List<String[]> linie) {
    if (linie.size() != wysokosc)
      throw new IllegalArgumentException(
          "Niepoprawny format danych wejsciowych.");
    for (String[] linia : linie) {
      if (linia.length != szerokosc)
        throw new IllegalArgumentException(
            "Niepoprawny format danych wejsciowych.");
    }
  }
}
