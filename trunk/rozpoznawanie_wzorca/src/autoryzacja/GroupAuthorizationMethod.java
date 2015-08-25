package autoryzacja;

import baza_danych.DatabaseFacade;
import baza_danych.model.Uzytkownik;

import com.google.inject.Inject;

import czytnik.DaneCzytnika;

/**
 * Autoryzuje (aktywnego) użytkownika nie będącego na blackliście klienta oraz
 * na podstawie przynależności do (aktywnej) grupy posiadającej uprawnienia do
 * (aktywnych) klientów
 * 
 * @author andrzej
 */

public class GroupAuthorizationMethod implements AuthorizationMethod {
  DatabaseFacade db;
  String errorMessage = "próba uzyskania nieautoryzowanego dostępu do zasobu";

  /**
   * @param db
   *          Fasada polaczenia z baza
   */
  @Inject
  public GroupAuthorizationMethod(DatabaseFacade db) {
    this.db = db;
  }

  @Override
  public boolean verify(Uzytkownik uzytkownik, DaneCzytnika czytnik) {
    boolean verificationSuccess = false;
    if (uzytkownik != null && czytnik != null) {
      verificationSuccess = db.sprawdzDostepUzytkownikZCzytnikiem(uzytkownik,
          czytnik);

    }
    return verificationSuccess;
  }

  @Override
  public String getErrorMessage() {
    return errorMessage;
  }

}
