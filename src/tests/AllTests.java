package tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ CardTest.class, HandComparisionTest.class, HandTest.class, InputReaderTest.class,
    RankCalculatorTest.class })
public class AllTests {

}
