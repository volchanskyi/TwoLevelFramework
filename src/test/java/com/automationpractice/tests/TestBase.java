package com.automationpractice.tests;

import org.openqa.selenium.remote.BrowserType;
import org.testng.ITestContext;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Listeners;

import com.automationpractice.appmanager.ApplicationManager;



@Listeners(TestListener.class)
public class TestBase {

    protected static final ApplicationManager APP = new ApplicationManager(
	    // for local GUI tests
	    System.getProperty("browser", BrowserType.CHROME));

    @BeforeSuite
    public void setUp(ITestContext context) throws Exception {
	APP.init();
	context.setAttribute("app", APP);
    }

    @AfterSuite(alwaysRun = true)
    public void tearDown() {
	APP.stop();
    }

}
