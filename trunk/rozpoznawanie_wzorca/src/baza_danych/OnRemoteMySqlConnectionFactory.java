package baza_danych;

import java.sql.Connection;
import java.sql.SQLException;

import com.google.inject.Inject;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

public class OnRemoteMySqlConnectionFactory implements ConnectionFactory {
  private MysqlDataSource dataSource;

  @Inject
  public OnRemoteMySqlConnectionFactory(MysqlDataSource dataSource) {
    this.dataSource = dataSource;
    dataSource.setUser("mapa_saa");
    dataSource.setPassword("zpi4");
    dataSource.setServerName("localhost");
    dataSource.setDatabaseName("mapa_saa");
  }

  @Override
  public Connection getConnection() throws SQLException {
    return dataSource.getConnection();
  }

}
