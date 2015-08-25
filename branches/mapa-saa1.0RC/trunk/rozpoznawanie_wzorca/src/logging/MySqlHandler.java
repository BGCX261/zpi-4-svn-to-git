package logging;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

public class MySqlHandler extends Handler {

  Connection connection;

  public MySqlHandler(Connection conn) {
    connection = conn;
  }

  @Override
  public void close() throws SecurityException {
    // TODO Auto-generated method stub

  }

  @Override
  public void flush() {
    // TODO Auto-generated method stub

  }

  @Override
  public void publish(LogRecord record) {
    try {
      String format = getFormatter().format(record);
      connection.createStatement().executeUpdate(format);
    } catch (SQLException e) {
      // TODO logowanie
      e.printStackTrace();
    }

  }

}
