package logging;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.LogRecord;

public class MySqlLogFormatter extends Formatter {

  private PreparedStatement ps;

  public MySqlLogFormatter(Connection connection) {
    try {
      ps = connection.prepareStatement("\n"
          + "INSERT INTO log_aktywnosci(uzytkownik_id, czytnik_id, opis) "
          + "VALUES(?, ?, ?);");
    } catch (SQLException e) {
      // FIXME AppLog
      e.printStackTrace();
    }
  }

  @Override
  public String format(LogRecord logRec) {
    try {
      if (logRec.getParameters() == null || logRec.getParameters()[0] == null) {
        ps.setNull(1, Types.NULL);
      } else {
        ps.setInt(1, (Integer) logRec.getParameters()[0]);
      }
      if (logRec.getParameters() == null || logRec.getParameters()[1] == null) {
        //FIXME: w tym miejscu można rzucić wyjątkiem
        //      nie można się autoryzować się do czytnika NULL
        ps.setNull(2, Types.NULL);
      } else {
        ps.setInt(2, (Integer) logRec.getParameters()[1]);
      }
      ps.setString(3,
          (logRec.getLevel() == Level.WARNING ? "WARNING " : "INFO ") +
          logRec.getMessage());
    } catch (SQLException e) {
      // TODO AppLog
      e.printStackTrace();
    }
    // FIXME: gupi preparedstatement nie ma metody getStatement!!! duh!
    return ps.toString().split("\n")[1];
  }

}
