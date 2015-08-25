package logging_tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ MySqlHandlerTest.class, MySqlLogFormaterTest.class,
    SystemAccessLoggerTest.class })
public class LoggingTestSuite {

}
