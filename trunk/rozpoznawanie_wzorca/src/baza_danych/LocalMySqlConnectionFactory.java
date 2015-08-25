package baza_danych;

import java.sql.Connection;
import java.sql.SQLException;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

@Singleton
public class LocalMySqlConnectionFactory implements ConnectionFactory {
  MysqlDataSource dataSource;

  @Inject
  public LocalMySqlConnectionFactory(MysqlDataSource dataSource) {
    this.dataSource = dataSource;
    dataSource.setUser("mapasaa");
    dataSource.setPassword("zpi4");
    dataSource.setServerName("localhost");
    dataSource.setDatabaseName("mapa-saa");
  }

  @Override
  public Connection getConnection() throws SQLException {
    return dataSource.getConnection();
  }

}
