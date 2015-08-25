package baza_danych;

import java.sql.Connection;
import java.sql.SQLException;

import com.google.inject.Inject;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

public class LocalMySqlConnectionFactory implements ConnectionFactory {
  MysqlDataSource dataSource;

  @Inject
  public LocalMySqlConnectionFactory(MysqlDataSource dataSource) {
    this.dataSource = dataSource;
    dataSource.setUser("mapasaa");
    dataSource.setPassword("mapa-saa");
    dataSource.setServerName("localhost");
    dataSource.setDatabaseName("mapa-saa");
  }

  @Override
  public Connection getConnection() throws SQLException {
    return dataSource.getConnection();
  }

}
