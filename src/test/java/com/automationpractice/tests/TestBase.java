package com.automationpractice.tests;

import org.openqa.selenium.remote.BrowserType;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import com.automationpractice.appmanager.ApplicationManager;

public class TestBase {

    protected static final ApplicationManager APP = new ApplicationManager(
	    System.getProperty("browser", BrowserType.FIREFOX));

    @BeforeSuite
    public void setUp() throws Exception {
	APP.init();
    }

    @AfterSuite(alwaysRun = true)
    public void tearDown() {
	APP.stop();
    }

}
