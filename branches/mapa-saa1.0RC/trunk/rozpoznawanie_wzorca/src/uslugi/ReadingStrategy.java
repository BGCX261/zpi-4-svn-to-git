package uslugi;

import algorytm.HashUzytkownika;

/**
 * Strategia odczytu potrzebnych danych z bazy
 * 
 * @author elistan
 * 
 */
public interface ReadingStrategy {
  /**
   * @return Zapytanie sql
   */
  String generujSql();

  String generujSql(HashUzytkownika hash);
}
