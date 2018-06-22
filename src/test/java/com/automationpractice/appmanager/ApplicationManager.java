package com.automationpractice.appmanager;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

public class ApplicationManager {
    private final Properties properties;
    private WebDriver wd;

    private String browser;
    private RegistrationHelper registrationHelper;

    public ApplicationManager(String browser) {
	this.browser = browser;
	properties = new Properties();
    }

    //Refactor (add params to choose browser types)
    public void init() throws IOException {
	String target = System.getProperty("target", "local");
	properties.load(new FileReader(new File(String.format("src/test/resources/%s.properties", target))));
	
	System.setProperty("webdriver.gecko.driver", "src/test/resources/webdrivers/pc/geckodriver.exe");

    }

    public void stop() {
	//Lazy init
	if (wd !=null) {
	    wd.quit();
	}
	
    }
    
    public HttpSession newSession() {
	return new HttpSession(this);
    }

    public String getProperty(String key) {
	return properties.getProperty(key);
	
    }

    public RegistrationHelper registration() {
	//Lazy init
	if (registrationHelper == null) {
	registrationHelper =  new RegistrationHelper(this);
    }
	return registrationHelper;
    }

    public WebDriver getDriver() throws MalformedURLException {
	//Lazy init
	if ("".equals(properties.getProperty("selenium.server")) && wd == null) {
	    if (browser.equals(BrowserType.FIREFOX)) {
		    wd = new FirefoxDriver();
		} else if (browser.equals(BrowserType.CHROME)) {
		    wd = new ChromeDriver();
		} else if (browser.equals(BrowserType.IE)) {
		    wd = new InternetExplorerDriver();
		}
		wd.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
		wd.get(properties.getProperty("web.baseUrl"));
	} else {
	    DesiredCapabilities capabilities = new DesiredCapabilities();
	    capabilities.setBrowserName(browser);
	    wd = new RemoteWebDriver(new URL(properties.getProperty("selenium.server")), capabilities);
	}
	return wd;
    }
    
   
}
