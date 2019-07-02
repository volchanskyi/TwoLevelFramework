package com.automationpractice.appmanager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

abstract public class ApplicationManagerHelper {
	private ApplicationManager app;
	private Properties properties;
	private WebDriver wd;
	private String browser;
	private RegistrationHelper registrationHelper;

	// Init Logger for ApplicationManager.class
	protected static final Logger APP_MANAGER_LOGGER = LoggerFactory.getLogger(ApplicationManager.class);

	// Get property key from the file
	public String getProperty(String key) {
		return getProperties().getProperty(key);

	}

	public void init() {
		String target = System.getProperty("target", "local");
		try {
			getProperties().load(new FileReader(new File(String.format("src/test/resources/%s.properties", target))));
		} catch (FileNotFoundException e) {
			APP_MANAGER_LOGGER.error(e.toString());
		} catch (IOException e) {
			APP_MANAGER_LOGGER.error(e.toString());
		}
	}

	public void stop() {
		// Lazy init
		if (getWd() != null) {
			getWd().quit();
		}

	}

	// Set webdriver path for local tests
	protected String setWebDriverPath(String browser) {
		String path;
		if (System.getProperty("platform").toUpperCase().contains("WINDOWS")) {
			path = "src/test/resources/webdriver/webdriver.exe";
		} else if (System.getProperty("platform").toUpperCase().contains("MAC OS X")) {
			path = "src/test/resources/webdriver/webdriver";
		} else
			throw new IllegalArgumentException("Unknown OS");
		return System.setProperty("webdriver." + browser + ".driver", path);
	}

	public Properties getProperties() {
		return properties;
	}

	public void setProperties(Properties properties) {
		this.properties = properties;
	}

	public WebDriver getWd() {
		return wd;
	}

	public void setWd(WebDriver wd) {
		this.wd = wd;
	}

	public RegistrationHelper getRegistrationHelper() {
		return registrationHelper;
	}

	public void setRegistrationHelper(RegistrationHelper registrationHelper) {
		this.registrationHelper = registrationHelper;
	}

	public String getBrowser() {
		return browser;
	}

	public void setBrowser(String browser) {
		this.browser = browser;
	}

	public ApplicationManager getApp() {
		return app;
	}

	public void setApp(ApplicationManager app) {
		this.app = app;
	}
}
