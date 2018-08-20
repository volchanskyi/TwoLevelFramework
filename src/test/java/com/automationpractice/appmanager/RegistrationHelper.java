package com.automationpractice.appmanager;

import java.net.MalformedURLException;

import org.openqa.selenium.By;

public class RegistrationHelper extends HelperBase {

    public RegistrationHelper(ApplicationManager app) throws MalformedURLException {
	super(app);

    }

    public RegistrationHelper initRegistrationUsingEmailWith(String email) {
	wd.get(useProperty("web.baseUrl") + "index.php?controller=authentication&back=my-account#account-creation");
	type(By.cssSelector(useProperty("locator.newUserNameField")), email);
	click(By.cssSelector(useProperty("locator.createAccBtn(RP)")));
	return this;

    }
    
    public HelperBase fillOutRegistrationFormWith(String fName, String lName, String password, String address,
	    String city, String postcode, String state, String phone) {
	// wd.get(app.getProperty("web.baseUrl") +
	// "index.php?controller=authentication&back=my-account#account-creation");
	type(By.name(useProperty("locator.fNameTxtFld(RP)")), fName);
	type(By.name(useProperty("locator.lastNameTextField")), lName);
	type(By.cssSelector(useProperty("locator.newPasswordTextField")), password);
	type(By.cssSelector("#firstname"), fName);
	type(By.cssSelector("#lastname"), lName);
	type(By.cssSelector(useProperty("locator.newAddressTextField")), address);
	type(By.cssSelector(useProperty("locator.newCityTextField")), city);
	type(By.cssSelector(useProperty("locator.newPostCodeTextField")), postcode);
	selectFromDDM(By.cssSelector(useProperty("locator.stateDropDownMenu")), state);
	type(By.cssSelector(useProperty("locator.newMobilePhoneTextField")), phone);
	type(By.cssSelector("#alias"), address);
	click(By.cssSelector(useProperty("locator.registerAccountButton")));
	return this;
    }

    // TODO Method is not finished!
    public void finishWith(String confirmationLink, String password) {
	wd.get(confirmationLink);
	type(By.name("password"), password);
	type(By.name("password_confirm"), password);
	click(By.xpath(useProperty("locator.createAccBtn(RP)")));
    }

    
	public boolean verifyWithTitle(String title) {
    	String actualTitle = getPageTitle(title);
    	return actualTitle.equals(title);
    }

}
