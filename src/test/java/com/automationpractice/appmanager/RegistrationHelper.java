package com.automationpractice.appmanager;

import java.net.MalformedURLException;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

public class RegistrationHelper extends HelperBase {

    public RegistrationHelper(ApplicationManager app) throws MalformedURLException {
	super(app);

    }

    public RegistrationHelper initRegistrationUsingEmailWith(String email) {
	wd.get(app.getProperty("web.baseUrl") + "index.php?controller=authentication&back=my-account#account-creation");
	type(By.cssSelector("#email_create"), email);
	click(By.cssSelector("#SubmitCreate"));
	return this;

    }

    public RegistrationHelper fillOutRegistrationFormWith(String fName, String lName, String password, String address,
	    String city, String postcode, String state, String phone) {
	// wd.get(app.getProperty("web.baseUrl") +
	// "index.php?controller=authentication&back=my-account#account-creation");
	type(By.name("customer_firstname"), fName);
	type(By.name("customer_lastname"), lName);
	type(By.cssSelector("#passwd"), password);
	type(By.cssSelector("#firstname"), fName);
	type(By.cssSelector("#lastname"), lName);
	type(By.cssSelector("#address1"), address);
	type(By.cssSelector("#city"), city);
	type(By.cssSelector("#postcode"), postcode);
	selectFromDDM(By.cssSelector("#id_state"), state);
	type(By.cssSelector("#phone_mobile"), phone);
	type(By.cssSelector("#alias"), address);
	click(By.cssSelector("#submitAccount"));
	return this;
    }

    // TODO Method is not finished!
    public void finish(String confirmationLink, String password) {
	wd.get(confirmationLink);
	type(By.name("password"), password);
	type(By.name("password_confirm"), password);
	click(By.xpath("//*[@id='SubmitCreate']/span"));
    }

    public boolean verify(String text) {
	return getPageTitle(By.cssSelector("title")).equals(text);
    }

}
