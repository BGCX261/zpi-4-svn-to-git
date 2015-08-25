package baza_danych;

import java.sql.Connection;
import java.sql.SQLException;

import com.google.inject.Inject;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

/**
 * Klasa składująca dane konfiguracyjne do bazy danych w jednym miejscu
 * 
 * @author andrzej
 */
public class RemoteMySqlConnectionFactory implements ConnectionFactory {
  MysqlDataSource dataSource;

  /**
   * POdstawowa konfiguracja
   */
  @Inject
  public RemoteMySqlConnectionFactory(MysqlDataSource dataSource) {
    this.dataSource = dataSource;
    this.dataSource.setUser("test");
    this.dataSource.setPassword("test");
    this.dataSource.setDatabaseName("test");
    this.dataSource.setServerName("156.17.247.193");
  }

  public Connection getConnection() throws SQLException {
    return dataSource.getConnection();
  }

}
