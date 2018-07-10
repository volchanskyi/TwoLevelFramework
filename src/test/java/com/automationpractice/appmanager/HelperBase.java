package com.automationpractice.appmanager;

import java.net.MalformedURLException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HelperBase {

    protected ApplicationManager app;
    protected WebDriver wd;
    protected WebDriverWait wait;

    public HelperBase(ApplicationManager app) throws MalformedURLException {
	this.app = app;
	this.wd = app.getDriver();
	// set EXPLICIT timeouts
	this.wait = new WebDriverWait(this.wd, 15);
	// Set timeout for Async Java Script
	this.wd.manage().timeouts().setScriptTimeout(20, TimeUnit.SECONDS);
	this.wd.manage().timeouts().pageLoadTimeout(15, TimeUnit.SECONDS);
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
//	WebElement ddMenu = waitForPresenceOfElement(p, objectName, objectType);

	Select choose = new Select(wait.until(ExpectedConditions.presenceOfElementLocated(locator)));
	// Selecting value
	choose.selectByVisibleText(value);
//	// Create instance of Javascript executor
//	js = (JavascriptExecutor) driver;
//	// Check if Ajax is still working
//	if ((Boolean) js.executeScript("return jQuery.active == 1")) {
//	    // Wait here to give time for async filtering script to finish
//	    // if we don`t wait here, DOM refreshes while the program continues working
//	    wait.until(ExpectedConditions.jsReturnsValue("return jQuery.active == 0"));
//	}
//	String chosen = choose.getFirstSelectedOption().getText();
//	if (chosen.equals(value)) {
//	    // Return the text that was inserted for verification
//	    return chosen;
//	} else
//	    return "The value hasn`t been selected";
    }

    protected String getPageTitle(By locator) {
	wait.until(ExpectedConditions.presenceOfElementLocated(locator));
	return wd.getTitle();
    }

}
