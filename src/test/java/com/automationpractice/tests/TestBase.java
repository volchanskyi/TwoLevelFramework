package com.automationpractice.tests;

import java.lang.reflect.Method;
import java.util.Arrays;

import org.openqa.selenium.remote.BrowserType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestContext;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Listeners;

import com.automationpractice.appmanager.ApplicationManager;

@Listeners(TestListener.class)
public class TestBase {

	final private Logger logger = LoggerFactory.getLogger(TestBase.class);

	protected static final ApplicationManager APP = new ApplicationManager(
			// for local GUI tests
			System.getProperty("browser", BrowserType.CHROME));

	@BeforeSuite(alwaysRun = true)
	public void setUp(ITestContext context) throws Exception {
		APP.init();
		context.setAttribute("app", APP);
	}

	@BeforeMethod(alwaysRun = true)
	public void logTestStart(Method method, Object[] parameters) {
		if (isDebugEnabled() == true) {
			logger.debug("Start test " + method.getName() + " with params " + Arrays.asList(parameters));
		}
	}

	@AfterMethod(alwaysRun = true)
	public void logTestStop(Method method, Object[] parameters) {
		if (isDebugEnabled() == true) {
			logger.debug("Stop test " + method.getName());
		}
	}

	@AfterSuite(alwaysRun = true)
	public void tearDown(ITestContext context) {
		logger.error("Failed tests " + Arrays.asList(context.getFailedTests()));
		logger.warn("Retrieves information about the failed configuration method invocations "
				+ Arrays.asList(context.getFailedConfigurations()));
		logger.info(
				"The host where this test was run, or null if it was run locally. " + Arrays.asList(context.getHost()));
		APP.stop();
	}

	private boolean isDebugEnabled() {
		try {
			if (System.getProperty("debug") == null || System.getProperty("debug").isEmpty()
					|| System.getProperty("debug").contains("disabled")) {
				return false;
			} else if (System.getProperty("debug").equalsIgnoreCase("enabled"))
				return true;
			else
				return false;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;

	}

}
