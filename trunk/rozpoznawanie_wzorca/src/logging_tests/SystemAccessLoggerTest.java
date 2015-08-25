package logging_tests;

import logging.MySqlHandler;
import logging.SystemAccessLogger;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import baza_danych.DatabaseFacade;

public class SystemAccessLoggerTest {
  SystemAccessLogger systemAccesLogger;
  DatabaseFacade db;

  @Before
  public void setUp() throws Exception {
    db = Mockito.mock(DatabaseFacade.class);
    Mockito.when(db.createSqlHandler()).thenReturn(
        Mockito.mock(MySqlHandler.class));
  }

  @Test
  public void testSystemAccessLogger() {
    systemAccesLogger = new SystemAccessLogger(db);
    Mockito.verify(db).createSqlHandler();
  }

}
