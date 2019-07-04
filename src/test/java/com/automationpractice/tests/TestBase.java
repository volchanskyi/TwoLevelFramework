package com.automationpractice.tests;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;

import org.testng.ITestContext;
import org.testng.ITestNGMethod;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Listeners;

@Listeners(TestListener.class)
public class TestBase extends TestBaseHelper {

	@BeforeSuite(alwaysRun = true)
	public void setUp(ITestContext context) throws Exception {
		APP.init();
		context.setAttribute("app", APP);
	}

	@BeforeMethod(alwaysRun = true)
	public void logTestStart(Method method, Object[] parameters) throws Exception {
		if (isDebugTestsEnabled() == true) {
			TEST_BASE_LOGGER.debug("Start test {} with params {}", method.getName(), Arrays.asList(parameters.toString()));
		}
	}

	@AfterMethod(alwaysRun = true)
	public void logTestStop(Method method, Object[] parameters) throws Exception {
		if (isDebugTestsEnabled() == true) {
			TEST_BASE_LOGGER.debug("Stop test {}", method.getName());
		}
		return;
	}

	@AfterSuite(alwaysRun = true)
	public void tearDown(ITestContext context) throws Exception {
		Collection<ITestNGMethod> result = context.getFailedTests().getAllMethods();
		TEST_BASE_LOGGER.error("{} Failed tests {}", result.size(), result);
		TEST_BASE_LOGGER.warn("Retrieves information about the failed configuration method invocations  {}",
				Arrays.asList(context.getFailedConfigurations()));
		TEST_BASE_LOGGER.info("The host where this test was run, or null if it was run locally. {}",
				Arrays.asList(context.getHost()));
		APP.stop();
	}

}
