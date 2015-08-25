package autoryzacja.tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ GroupAuthorizationMethodTest.class,
    GraphAuthorizationMethodTest.class })
public class AutoryzacjaTestSuite {

}
