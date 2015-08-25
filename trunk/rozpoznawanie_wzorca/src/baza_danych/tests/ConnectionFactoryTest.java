package baza_danych.tests;

import java.sql.SQLException;

import static org.junit.Assert.fail;

import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;

import baza_danych.ConnectionFactory;
import baza_danych.LocalMySqlConnectionFactory;
import baza_danych.OnRemoteMySqlConnectionFactory;
import baza_danych.RemoteMySqlConnectionFactory;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

public class ConnectionFactoryTest {

  private MysqlDataSource source;

  @Before
  public void setUp() throws Exception {
    source = mock(MysqlDataSource.class);
  }

  @Test
  public void testPrepareRemote() {
    new RemoteMySqlConnectionFactory(source);
    verify(source).setUser("mapa_saa");
    verify(source).setPassword("zpi4");
    verify(source).setDatabaseName("mapa_saa");
    verify(source).setServerName("80.51.104.221");
  }

  @Test
  public void testPrepareLocal() {
    new LocalMySqlConnectionFactory(source);
    verify(source).setUser("mapasaa");
    verify(source).setPassword("zpi4");
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

  @Test
  public void testGetConnectionOnRemote() {
    ConnectionFactory factory = new OnRemoteMySqlConnectionFactory(source);
    try {
      factory.getConnection();
      verify(source).getConnection();
    } catch (SQLException e) {
      fail("Exception " + e);
    }
  }

  @Test
  public void testPrepareOnRemote() {
    new OnRemoteMySqlConnectionFactory(source);
    verify(source).setUser("mapa_saa");
    verify(source).setPassword("zpi4");
    verify(source).setDatabaseName("mapa_saa");
    verify(source).setServerName("localhost");
  }
}
