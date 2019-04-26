package com.automationpractice.appmanager;

import java.net.MalformedURLException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HelperBase {

	private ApplicationManager app;
	private WebDriver wd;
	private WebDriverWait wait;

	HelperBase(ApplicationManager app) throws MalformedURLException {
		this.app = app;
		this.setWd(app.initWebDriver());
		// Assure all browsers have the same screen resolution and starting point (for
		// UI tests)
		this.getWd().manage().window().setPosition(new Point(0, 0));
		this.getWd().manage().window().setSize(new Dimension(1280, 1024));
		// set EXPLICIT timeouts
		this.wait = new WebDriverWait(this.getWd(), 15);
		// Set timeout for Async Java Script
		this.getWd().manage().timeouts().setScriptTimeout(20, TimeUnit.SECONDS);
		this.getWd().manage().timeouts().pageLoadTimeout(15, TimeUnit.SECONDS);
	}

	protected void navigateTo(String url) {
		this.getWd().navigate().to(url);
		wait.until(ExpectedConditions.urlToBe(url));
	}

	protected void click(By locator) {
		// wd.findElement(locator).click();
		wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
		// this.getObject(p, objectName, objectType))
	}

	protected void type(By locator, String text) {
		click(locator);
		if (text != null) {
			String existingText = wait.until(ExpectedConditions.visibilityOfElementLocated(locator))
					.getAttribute("value");
			// wd.findElement(locator).getAttribute("value");
			if (!text.equals(existingText)) {
				wait.until(ExpectedConditions.visibilityOfElementLocated(locator)).clear();
				wait.until(ExpectedConditions.visibilityOfElementLocated(locator)).sendKeys(text);
			}
		}
	}

	// select from dd menu
	protected void selectFromDDM(By locator, String value) {
		// WebElement ddMenu = waitForPresenceOfElement(p, objectName, objectType);

		Select choose = new Select(wait.until(ExpectedConditions.presenceOfElementLocated(locator)));
		// Selecting value
		choose.selectByVisibleText(value);

	}

	// Get title by using embedded method
	protected String getPageTitle(String title) {
		wait.until(ExpectedConditions.titleIs(title));
		return getWd().getTitle();
	}

	protected String getActiveOverlay(By locator, String overlay) {
		wait.until(ExpectedConditions.textToBe(locator, overlay));
		return wait.until(ExpectedConditions.visibilityOfElementLocated(locator)).getAttribute("value");
	}

	public String useProperty(String property) {
		return app.getProperty(property);
	}

	public WebDriver getWd() {
		return wd;
	}

	public void setWd(WebDriver wd) {
		this.wd = wd;
	}

}
