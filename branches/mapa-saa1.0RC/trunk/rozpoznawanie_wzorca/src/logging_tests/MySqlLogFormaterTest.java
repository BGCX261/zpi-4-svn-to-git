package logging_tests;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.LogRecord;

import logging.MySqlLogFormatter;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;

public class MySqlLogFormaterTest {
  private Connection mockedConnection;
  private LogRecord mockedLogRecord;
  private PreparedStatement mockedStatement;
  private Object[] mockedParameters;
  private String prepStmtQry;

  @Before
  public void configure() {
    mockedConnection = mock(Connection.class);
    mockedLogRecord = mock(LogRecord.class);
    mockedStatement = mock(PreparedStatement.class);
    mockedParameters = new Object[2];
    prepStmtQry = "\n"
        + "INSERT INTO log_aktywnosci(uzytkownik_id, czytnik_id, opis) "
        + "VALUES(?, ?, ?);";
  }
  
  @Test
  public void testConstructor() throws SQLException{
    System.out.println("\nTest constructor");
    
    Formatter formater = new MySqlLogFormatter(mockedConnection);
    assertNotNull(formater);
  }

  @Test
  public void testAllParams() throws SQLException{
    System.out.println("\nTest normal case");
    String compiledStmtQry = "" +
        "INSERT INTO log_aktywnosci(uzytkownik_id, czytnik_id, opis) " +
        "VALUES(1, 2, 'INFO Test normal case');";
    when(mockedConnection.prepareStatement("\n"
        + "INSERT INTO log_aktywnosci(uzytkownik_id, czytnik_id, opis) "
        + "VALUES(?, ?, ?);")).thenReturn(mockedStatement);
    mockedParameters[0] = new Integer(1);
    mockedParameters[1] = new Integer(2);
    when(mockedLogRecord.getParameters()).thenReturn(mockedParameters);
    when(mockedLogRecord.getMessage()).thenReturn("Test normal case");
    when(mockedLogRecord.getLevel()).thenReturn(Level.INFO);
    when(mockedStatement.toString()).thenReturn("\n" + compiledStmtQry);
    
    Formatter formater = new MySqlLogFormatter(mockedConnection);
    assertEquals(compiledStmtQry, formater.format(mockedLogRecord));
  }

  @Test
  public void testNoParams() throws SQLException{
    System.out.println("\nTest no params");
    String compiledStmtQry = "" +
        "INSERT INTO log_aktywnosci(uzytkownik_id, czytnik_id, opis) " +
        "VALUES(NULL, NULL, 'INFO Test no params');";
    when(mockedConnection.prepareStatement(prepStmtQry)).thenReturn(mockedStatement);
    when(mockedLogRecord.getMessage()).thenReturn("Test no params");
    when(mockedLogRecord.getLevel()).thenReturn(Level.INFO);
    when(mockedStatement.toString()).thenReturn("\n" + compiledStmtQry);
    
    Formatter formater = new MySqlLogFormatter(mockedConnection);
    assertEquals(compiledStmtQry, formater.format(mockedLogRecord));
  }
  
  @Test
  public void testNoUser() throws SQLException{
    System.out.println("\nTest no user");
    String compiledStmtQry = "" +
        "INSERT INTO log_aktywnosci(uzytkownik_id, czytnik_id, opis) " +
        "VALUES(NULL, 2, 'INFO Test no user');";
    mockedParameters[1] = new Integer(2);
    when(mockedLogRecord.getParameters()).thenReturn(mockedParameters);
    when(mockedConnection.prepareStatement(prepStmtQry)).thenReturn(mockedStatement);
    when(mockedLogRecord.getMessage()).thenReturn("Test no user");
    when(mockedLogRecord.getLevel()).thenReturn(Level.INFO);
    when(mockedStatement.toString()).thenReturn("\n" + compiledStmtQry);
    
    Formatter formater = new MySqlLogFormatter(mockedConnection);
    assertEquals(compiledStmtQry, formater.format(mockedLogRecord));
  }
  
  @Test
  public void testNoMessage() throws SQLException{
    System.out.println("\nTest no message");
    String compiledStmtQry = "" +
        "INSERT INTO log_aktywnosci(uzytkownik_id, czytnik_id, opis) " +
        "VALUES(1, 2, 'INFO ');";
    mockedParameters[0] = new Integer(1);
    mockedParameters[1] = new Integer(2);
    when(mockedLogRecord.getParameters()).thenReturn(mockedParameters);
    when(mockedConnection.prepareStatement(prepStmtQry)).thenReturn(mockedStatement);
    when(mockedLogRecord.getLevel()).thenReturn(Level.INFO);
    when(mockedStatement.toString()).thenReturn("\n" + compiledStmtQry);
    
    Formatter formater = new MySqlLogFormatter(mockedConnection);
    assertEquals(compiledStmtQry, formater.format(mockedLogRecord));
  }
  
  @Test
  public void testNoLevel() throws SQLException{
    System.out.println("\nTest no level");
    String compiledStmtQry = "" +
        "INSERT INTO log_aktywnosci(uzytkownik_id, czytnik_id, opis) " +
        "VALUES(1, 2, 'INFO Test no level');";
    mockedParameters[0] = new Integer(1);
    mockedParameters[1] = new Integer(2);
    when(mockedLogRecord.getParameters()).thenReturn(mockedParameters);
    when(mockedConnection.prepareStatement(prepStmtQry)).thenReturn(mockedStatement);
    when(mockedLogRecord.getMessage()).thenReturn("Test no level");
    when(mockedStatement.toString()).thenReturn("\n" + compiledStmtQry);
    
    Formatter formater = new MySqlLogFormatter(mockedConnection);
    assertEquals(compiledStmtQry, formater.format(mockedLogRecord));
  }
  
}
