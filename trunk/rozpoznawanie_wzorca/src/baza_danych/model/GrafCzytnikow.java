package baza_danych.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;

public class GrafCzytnikow {
  private static final String ERROR_MESSAGE = "Niepoprawny format pliku.\nSpodziewany format to idCzytnika;idCzytnika";
  private Map<Integer, Set<Integer>> czytniki;

  @AssistedInject
  public GrafCzytnikow(@Assisted List<ParaCzytnikow> paryCzytnikow) {
    czytniki = new TreeMap<Integer, Set<Integer>>();
    przygotujMape(paryCzytnikow);
  }

  @AssistedInject
  public GrafCzytnikow(@Assisted BufferedReader reader) throws IOException {
    List<ParaCzytnikow> paryCzytnikow = wczytajPary(reader);
    czytniki = new TreeMap<Integer, Set<Integer>>();
    przygotujMape(paryCzytnikow);
  }

  private List<ParaCzytnikow> wczytajPary(BufferedReader reader)
      throws IOException {
    List<ParaCzytnikow> paryCzytnikow = new ArrayList<ParaCzytnikow>();
    String linia;
    while ((linia = reader.readLine()) != null) {
      ParaCzytnikow para = stworzPareZLinii(linia);
      paryCzytnikow.add(para);
    }
    return paryCzytnikow;
  }

  private ParaCzytnikow stworzPareZLinii(String linia) {
    try {
      String[] czytniki = linia.split(";");
      if (czytniki.length != 2)
        throw new IllegalArgumentException(ERROR_MESSAGE);
      int pierwszy = Integer.parseInt(czytniki[0].trim());
      int drugi = Integer.parseInt(czytniki[1].trim());
      ParaCzytnikow para = new ParaCzytnikow(pierwszy, drugi);
      return para;
    } catch (NumberFormatException e) {
      throw new IllegalArgumentException(ERROR_MESSAGE, e);
    }

  }

  private void przygotujMape(List<ParaCzytnikow> paryCzytnikow) {
    for (ParaCzytnikow para : paryCzytnikow) {
      Set<Integer> zbiorMozliwych = czytniki.get(para.pierwszyCzytnik);
      if (zbiorMozliwych == null) {
        zbiorMozliwych = new TreeSet<Integer>();
        czytniki.put(para.pierwszyCzytnik, zbiorMozliwych);
      }
      zbiorMozliwych.add(para.drugiCzytnik);
    }
  }

  public boolean czyDozwolonePrzejscie(int czytnik, int drugiCzytnik) {
    int mniejszy, wiekszy;
    if (czytnik < drugiCzytnik) {
      mniejszy = czytnik;
      wiekszy = drugiCzytnik;
    } else {
      mniejszy = drugiCzytnik;
      wiekszy = czytnik;
    }
    Set<Integer> zbiorDopuszczalnych = czytniki.get(mniejszy);
    return czyNiepustyIZawiera(wiekszy, zbiorDopuszczalnych);
  }

  private boolean czyNiepustyIZawiera(int czytnikDoSprawdzenia,
      Set<Integer> zbiorDopuszczalnych) {
    return zbiorDopuszczalnych != null
        && zbiorDopuszczalnych.contains(czytnikDoSprawdzenia);
  }

  public List<ParaCzytnikow> przygotujParyCzytnikow() {
    List<ParaCzytnikow> paryCzytnikow = new ArrayList<ParaCzytnikow>();
    for (Integer czytnik : czytniki.keySet()) {
      Set<Integer> dopuszczalneCzytniki = czytniki.get(czytnik);
      for (Integer drugiCzytnik : dopuszczalneCzytniki) {
        paryCzytnikow.add(new ParaCzytnikow(czytnik, drugiCzytnik));
      }
    }
    return paryCzytnikow;

  }
}
