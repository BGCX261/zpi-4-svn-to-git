package socket;

import algorytm.HashUzytkownika;
import czytnik.DaneCzytnika;

/**
 * Opakowanie na hash i dane czytnika przesylane przez obserwator czytnika
 * 
 * @author elistan
 * 
 */
public class HashDaneCzytnikaPair {
  public HashUzytkownika hash;
  public DaneCzytnika dane;
  public boolean valid;

  public HashDaneCzytnikaPair(HashUzytkownika _hash, DaneCzytnika _dane) {
    hash = _hash;
    dane = _dane;
    valid = true;
  }

  public HashDaneCzytnikaPair() {
    valid = false;
  }

}
