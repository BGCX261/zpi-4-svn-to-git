package baza_danych;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Handler;
import java.util.logging.Logger;

import logging.BasicLogger;
import logging.MySqlHandler;
import logging.MySqlLogFormatter;
import algorytm.HashUzytkownika;
import algorytm.HashUzytkownikaFactory;

import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * Fasada dajaca ujednolicony dostep do bazy danych i ukrywajaca wewnetrzna
 * implementacje java.sql
 * 
 * @author elistan
 * 
 */
@Singleton
public class DatabaseFacade {
  ConnectionFactory connFactory;
  HashUzytkownikaFactory hashFactory;
  Logger logger = BasicLogger.applicationLogger;
  ResultSet resultSet;

  /**
   * @param factory
   *          Fabryka polaczen
   * @param hashFactory
   *          Fabryka hashy uzytkownika
   */
  @Inject
  public DatabaseFacade(ConnectionFactory factory,
      HashUzytkownikaFactory hashFactory) {
    this.connFactory = factory;
    this.hashFactory = hashFactory;
  }

  /**
   * Wysyla kwerende typu update do bazy danych
   * 
   * @param query
   * @return Ilosc uaktualnionych wierszy
   */
  public int wyslijKwerendeUpdate(String query) {
    Connection connection = null;
    try {
      connection = connFactory.getConnection();

      PreparedStatement ps;
      ps = connection.prepareStatement(query);
      int result = ps.executeUpdate();
      resultSet = ps.getResultSet();
      return result;
    } catch (SQLException e) {
      logger.throwing(DatabaseFacade.class.getCanonicalName(),
          "wyslijKwerendeUpdate", e);
    } finally {
      if (connection != null)
        closeIgnoringException(connection);
    }
    return 0;
  }

  private void closeIgnoringException(Connection connection) {
    try {
      connection.close();
    } catch (SQLException e) {
    }
  }

  /**
   * Wysyla dowolna kwerende do bazy danych
   * 
   * @param query
   *          Kwerenda
   * @return True - dalo sie wyslac kwerende.
   */
  public boolean wyslijKwerende(String query) {
    Connection connection = null;
    try {
      connection = connFactory.getConnection();

      PreparedStatement ps;
      ps = connection.prepareStatement(query);
      boolean result = ps.execute();
      resultSet = ps.getResultSet();
      return result;
    } catch (SQLException e) {
      logger.throwing(DatabaseFacade.class.getCanonicalName(),
          "wyslijKwerende", e);
    } finally {
      if (connection != null)
        closeIgnoringException(connection);
    }
    return false;
  }

  /**
   * Zwraca liste hashy uzytkownikow
   * 
   * @param query
   *          Zapytanie generujace hashe
   * @return Lista hashy uzytkownikow
   */
  public List<HashUzytkownika> pobierzUzytkownikow(String query) {
    Connection connection = null;
    ResultSet res;
    ArrayList<HashUzytkownika> listaHashy = new ArrayList<HashUzytkownika>();

    try {
      connection = connFactory.getConnection();

      PreparedStatement ps = connection.prepareStatement(query);
      res = ps.executeQuery();

      res.first();
      while (!res.isAfterLast()) {
        String hash = res.getString(1);
        listaHashy.add(hashFactory.createHashUzytkownika(hash));
        res.next();
      }
    } catch (SQLException e) {
      logger.throwing(DatabaseFacade.class.getCanonicalName(),
          "pobierzUzytkownikow", e);
      return listaHashy;
    } finally {
      if (connection != null) {
        closeIgnoringException(connection);
        return listaHashy;
      }
    }
    return listaHashy;
  }

  /**
   * 
   * @return True gdy poprzednia kwerenda zwrocila wyniki. False w przeciwnym
   *         przypadku i gdy nie bylo jeszcze kwerendy
   */
  public boolean czyKwerendaZwrocilaWyniki() {
    if (resultSet == null)
      return false;
    try {
      return resultSet.next();
    } catch (SQLException e) {
      logger.throwing(DatabaseFacade.class.getCanonicalName(),
          "czyKwerendaZwrocilaWyniki", e);
      return false;
    }
  }

  public Integer pobierzIdUzytkownika(String sql) {
    Connection connection = null;
    PreparedStatement ps;
    try {
      connection = connFactory.getConnection();
      ps = connection.prepareStatement(sql);
      if (ps.execute()) {
        resultSet = ps.getResultSet();
        resultSet.first();
        return ps.getResultSet().getInt(1);
      } else {
        return null;
      }
    } catch (SQLException e) {
      logger.throwing(this.getClass().getCanonicalName(),
          "pobierzIdUzytkownika", e);
    }
    return null;
  }

  public Handler createSqlHandler() {
    Connection connection;
    try {
      connection = connFactory.getConnection();
    } catch (SQLException e) {
      throw new RuntimeException("Nie mozna polaczyc sie z baza danych", e);
    }
    Handler handler = new MySqlHandler(connection);
    handler.setFormatter(new MySqlLogFormatter(connection));
    return handler;
  }
}
