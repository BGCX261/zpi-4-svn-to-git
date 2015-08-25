package czytnik;

import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.Semaphore;
import java.util.logging.Logger;

import logging.BasicLogger;
import socket.HashDaneCzytnikaPair;
import algorytm.HashUzytkownika;

/**
 * Interfejs obserwatora czytnika. Rozne zachowanie w zaleznosci od tego czy
 * dodajemy do bazy danych, czy autoryzujemy
 * 
 * @author elistan
 * 
 */
public abstract class ObserwatorCzytnika implements Observer {
  private Semaphore semafor;
  private Logger logger;
  private int permits;

  public ObserwatorCzytnika(Semaphore semafor, int permits) {
    this.semafor = semafor;
    this.logger = BasicLogger.applicationLogger;
    this.permits = permits;
  }

  /**
   * Zwraca hash uzytkownika otrzymany na podstawie obrazka z czytnika
   * 
   * @return hash
   * @throws InterruptedException
   *           Cos poszlo nie tak z watkami
   */
  public HashUzytkownika getHash() throws InterruptedException {
    semafor.acquire(permits);
    HashUzytkownika wynik = stworzHash();
    return wynik;
  }

  /**
   * Zwraca dane czytnika, ktory wczytal odcisk palca
   * 
   * @return Dane czytnika
   */
  public abstract DaneCzytnika getDane();

  @Override
  public void update(Observable o, Object arg) {
    HashDaneCzytnikaPair pair = (HashDaneCzytnikaPair) arg;
    if (pair.valid) {
      zachowajHash(pair.hash, pair.dane);
      semafor.release();
    } else {
      obsluzBlednyOdczyt();
    }
  }

  protected abstract HashUzytkownika stworzHash();

  protected abstract void zachowajHash(HashUzytkownika hash, DaneCzytnika dane);

  protected void obsluzBlednyOdczyt() {
    logger.warning("Nie udalo sie odczytac prawidlowego odcisku.");
    // TODO: dodac powiadomienie w odpowiednie miejsce.
  }
}