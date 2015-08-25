package algorytm;

/**
 * Interfejs fabryki na potrzeby guice. Konkretna implementacja tworzona jest
 * automatycznie.
 * 
 * @author elistan
 * 
 */
public interface HashUzytkownikaFactory {
  /**
   * @param dane
   *          Dane sluzace do utworzenia hasha
   * @return Stworzony hash
   */
  public HashUzytkownika createHashUzytkownika(double[] dane);

  /**
   * @param dane
   *          Dane sluzace do utworzenia hasha
   * @return Stworzony hash
   */
  public HashUzytkownika createHashUzytkownika(String dane);
}
