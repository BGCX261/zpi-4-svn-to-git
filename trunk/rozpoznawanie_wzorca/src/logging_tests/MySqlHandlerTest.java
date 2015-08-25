package logging_tests;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

import logging.MySqlHandler;

import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;

public class MySqlHandlerTest {
  Connection connection;
  MySqlHandler handler;
  Formatter formatter;
  LogRecord record;
  PreparedStatement statement;
  String sformatowane = "SFORMATOWANY LOG RECORD";

  @Before
  public void setUp() throws Exception {
    createMocks();
    handler.setFormatter(formatter);
    when(connection.getAutoCommit()).thenReturn(true);
    when(formatter.format(record)).thenReturn(sformatowane);
    when(connection.createStatement()).thenReturn(statement);
  }

  private void createMocks() {
    connection = mock(Connection.class);
    handler = new MySqlHandler(connection);
    formatter = mock(Formatter.class);
    record = mock(LogRecord.class);
    statement = mock(PreparedStatement.class);
  }

  @Test
  public void testFlushAutoCommit() throws SQLException {
    handler.flush();
    verify(connection).isClosed();
    verify(connection).getAutoCommit();
    verify(connection, times(0)).commit();
  }

  @Test
  public void testFlushClosed() throws SQLException {
    when(connection.isClosed()).thenReturn(true);
    handler.flush();
    verify(connection).isClosed();
    verify(connection, times(0)).getAutoCommit();
    verify(connection, times(0)).commit();
  }

  @Test
  public void testFlush() throws SQLException {
    when(connection.getAutoCommit()).thenReturn(false);
    handler.flush();
    verify(connection).isClosed();
    verify(connection).getAutoCommit();
    verify(connection).commit();
  }

  @Test
  public void testClose() throws SQLException {
    handler.close();
    verify(connection).close();
  }

  @Test
  public void testPublishLogRecord() throws SQLException {
    handler.publish(record);
    verify(statement).executeUpdate(sformatowane);
  }

}
