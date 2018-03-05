package com.automationpractice.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class HelperBase {

    
    	protected ApplicationManager app;
    	protected WebDriver wd;
    	
    public HelperBase(ApplicationManager app) {
	this.app = app;
	this.wd = app.getDriver();
    }
    
    protected void click(By locator) {
	wd.findElement(locator).click();
    }
    
    protected void type(By locator, String text) {
	click(locator);
	if (text != null) {
	    String existingText = wd.findElement(locator).getAttribute("value");
	    if (! text.equals(existingText)) {
		wd.findElement(locator).clear();
		wd.findElement(locator).sendKeys(text);
	    }
	}
    }

    	
}
