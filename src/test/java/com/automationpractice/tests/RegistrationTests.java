package com.automationpractice.tests;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.testng.Assert.assertTrue;

import java.io.IOException;

import javax.mail.MessagingException;

import org.testng.annotations.Test;

import com.automationpractice.appmanager.HttpSession;
import com.automationpractice.appmanager.RegistrationHelper;

public class RegistrationTests extends TestBase {

	@Test(groups = {
			"API" }, priority = 3, dataProvider = "validCredentialsForRegistrationController", dataProviderClass = TestDataProviders.class)
	public void testRegisterNewAccountUsingAPI(String email, String fName, String lName, String password,
			String address, String city, String postalCode, String state, String phone)
			throws MessagingException, IOException, InterruptedException {
		String title = "My account - My Store";
		String newEmail = email;
		// String link = "https://blablabla"; (not implemented yet) DO NOT DELETE
		HttpSession session = APP.newSession();
		assertTrue(session.createEmailWith(newEmail));
		assertTrue(session.signUpWith(newEmail));
		// assertTrue(session.verifyActivationLink(link)); (automationpractice.com
		// doesn`t send an activation link) DO NOT DELETE
		assertTrue(
				session.registerWith(fName, lName, password, address, city, postalCode, state, phone, title, newEmail));
	}

	@Test(groups = { "API" }, priority = 2)
	public void testRegisterNewAccountWithExistedCredentialsUsingAPI() throws IOException {
		HttpSession session = APP.newSession();
		String errMsg = "An account using this email address has already been registered. Please enter a valid password or request a new one. ";
		assertThat(session.registerExistedAccountWithApiUsing("volchanskij@gmail.com"), equalTo(errMsg));
	}

	@Test(groups = { "API" }, priority = 1, dataProvider = "invalidEmail", dataProviderClass = TestDataProviders.class)
	public void testRegisterNewAccountWithWrongEmailFormatUsingAPI(String email) throws IOException {
		HttpSession session = APP.newSession();
		String errMsg = "Invalid email address.";
		assertThat(session.registerExistedAccountWithApiUsing(email), equalTo(errMsg));
	}

	@Test(groups = {
			"GUI" }, priority = 100, dataProvider = "validCredentialsForRegistrationPage", dataProviderClass = TestDataProviders.class)
	public void testRegisterNewAccountUsingGUI(String email, String fName, String lName, String password,
			String address, String city, String postalCode, String state, String phone)
			throws MessagingException, IOException, InterruptedException {
		String myAccountPageTitle = "My account - My Store";
		// String link = "https://blablabla"; (not implemented yet) DO NOT DELETE
		RegistrationHelper regHelper = APP.registration();
		// HttpSession session = APP.newSession();
		// assertTrue(session.createEmail(email));
		// assertTrue(session.signUp(email + "@mailinator.com"));
		// assertTrue(session.verifyActivationLink(link)); (automationpractice.com
		// doesn`t send an activation link) DO NOT DELETE
		regHelper.initRegistrationUsingEmailWith(email).fillOutRegistrationFormWith(fName, lName, password, address,
				city, postalCode, state, phone);
		assertTrue(regHelper.verifyWithTitle(myAccountPageTitle));
	}

}
