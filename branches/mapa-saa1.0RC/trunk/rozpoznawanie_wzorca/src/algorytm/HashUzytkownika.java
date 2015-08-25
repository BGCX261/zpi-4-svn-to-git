package algorytm;

import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;
import com.google.inject.name.Named;

/**
 * Klasa opakowująca hash uzytkownika. W tej chwili jest to tablica 17 liczb.
 * Kazdy odpowiada sredniej jasnosci pikseli w danym sektorze obrazka
 * 
 * @author elistan
 * 
 */
public class HashUzytkownika {

  double[] hash;

  private HashUzytkownika(int rozmiar) {
    hash = new double[rozmiar];
  }

  /**
   * Anotacja assisted mowi, ze jest bedzie tutaj wstrzykiwany parametr
   * zdefiniowany w momencie tworzenia, a nie podany z gory w module.
   * 
   * @param hash
   *          - Hash uzytkownika dla ktorego chcemy utworzyc obiekt
   */
  @AssistedInject
  public HashUzytkownika(@Assisted double[] hash,
      @Named("RozmiarHasha") int rozmiar) {
    this(rozmiar);
    System.arraycopy(hash, 0, this.hash, 0, hash.length);
  }

  /**
   * @param strHash
   *          Konstruktor budujacy na podstawie stringa
   */
  @AssistedInject
  public HashUzytkownika(@Assisted String strHash,
      @Named("RozmiarHasha") int rozmiar) {
    this(rozmiar);
    String[] nStr = strHash.split(" ");
    if (nStr.length != hash.length)
      throw new IllegalArgumentException(
          "Hash uzytkownika ma zla postac, oczekiwana to " + hash.length
              + "liczb oddzielonych spacjami");
    for (int i = 0; i < hash.length; i++) {
      hash[i] = Double.parseDouble(nStr[i]);
    }
  }

  /**
   * Zwraca tablice cech
   * 
   * @return tablica
   */
  public double[] getData() {
    return hash;
  }

  /**
   * Oblicza odleglosc euklidesowa miedzy dwoma hashami uzytkownika
   * 
   * @param drugi
   * @return Odleglosc euklidesowa od wektora podanego w argumencie
   */
  public double wyliczOdleglosc(HashUzytkownika drugi) {
    double wynik = 0;
    for (int i = 0; i < hash.length; i++) {
      wynik += (hash[i] - drugi.hash[i]) * (hash[i] - drugi.hash[i]);
    }
    wynik = Math.sqrt(wynik);
    return wynik;
  }

  public int getRozmiar() {
    return hash.length;
  }

  public String toString() {
    StringBuilder builder = new StringBuilder(3 * hash.length);
    for (double cecha : hash) {
      builder.append(cecha).append(" ");
    }
    return builder.toString();
  }
}
