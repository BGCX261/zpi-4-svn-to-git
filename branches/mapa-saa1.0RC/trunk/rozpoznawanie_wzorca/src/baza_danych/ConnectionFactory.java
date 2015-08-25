package baza_danych;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Fabryka tworząca połączenia.
 * 
 * @author elistan
 * 
 */
public interface ConnectionFactory {
  /**
   * Zwraca połączenie do bazy
   * 
   * @return połączenie
   * @throws SQLException
   *           Nie udało się uzyskać połączenia
   */
  Connection getConnection() throws SQLException;
}