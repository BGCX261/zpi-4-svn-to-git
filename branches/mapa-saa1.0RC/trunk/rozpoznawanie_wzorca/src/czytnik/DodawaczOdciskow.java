package czytnik;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

import algorytm.HashUzytkownika;
import algorytm.HashUzytkownikaFactory;

import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * Klasa obserwujaca czytnik linii papilarnych, przetwarzajaca i przekazujaca
 * odebrane obrazy do dodania do bazy danych.
 * 
 * @author elistan
 * 
 */
@Singleton
public class DodawaczOdciskow extends ObserwatorCzytnika {
  List<HashUzytkownika> listaHashy;
  HashUzytkownikaFactory factory;

  /**
   * @param n
   *          Ilosc obrazkow do usrednienia
   * @param factory
   *          Fabryka hashow uzytkownika
   * @param semafor
   *          Semafor wykorzystany w implemenacji
   */
  @Inject
  public DodawaczOdciskow(HashUzytkownikaFactory factory, Semaphore semafor,
      int liczbaPowtorzen) {
    super(semafor, liczbaPowtorzen);
    listaHashy = new ArrayList<HashUzytkownika>();
    this.factory = factory;
  }

  @Override
  public DaneCzytnika getDane() {
    throw new UnsupportedOperationException(
        "Ta operacja jest niedozwolona w trybie dodawania uzytkownika");
  }

  @Override
  protected HashUzytkownika stworzHash() {
    int iloscHashy = listaHashy.size();
    if (iloscHashy == 0)
      throw new IllegalStateException("Nie pobrano jeszcze odciskow palca");
    int rozmiar = listaHashy.get(0).getRozmiar();
    double[] sredniHash = new double[rozmiar];
    for (int i = 0; i < rozmiar; i++) {
      for (HashUzytkownika hash : listaHashy) {
        sredniHash[i] += hash.getData()[i];
      }
      sredniHash[i] /= (double) listaHashy.size();
    }
    return factory.createHashUzytkownika(sredniHash);
  }

  @Override
  protected void zachowajHash(HashUzytkownika hash, DaneCzytnika dane) {
    listaHashy.add(hash);
  }

  @Override
  protected void obsluzBlednyOdczyt() {
    super.obsluzBlednyOdczyt();
    // TODO: dodac powiadomienie do appletu
  }

}
