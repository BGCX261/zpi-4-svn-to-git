package czytnik;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Semaphore;

import algorytm.HashUzytkownika;

import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * Obserwator czytnika sluzacy do weryfikacji odciskow
 * 
 * @author elistan
 * 
 */
@Singleton
public class WeryfikatorOdciskow extends ObserwatorCzytnika {
  ConcurrentLinkedQueue<HashUzytkownika> hashe;
  ConcurrentLinkedQueue<DaneCzytnika> dane;

  /**
   * @param semafor
   *          Semafor sluzacy do synchronizacji
   */
  @Inject
  public WeryfikatorOdciskow(Semaphore semafor, int liczbaPowtorzen) {
    super(semafor, liczbaPowtorzen);
    hashe = new ConcurrentLinkedQueue<HashUzytkownika>();
    dane = new ConcurrentLinkedQueue<DaneCzytnika>();
  }

  @Override
  public DaneCzytnika getDane() {
    DaneCzytnika wynik = dane.poll();
    if (wynik == null)
      throw new IllegalStateException("Dane nie zostaly jeszcze pobrane");
    return wynik;
  }

  @Override
  protected HashUzytkownika stworzHash() {
    HashUzytkownika wynik = hashe.poll();
    if (wynik == null)
      throw new IllegalStateException("Nie pobrano jeszcze odciskow palca");
    return wynik;
  }

  @Override
  protected void zachowajHash(HashUzytkownika hash, DaneCzytnika dane) {
    this.hashe.add(hash);
    this.dane.add(dane);

  }

  @Override
  protected void obsluzBlednyOdczyt() {
    super.obsluzBlednyOdczyt();
    // TODO: dodac powiadomienie w odpowiednie miejsce.

  }

}
