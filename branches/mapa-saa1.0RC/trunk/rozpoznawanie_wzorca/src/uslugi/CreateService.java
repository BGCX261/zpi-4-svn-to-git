package uslugi;

import java.util.logging.Logger;

import logging.BasicLogger;
import baza_danych.DatabaseFacade;

import com.google.inject.Inject;

/**
 * Usluga dodajaca dane autoryzacji do bazy danych
 * 
 * @author elistan
 * 
 */
public class CreateService {
  private AddingStrategy strategiaDodawania;
  private DatabaseFacade db;
  private Logger logger = BasicLogger.applicationLogger;

  /**
   * Tworzy serwis dodający metody autoryzacji
   * 
   * @param strategia
   *          Strategia dodawania -różna dla różnych metod autoryzacji
   * @param db
   *          Fasada bazy danych, do ktorych dodana bedzie metoda
   */
  @Inject
  public CreateService(AddingStrategy strategia, DatabaseFacade db) {
    this.strategiaDodawania = strategia;
    this.db = db;
  }

  /**
   * Dodaje uzytkownika wraz z danymi do bazy
   * 
   * @param idUzytkownika
   * @return true dla udanego dodania
   */
  public boolean dodaj(int idUzytkownika) {
    boolean result = false;
    try {
      String query = strategiaDodawania.generujSql(idUzytkownika);
      int zwrocone = db.wyslijKwerendeUpdate(query);
      if (zwrocone != 1)
        logger.warning("Zwrocono " + zwrocone + " wierszy");
      result = zwrocone == 1;
    } catch (InterruptedException e) {
      logger.throwing(CreateService.class.getCanonicalName(), "dodaj", e);
      return false;
    }
    return result;
  }
}
