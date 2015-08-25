package autoryzacja;

import baza_danych.DatabaseFacade;

import com.google.inject.Inject;

import czytnik.DaneCzytnika;
import algorytm.HashUzytkownika;

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

  private String prepareQuery(String hash, int czytnik) {
    StringBuilder sb = new StringBuilder();
    sb.append("SELECT ")
        .append("    uzy.imie        status, ")
        .append("    uzy.nazwisko    uzytkownik, ")
        .append("    odc.hash        HASH, ")
        .append("    czy.id          czytnik ")
        .append("FROM        odcisk_palca        odc ")
        .append(
            "INNER JOIN  uzytkownik          uzy ON  uzy.id = odc.uzytkownik_id ")
        .append("                                        AND uzy.aktywny = 1 ")
        .append("                                        AND uzy.usuniety = 0 ")
        .append("                                        AND odc.aktywny = 1 ")
        .append(
            "LEFT JOIN   grupa_uzytkownik    gu  ON  gu.uzytkownik_id = uzy.id ")
        .append("LEFT JOIN   grupa               gru ON  gru.id = gu.grupa_id ")
        .append("                                        AND gru.aktywna = 1 ")
        .append(
            "LEFT JOIN   grupa_czytnik       gc  ON  gc.grupa_id = gu.grupa_id ")
        .append(
            "INNER JOIN  czytnik             czy ON  czy.id = gc.czytnik_id ")
        .append("    AND czy.aktywny = 1 ").append("    AND czy.id NOT IN ( ")
        .append("        SELECT czytnik_id cid ")
        .append("        FROM czytnik_uzytkownik ").append("        WHERE ")
        .append("            uzytkownik_id = uzy.id ")
        .append("            AND blacklist = 1 ").append("    ) ")
        .append("    OR czy.id IN ( ").append("        SELECT czytnik_id cid ")
        .append("        FROM czytnik_uzytkownik ").append("        WHERE ")
        .append("            uzytkownik_id = uzy.id ")
        .append("            AND blacklist = 0 ").append("    ) ")
        .append("WHERE ").append("    odc.hash = \"").append(hash.toString()).append("\"")
        .append(" AND czy.id = \"").append(czytnik).append("\";");
    System.out.println(sb.toString());
    return sb.toString();
  }

  @Override
  public boolean verify(HashUzytkownika hash, DaneCzytnika czytnik) {
    boolean verificationSuccess = false;
    if (hash != null && czytnik != null) {
      db.wyslijKwerende(prepareQuery(hash.toString(), czytnik.getId()));
      verificationSuccess = db.czyKwerendaZwrocilaWyniki();

    }
    return verificationSuccess;
  }

  @Override
  public String getErrorMessage() {
    return errorMessage;
  }

}
