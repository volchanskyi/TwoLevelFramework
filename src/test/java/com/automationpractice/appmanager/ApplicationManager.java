package com.automationpractice.appmanager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.SessionNotCreatedException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ApplicationManager {
	private final Properties properties;
	private WebDriver wd;
	private String browser;
	private RegistrationHelper registrationHelper;

	// Init Logger for ApplicationManager.class
	final private Logger appManagerlogger = LoggerFactory.getLogger(ApplicationManager.class);

	public ApplicationManager(String browser) {
		this.browser = browser;
		properties = new Properties();

	}

	public void init() {
		String target = System.getProperty("target", "local");
		try {
			properties.load(new FileReader(new File(String.format("src/test/resources/%s.properties", target))));
		} catch (FileNotFoundException e) {
			appManagerlogger.error(e.toString());
		} catch (IOException e) {
			appManagerlogger.error(e.toString());
		}
	}

	public void stop() {
		// Lazy init
		if (wd != null) {
			wd.quit();
		}

	}

	public HttpSession newSession() {
		return new HttpSession(this);
	}

	public HttpRegistrationSession newRegistrationSession() {
		return new HttpRegistrationSession(this);
	}

	public String getProperty(String key) {
		return properties.getProperty(key);

	}

	public RegistrationHelper registration() {
		// Lazy init
		if (registrationHelper == null) {
			try {
				registrationHelper = new RegistrationHelper(this);
			} catch (MalformedURLException e) {
				appManagerlogger.error(e.toString());
			}
		}
		return registrationHelper;
	}

	public WebDriver getDriver() throws SessionNotCreatedException {
		// Load locators
		try {
			properties.load(new FileReader(new File(String.format("src/test/resources/locator.properties"))));
		} catch (FileNotFoundException e) {
			// If the the property file cant be found
			appManagerlogger
					.error(e.toString() + " Check *.property file in the source folder. Was the build option passed?");
		} catch (IOException e) {
			// FS access error
			appManagerlogger.error(e.toString());
		}

		// If we dont use selenium server then run local browser
		if ("".equals(properties.getProperty("selenium.server"))) {
			// Lazy init
			try {
				if (wd == null) {
					if (browser.equals(BrowserType.FIREFOX)) {
//						if (isPlatform("WINDOWS")) {
//							setSystemProperty("webdriver.gecko.driver",
//									"src/test/resources/webdrivers/pc/geckodriver.exe");
//						} else {
//							setSystemProperty("webdriver.gecko.driver",
//									"src/test/resources/webdrivers/mac/geckodriver");
//						}
						System.setProperty("webdriver.gecko.driver",
								"src/test/resources/webdrivers/pc/geckodriver.exe");
						wd = new FirefoxDriver();
					} else if (browser.equals(BrowserType.CHROME)) {
//						if (isPlatform("WINDOWS")) {
//							setSystemProperty("webdriver.chrome.driver",
//									"src/test/resources/webdrivers/pc/chromedriver.exe");
//						} else {
//							setSystemProperty("webdriver.chrome.driver",
//									"src/test/resources/webdrivers/mac/chromedriver");
//						}
						System.setProperty("webdriver.chrome.driver",
								"src/test/resources/webdrivers/pc/chromedriver.exe");
						wd = new ChromeDriver();
					} else if (browser.equals(BrowserType.SAFARI)) {
						System.setProperty("webdriver.safari.driver", "/usr/bin/safaridriver");
						wd = new SafariDriver();
					} else if (browser.equals(BrowserType.IE)) {
						System.setProperty("webdriver.ie.driver",
								"src/test/resources/webdrivers/pc/IEDriverServer.exe");
						InternetExplorerOptions options = new InternetExplorerOptions();
						options.ignoreZoomSettings();
						options.destructivelyEnsureCleanSession();
						options.introduceFlakinessByIgnoringSecurityDomains();
						wd = new InternetExplorerDriver(options);
					} else if (browser.equals(BrowserType.EDGE)) {
						System.setProperty("webdriver.edge.driver",
								"src/test/resources/webdrivers/pc/MicrosoftWebDriver.exe");
						EdgeOptions options = new EdgeOptions();
						options.setCapability(CapabilityType.SUPPORTS_JAVASCRIPT, true);
						options.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
						options.setCapability(CapabilityType.SUPPORTS_ALERTS, true);
						options.setCapability("InPrivate", true);
						wd = new EdgeDriver(options);
					} else
						throw new IllegalArgumentException("Unknown OS");
					wd.get(properties.getProperty("web.baseUrl"));
				}
			} catch (NullPointerException e) {
				// If no/wrong build option passed
				appManagerlogger
						.error(e.toString() + " | The browser wasn`t initialized. Check the build option presented");
			} catch (IllegalStateException e) {
				// the webdriver binary issue
				appManagerlogger.error(e.toString()
						+ " | The browser wasn`t initialized. Check the webdriver presented / valid build option passed");
			} catch (WebDriverException e) {
				appManagerlogger.error(e.toString()
						+ " | Are you trying to run the build on the currently available platform/configuration?");
			}
		} else {
			// Run tests remotely
			try {
				DesiredCapabilities capabilities = new DesiredCapabilities();
				capabilities.setBrowserName(browser);
				capabilities.setPlatform(Platform.fromString(System.getProperty("platform", "win10")));
				wd = new RemoteWebDriver(new URL(properties.getProperty("selenium.server")), capabilities);
			} catch (WebDriverException e) {
				// If there`s capability issue. Can`t start the remote webdriver
				appManagerlogger.error(e.toString()
						+ " | Are you trying to run the build on the currently available platform/configuration?"
						+ "| Are you trying to run the build locally? Was the build option passed?");
			} catch (MalformedURLException e) {
				// If loading property failed or no build option passed
				appManagerlogger.error(
						e.toString() + " | Most likely loading property file has failed. Was the build option passed?");
			}
		}
		return wd;
	}

	public byte[] takeScreenshot() {
		// Lazy init
		// no need to take screenshot if there`s no UI involved
		if (wd != null) {
			return ((TakesScreenshot) wd).getScreenshotAs(OutputType.BYTES);
		} else
			return null;
	}

//	// Distinguish OS
//	private boolean isPlatform(String platform) {
//		return System.getProperty("platform").toUpperCase().contains(platform);
//	}
//
//	// Set system property
//	private String setSystemProperty(String webDriver, String webDriverPath) {
//		return System.setProperty(webDriver, webDriverPath);
//	}

}
