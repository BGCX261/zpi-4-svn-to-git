package uslugi.fingerprint;

import uslugi.AddingStrategy;
import algorytm.HashUzytkownika;

import com.google.inject.Inject;

import czytnik.ObserwatorCzytnika;

/**
 * Strategia dodawania odciskow palca do bazy danych
 * 
 * @author elistan
 * 
 */
public class FingerprintAddStrategy implements AddingStrategy {
  ObserwatorCzytnika obserwator;

  /**
   * @param obserwator
   *          Obserwator czytnika
   */
  @Inject
  public FingerprintAddStrategy(ObserwatorCzytnika obserwator) {
    this.obserwator = obserwator;
  }

  @Override
  public String generujSql(int id) throws InterruptedException {
    HashUzytkownika hash = pobierzHash();
    return stworzZapytanie(id, hash);
  }

  private String stworzZapytanie(int id, HashUzytkownika hash) {
    StringBuilder result = new StringBuilder(
        "INSERT INTO odcisk_palca(hash, uzytkownik_id) ");
    result.append("VALUES(\"");
    result.append(hash);
    result.append("\", ");
    result.append(id);
    result.append(");");
    return result.toString();
  }

  private HashUzytkownika pobierzHash() throws InterruptedException {
    HashUzytkownika hash = obserwator.getHash();
    return hash;
  }

}
