package czytnik.tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ DodawaczOdciskowTest.class, WeryfikatorOdciskowTest.class,
    DaneCzytnikaTest.class })
public class CzytnikTestSuite {

}
