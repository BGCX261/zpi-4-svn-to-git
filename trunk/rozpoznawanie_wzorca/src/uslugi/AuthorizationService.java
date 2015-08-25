package uslugi;

import java.util.ArrayList;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import autoryzacja.AuthorizationMethod;
import baza_danych.model.Uzytkownik;

import com.google.inject.Inject;
import com.google.inject.name.Named;

import czytnik.DaneCzytnika;

/**
 * Serwis dokonujacy autoryzacji
 * 
 * @author elistan
 * 
 */
public class AuthorizationService {
  ArrayList<AuthorizationMethod> methods = new ArrayList<AuthorizationMethod>();
  private final Logger LOGGER;
  private String ostatniKomunikat;

  /**
   * Inicjuje metodami autoryzacji
   * 
   * @param methods
   *          Rozne metody autoryzacji
   */
  @Inject
  public AuthorizationService(Set<AuthorizationMethod> methods,
      @Named("SystemAccessLogger") Logger systemAccessLogger) {
    for (AuthorizationMethod am : methods) {
      this.methods.add(am);
    }
    this.LOGGER = systemAccessLogger;
  }

  /**
   * Przekazuje dane do metod autoryzacji
   * 
   * @param czytnik
   *          dane czytnika
   * @param hash
   *          Hash uzytkownika
   * @return Wynik autoryzacji - negatywny gdy chociaz jedna metoda nie dopusci
   *         autoryzacji.
   */
  public boolean autoryzuj(DaneCzytnika czytnik, Uzytkownik hash) {
    boolean result = true;
    AuthorizationMethod failingMethod = null;
    for (AuthorizationMethod method : methods) {
      boolean before = result;
      result = result && method.verify(hash, czytnik);
      if (before != result) {
        failingMethod = method;
      }
    }
    logAccess(czytnik, hash, failingMethod);
    return result;
  }

  private void logAccess(DaneCzytnika czytnik, Uzytkownik uzytkownik,
      AuthorizationMethod failingMethod) {
    Object[] params = new Object[2];
    LogRecord logRec = przygotujKomunikat(failingMethod);
    params[0] = uzytkownik.getId();
    params[1] = czytnik.getId();
    logRec.setParameters(params);
    LOGGER.log(logRec);

  }

  private LogRecord przygotujKomunikat(AuthorizationMethod failingMethod) {
    LogRecord logRec;
    Level logLevel;
    if (failingMethod != null) {
      ostatniKomunikat = "ACCESS DENIED; CAUSE: "
          + failingMethod.getErrorMessage();
      logLevel = Level.WARNING;
    } else {
      ostatniKomunikat = "ACCESS GRANTED;";
      logLevel = Level.INFO;
    }
    logRec = new LogRecord(logLevel, ostatniKomunikat);
    return logRec;
  }

  public String pobierzOstatniKomunikat() {
    return ostatniKomunikat;
  }
}
