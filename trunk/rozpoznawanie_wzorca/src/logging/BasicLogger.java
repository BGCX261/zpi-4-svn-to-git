package logging;

import java.io.File;
import java.io.IOException;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * Klasa logujaca wydarzenia w kodzie aplikacji
 * 
 * @author elistan
 * 
 */
public class BasicLogger {
  /**
   * Logger dla zdarzen wewnetrznych aplikacji
   */
  public static final Logger applicationLogger;
  static {
    applicationLogger = Logger.getLogger("AppLogger");
    applicationLogger.setLevel(Level.INFO);
    Handler console = new ConsoleHandler();
    console.setFormatter(new SimpleFormatter());
    console.setLevel(Level.ALL);
    Handler file;
    try {
      file = new FileHandler("applog.txt");
      file.setFormatter(new SimpleFormatter());
      applicationLogger.addHandler(console);
      applicationLogger.addHandler(file);
      File f = new File("/home/elistan/");
      if (f.exists() && f.isDirectory()) {
        file = new FileHandler(f.getPath() + "log_z_appletu.txt");
        file.setFormatter(new SimpleFormatter());
        file.setLevel(Level.ALL);
        applicationLogger.addHandler(file);
      }
    } catch (SecurityException e) {
      e.printStackTrace();
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

  }
}
