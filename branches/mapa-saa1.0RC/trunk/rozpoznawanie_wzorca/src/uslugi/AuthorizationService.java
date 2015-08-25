package uslugi;

import java.util.ArrayList;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import algorytm.HashUzytkownika;
import autoryzacja.AuthorizationMethod;

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
  private final ReadService rs;

  /**
   * Inicjuje metodami autoryzacji
   * 
   * @param methods
   *          Rozne metody autoryzacji
   */
  @Inject
  public AuthorizationService(Set<AuthorizationMethod> methods,
      @Named("SystemAccessLogger") Logger systemAccessLogger,
      ReadService readService) {
    for (AuthorizationMethod am : methods) {
      this.methods.add(am);
    }
    this.LOGGER = systemAccessLogger;
    rs = readService;
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
  public boolean autoryzuj(DaneCzytnika czytnik, HashUzytkownika hash) {
    boolean result = true;
    AuthorizationMethod failingMethod = null;
    for (AuthorizationMethod method : methods) {
      boolean before = result;
      result = result && method.verify(hash, czytnik);
      if (before != result) {
        failingMethod = method;
      }
    }
    if (!result)
      logIllegalAcces(czytnik, hash, failingMethod);
//    else
      //FIXME:
    return result;
  }

  private void logIllegalAcces(DaneCzytnika czytnik, HashUzytkownika hash,
      AuthorizationMethod failingMethod) {
    Object[] params = new Object[2];
    LogRecord logRec;
    if (failingMethod != null) {
      logRec = new LogRecord(Level.WARNING, "ACCESS DENIED; CAUSE: "
          + failingMethod.getErrorMessage());
    } else {
      logRec = new LogRecord(Level.INFO, "ACCESS GRANTED;");
    }
    params[0] = rs.pobierzIdUzytkownika(hash);
    params[1] = czytnik.getId();
    logRec.setParameters(params);
    LOGGER.log(logRec);

  }

}
