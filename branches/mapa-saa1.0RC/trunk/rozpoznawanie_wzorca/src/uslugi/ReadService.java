package uslugi;

import java.util.List;

import baza_danych.DatabaseFacade;

import com.google.inject.Inject;

import algorytm.HashUzytkownika;

/**
 * Serwis odczytu z bazy danych
 * 
 * @author elistan
 * 
 */
public class ReadService {
  private ReadingStrategy strategiaOdczytu;
  private DatabaseFacade db;

  /**
   * @param strategia
   *          Strategia odczytu z bazy danych.
   * @param db
   *          Fasada bazy danych/
   */
  @Inject
  public ReadService(ReadingStrategy strategia, DatabaseFacade db) {
    this.strategiaOdczytu = strategia;
    this.db = db;
  }

  /**
   * @return Lista hashy uzytkownikow wygenerowana na podstawie strategii.
   */
  public List<HashUzytkownika> pobierzUzytkownikow() {
    return db.pobierzUzytkownikow(strategiaOdczytu.generujSql());
  }
  
  /**
   * @return Identyfikator użytkownika na podstawie hasha.
   */
  public Integer pobierzIdUzytkownika(HashUzytkownika hash) {
    return db.pobierzIdUzytkownika(strategiaOdczytu.generujSql(hash));
  }
  
  
}
