package algorytm;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.logging.Logger;

import logging.BasicLogger;

import com.google.inject.Inject;
import com.google.inject.name.Named;

/**
 * @author elistan
 * 
 */
public class Algorytm {
  private Logger logger = BasicLogger.applicationLogger;
  private final double threshold;
  private double najlepszaOdleglosc;
  private double drugaNajlepsza;

  @Inject
  public Algorytm(@Named("Threshold") double threshold) {
    this.threshold = threshold;
  }

  /**
   * Znajduje indeks hasha uzytkownika w bazie, ktory jest najpodobniejszy do
   * szukanego.
   * 
   * @param hash
   *          Szukany hash
   * @param hasheWBazie
   *          Lista hashow z bazy
   * @return Indeks najlepiej dopasowanego
   */
  public int znajdzNajpodobniejszy(HashUzytkownika hash,
      List<HashUzytkownika> hasheWBazie) {
    int najlepszy = znajdzNajlepszy(hash, hasheWBazie);
    logger.info("Dopasowano id - " + najlepszy + "roznica to: "
        + najlepszaOdleglosc + " druga najlepsza odleglosc: " + drugaNajlepsza);
    if (czyThresholdNiePrzekroczony())
      return najlepszy;
    throw new NoSuchElementException(
        "Nie znaleziono wystarczajaco podobnego elementu.");
  }

  private int znajdzNajlepszy(HashUzytkownika hash, List<HashUzytkownika> hashe) {
    int indeksNajlepszego = 0;
    najlepszaOdleglosc = Double.MAX_VALUE;
    drugaNajlepsza = Double.MAX_VALUE;
    for (int i = 0; i < hashe.size(); i++) {
      double odleglosc = hash.wyliczOdleglosc(hashe.get(i));
      logger.fine("Wyliczona odleglosc: " + odleglosc);
      if (odleglosc < najlepszaOdleglosc) {

        najlepszaOdleglosc = odleglosc;
        indeksNajlepszego = i;

      } else if (odleglosc < drugaNajlepsza) {
        drugaNajlepsza = odleglosc;
      }
    }
    return indeksNajlepszego;
  }

  private boolean czyThresholdNiePrzekroczony() {
    return najlepszaOdleglosc < threshold;
  }
}
