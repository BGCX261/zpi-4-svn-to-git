package logging;

import java.util.logging.Level;
import java.util.logging.Logger;

import baza_danych.DatabaseFacade;

import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class SystemAccessLogger {

  public final Logger systemAccessLogger;
  public final static String loggerName = "SysAccLogger";
  DatabaseFacade db;

  @Inject
  public SystemAccessLogger(DatabaseFacade db) {
    this.db = db;
    systemAccessLogger = Logger.getLogger(loggerName);
    systemAccessLogger.setLevel(Level.INFO);
    systemAccessLogger.addHandler(db.createSqlHandler());
  }

}
