package uslugi.tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ AuthorizationServiceTest.class, CreateServiceTest.class,
    FingerprintAddStrategyTest.class, FingerprintReadingStrategyTest.class,
    ReadServiceTest.class })
public class UslugiTestSuite {

}
