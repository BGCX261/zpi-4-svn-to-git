package autoryzacja;

import baza_danych.model.Uzytkownik;
import czytnik.DaneCzytnika;

/**
 * Interfejs metody autoryzacji
 * 
 * @author elistan
 * 
 */
public interface AuthorizationMethod {
  /**
   * Weryfikacja na podstawie zadanego hasha uzytkownika dla zadanego czytnika
   * 
   * @param hash
   *          Hash uzytkownika
   * @param czytnik
   *          Dane czytnika
   * @return True dla pomyslnej autoryzacji
   */
  boolean verify(Uzytkownik hash, DaneCzytnika czytnik);

  String getErrorMessage();

}