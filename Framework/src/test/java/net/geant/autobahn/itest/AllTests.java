package net.geant.autobahn.itest;

import net.geant.autobahn.itest.env1.AllEnv1Tests;
import net.geant.autobahn.itest.singledomain.ResourcesReservationTest;
import net.geant.autobahn.itest.singledomain.ResourcesReservationVlanTranslationTest;
import net.geant.autobahn.itest.singledomain.ResourcesReservationVlanTranslationTest2;

import org.apache.cxf.common.logging.Log4jLogger;
import org.apache.cxf.common.logging.LogUtils;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
	AllEnv1Tests.class, ResourcesReservationTest.class, ResourcesReservationVlanTranslationTest.class,
	ResourcesReservationVlanTranslationTest2.class
})
public class AllTests {

	@BeforeClass
	public static void initialize() {
		LogUtils.setLoggerClass(Log4jLogger.class);
	}
	
}
