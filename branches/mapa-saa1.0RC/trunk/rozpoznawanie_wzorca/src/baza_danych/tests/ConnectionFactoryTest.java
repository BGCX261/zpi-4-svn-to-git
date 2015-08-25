package baza_danych.tests;

import java.sql.SQLException;

import static org.junit.Assert.fail;

import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import baza_danych.ConnectionFactory;
import baza_danych.LocalMySqlConnectionFactory;
import baza_danych.RemoteMySqlConnectionFactory;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

public class ConnectionFactoryTest {

  private MysqlDataSource source;

  @Before
  public void setUp() throws Exception {
    source = mock(MysqlDataSource.class);
  }

  @Ignore
  @Test
  public void testPrepareRemote() {
    new RemoteMySqlConnectionFactory(source);
    verify(source).setUser("test");
    verify(source).setPassword("test");
    verify(source).setDatabaseName("test");
    verify(source).setServerName("SQL09.FREEMYSQL.NET");
  }

  @Test
  public void testPrepareLocal() {
    new LocalMySqlConnectionFactory(source);
    verify(source).setUser("mapasaa");
    verify(source).setPassword("mapa-saa");
    verify(source).setDatabaseName("mapa-saa");
    verify(source).setServerName("localhost");
  }

  @Test
  public void testGetConnectionLocal() {
    ConnectionFactory factory = new LocalMySqlConnectionFactory(source);
    try {
      factory.getConnection();
      verify(source).getConnection();
    } catch (SQLException e) {
      fail("Exception " + e);
    }

  }

  @Test
  public void testGetConnectionRemote() {
    ConnectionFactory factory = new RemoteMySqlConnectionFactory(source);
    try {
      factory.getConnection();
      verify(source).getConnection();
    } catch (SQLException e) {
      fail("Exception " + e);
    }

  }
}
