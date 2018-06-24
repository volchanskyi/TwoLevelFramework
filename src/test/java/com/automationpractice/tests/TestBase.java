package com.automationpractice.tests;

import org.openqa.selenium.remote.BrowserType;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import com.automationpractice.appmanager.ApplicationManager;

public class TestBase {

    protected static final ApplicationManager APP = new ApplicationManager(
	    //for local GUI tests
	    System.getProperty("browser", BrowserType.CHROME)
	    );

    @BeforeSuite
    public void setUp() throws Exception {
	APP.init();
    }

    @AfterSuite(alwaysRun = true)
    public void tearDown() {
	APP.stop();
    }

}
