package com.automationpractice.appmanager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

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

public class WebDriverWorker extends ApplicationManagerHelper {

	public WebDriver initWebDriver() throws SessionNotCreatedException {
		// Load locators
		try {
			getProperties().load(new FileReader(new File(String.format("src/test/resources/locator.properties"))));
		} catch (FileNotFoundException e) {
			// If the the property file cant be found
			appManagerlogger
					.error(e.toString() + " Check *.property file in the source folder. Was the build option passed?");
		} catch (IOException e) {
			// FS access error
			appManagerlogger.error(e.toString());
		}
		// If we dont use selenium server then run local browser
		if ("".equals(getProperties().getProperty("selenium.server"))) {
			// Lazy init
			try {
				if (getWd() == null) {
					if (getBrowser().equals(BrowserType.FIREFOX)) {
						setWebDriverPath("gecko");
						setWd(new FirefoxDriver());
					} else if (getBrowser().equals(BrowserType.CHROME)) {
						setWebDriverPath("chrome");
						setWd(new ChromeDriver());
					} else if (getBrowser().equals(BrowserType.SAFARI)) {
						System.setProperty("webdriver.safari.driver", "/usr/bin/safaridriver");
						setWd(new SafariDriver());
					} else if (getBrowser().equals(BrowserType.IE)) {
						setWebDriverPath("ie");
						InternetExplorerOptions options = new InternetExplorerOptions();
						options.ignoreZoomSettings();
						options.destructivelyEnsureCleanSession();
						options.introduceFlakinessByIgnoringSecurityDomains();
						setWd(new InternetExplorerDriver(options));
					} else if (getBrowser().equals(BrowserType.EDGE)) {
						setWebDriverPath("edge");
						EdgeOptions options = new EdgeOptions();
						options.setCapability(CapabilityType.SUPPORTS_JAVASCRIPT, true);
						options.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
						options.setCapability(CapabilityType.SUPPORTS_ALERTS, true);
						options.setCapability("InPrivate", true);
						setWd(new EdgeDriver(options));
					}
					getWd().get(getProperties().getProperty("web.baseUrl"));
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
				capabilities.setBrowserName(getBrowser());
				capabilities.setPlatform(Platform.fromString(System.getProperty("platform", "win10")));
				setWd(new RemoteWebDriver(new URL(getProperties().getProperty("selenium.server")), capabilities));
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
		return getWd();
	}

	public byte[] takeScreenshot() {
		// Lazy init
		// no need to take screenshot if there`s no UI involved
		if (getWd() != null) {
			return ((TakesScreenshot) getWd()).getScreenshotAs(OutputType.BYTES);
		} else
			return null;
	}

}
