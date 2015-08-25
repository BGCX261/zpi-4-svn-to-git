package baza_danych;

import java.sql.Connection;
import java.sql.SQLException;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

/**
 * Klasa składująca dane konfiguracyjne do bazy danych w jednym miejscu
 * 
 * @author andrzej
 */
@Singleton
public class RemoteMySqlConnectionFactory implements ConnectionFactory {
  MysqlDataSource dataSource;

  /**
   * POdstawowa konfiguracja
   */
  @Inject
  public RemoteMySqlConnectionFactory(MysqlDataSource dataSource) {
    this.dataSource = dataSource;
    this.dataSource.setUser("mapa_saa");
    this.dataSource.setPassword("zpi4");
    this.dataSource.setDatabaseName("mapa_saa");
    this.dataSource.setServerName("80.51.104.221");
  }

  public Connection getConnection() throws SQLException {
    return dataSource.getConnection();
  }

}
