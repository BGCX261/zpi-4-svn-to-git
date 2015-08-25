package uslugi.fingerprint;

import uslugi.ReadingStrategy;
import algorytm.HashUzytkownika;

/**
 * Strategia odczytu dla Odcisku palca
 * 
 * @author elistan
 * 
 */
public class FingerprintReadingStrategy implements ReadingStrategy {

  @Override
  public String generujSql() {
    StringBuilder sb = new StringBuilder(50);
    sb.append("SELECT op.hash ").append("FROM odcisk_palca op ")
        .append("WHERE op.aktywny = 1;");
    return sb.toString();
  }

  @Override
  public String generujSql(HashUzytkownika hash) {
    StringBuilder sb = new StringBuilder(50);
    sb.append("SELECT op.uzytkownik_id ").append("FROM odcisk_palca op ")
        .append("WHERE op.hash = '").append(hash.toString()).append("';");
    return sb.toString();
  }

}
