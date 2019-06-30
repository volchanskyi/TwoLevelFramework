package com.automationpractice.appmanager;

import java.net.MalformedURLException;

import org.openqa.selenium.By;

import com.automationpractice.model.RegistrationFormData;

public class RegistrationHelper extends HelperBase {

	RegistrationHelper(ApplicationManager app) throws MalformedURLException {
		super(app);

	}

	public RegistrationHelper initRegistrationUsingEmailWith(String email) {
		getWd().get(useProperty("web.baseUrl") + "index.php?controller=authentication&back=my-account#account-creation");
		type(By.cssSelector(useProperty("locator.newUserNameField")), email);
		click(By.cssSelector(useProperty("locator.createAccBtn(RP)")));
		return this;

	}

	public HelperBase fillOutRegistrationFormWith(RegistrationFormData registrationFormData) {
		type(By.name(useProperty("locator.fNameTxtFld(RP)")), registrationFormData.getFirstName());
		type(By.name(useProperty("locator.lastNameTextField")), registrationFormData.getFirstName());
		type(By.cssSelector(useProperty("locator.newPasswordTextField")), registrationFormData.getPassword());
		type(By.cssSelector("#firstname"), registrationFormData.getFirstName());
		type(By.cssSelector("#lastname"), registrationFormData.getFirstName());
		type(By.cssSelector(useProperty("locator.newAddressTextField")), registrationFormData.getAddress());
		type(By.cssSelector(useProperty("locator.newCityTextField")), registrationFormData.getCityName());
		type(By.cssSelector(useProperty("locator.newPostCodeTextField")), registrationFormData.getPostCode());
		selectFromDDM(By.cssSelector(useProperty("locator.stateDropDownMenu")), registrationFormData.getState());
		type(By.cssSelector(useProperty("locator.newMobilePhoneTextField")), registrationFormData.getPhoneNumber());
		type(By.cssSelector("#alias"), registrationFormData.getAddress());
		click(By.cssSelector(useProperty("locator.registerAccountButton")));
		return this;
	}

	// TODO Method is not finished!
	public void finishWith(String confirmationLink, String password) {
		getWd().get(confirmationLink);
		type(By.name("password"), password);
		type(By.name("password_confirm"), password);
		click(By.xpath(useProperty("locator.createAccBtn(RP)")));
	}

	public boolean verifyWithTitle(String title) {
		String actualTitle = getPageTitle(title);
		return actualTitle.equals(title);
	}

}
