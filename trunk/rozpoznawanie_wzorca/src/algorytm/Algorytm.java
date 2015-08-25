package algorytm;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.logging.Logger;

import logging.BasicLogger;
import baza_danych.model.Uzytkownik;

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

  public Uzytkownik znajdzNajpodobniejszegoUzytkownika(
      HashUzytkownika pobranyHash, List<Uzytkownik> uzytkownicy) {
    if (uzytkownicy.isEmpty())
      throw new IllegalStateException("W bazie nie ma zadnych uzytkownikow");
    int indeksNajlepszego = -1;
    int indeksDrugiegoNajlepszego = -1;
    najlepszaOdleglosc = Double.MAX_VALUE;
    drugaNajlepsza = Double.MAX_VALUE;
    for (int i = 0; i < uzytkownicy.size(); i++) {
      double odleglosc = wyliczPodobienstwo(pobranyHash, uzytkownicy.get(i));
      logger.finest("Wyliczona odleglosc: " + odleglosc);
      if (odleglosc < najlepszaOdleglosc) {
        najlepszaOdleglosc = odleglosc;
        indeksNajlepszego = i;
      } else if (odleglosc < drugaNajlepsza) {
        drugaNajlepsza = odleglosc;
      }
    }
    zalogujDopasowanie(uzytkownicy, indeksNajlepszego,
        indeksDrugiegoNajlepszego);
    if (czyThresholdNiePrzekroczony())
      return uzytkownicy.get(indeksNajlepszego);
    throw new NoSuchElementException(
        "Nie znaleziono wystarczajaco podobnego elementu.");
  }

  private void zalogujDopasowanie(List<Uzytkownik> uzytkownicy,
      int indeksNajlepszego, int indeksDrugiego) {
    StringBuilder wiadomosc = new StringBuilder();
    wiadomosc.append("Dopasowano id - ")
        .append(uzytkownicy.get(indeksNajlepszego).getId())
        .append("\nRoznica wynosi: ").append(najlepszaOdleglosc)
        .append("\nDrugie najlepsze dopasowanie - ").append(indeksDrugiego)
        .append("\nRoznica to: ").append(drugaNajlepsza);
    logger.info(wiadomosc.toString());
  }

  private double wyliczPodobienstwo(HashUzytkownika pobranyHash,
      Uzytkownik uzytkownik) {
    double wyliczonaOdleglosc = Double.MAX_VALUE;
    for (HashUzytkownika hashUzytkownika : uzytkownik.pobierzHashe()) {
      double odleglosc = pobranyHash.wyliczOdleglosc(hashUzytkownika);
      if (odleglosc < wyliczonaOdleglosc)
        wyliczonaOdleglosc = odleglosc;
    }
    return wyliczonaOdleglosc;
  }

  private boolean czyThresholdNiePrzekroczony() {
    return najlepszaOdleglosc < threshold;
  }
}
