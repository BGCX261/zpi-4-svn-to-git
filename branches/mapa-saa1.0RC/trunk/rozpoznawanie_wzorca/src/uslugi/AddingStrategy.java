package uslugi;

/**
 * Strategia dodawania metody autoryzacji do bazy danych. Strategia powinna znac
 * wszystkie dane potrzebne do dodania, poza id uzytkownika.
 * 
 * @author elistan
 * 
 */
public interface AddingStrategy {
  /**
   * Generuje kwerende sql, ktora nalezy wyslac do bazy
   * 
   * @param id
   *          Id uzytkownika, ktory ma zostac dodany.
   * @return Sql gotowy do wyslania do bazy.
   * @throws InterruptedException
   *           Jezeli pojawi sie przerwanie blokujacego procesu np podczas
   *           czytania odcisku palca.
   */
  String generujSql(int id) throws InterruptedException;
}
