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
import com.automationpractice.matchers.MatcherBase;

@Listeners(TestListener.class)
public class TestBase extends MatcherBase {

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
	private void beforeMethod(Method method, Object[] parameters) {
		logger.debug("Start test " + method.getName() + " with params " + Arrays.asList(parameters));

	}

	@AfterMethod(alwaysRun = true)
	private void logTestStop(Method method, Object[] parameters) {
		logger.debug("Stop test " + method.getName());

	}

	@AfterSuite(alwaysRun = true)
	public void tearDown() {
		APP.stop();
	}

}
