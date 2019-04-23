package com.automationpractice.tests;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.io.IOException;
import java.net.URISyntaxException;

import javax.mail.MessagingException;

import org.testng.annotations.Test;

import com.automationpractice.appmanager.HttpRegistrationSession;
import com.automationpractice.appmanager.RegistrationHelper;
import com.automationpractice.model.RegistrationFormData;

public class RegistrationTests extends TestBase {

	@Test(groups = { "API",
			"REGISTRATION" }, priority = 3, dataProvider = "validCredentialsForRegistrationController", dataProviderClass = TestDataProviders.class)
	public void testRegisterNewAccountUsingAPI(RegistrationFormData registrationFormData)
			throws MessagingException, IOException, InterruptedException, URISyntaxException {
		String title = "My account - My Store";
		String newEmail = registrationFormData.getEmail();
		String activationLink = "Dear Random User";
		HttpRegistrationSession session = APP.newRegistrationSession();
		session.createEmailWith(newEmail);
		assertTrue(session.signUpWith(registrationFormData));
		assertTrue(session.verifyActivationLink(newEmail, activationLink));
		assertTrue(session.registerWith(registrationFormData, title));
	}

	@Test(groups = { "API", "REGISTRATION" }, priority = 2)
	public void testRegisterNewAccountWithExistedCredentialsUsingAPI() throws IOException {
		HttpRegistrationSession session = APP.newRegistrationSession();
		String errMsg = "An account using this email address has already been registered. Please enter a valid password or request a new one. ";
		assertEquals(session.registerExistedAccountWithApiUsing("volchanskij@gmail.com"), errMsg);
	}

	@Test(groups = { "API", "REGISTRATION" }, priority = 4)
	public void testRegisterNewAccountWithEmptyCredentialsUsingAPI() throws IOException {
		HttpRegistrationSession session = APP.newRegistrationSession();
		String errMsg = "Invalid email address.";
		assertEquals(session.registerExistedAccountWithApiUsing(""), errMsg);
	}

	@Test(groups = { "API",
			"REGISTRATION" }, priority = 1, dataProvider = "invalidEmail", dataProviderClass = TestDataProviders.class)
	public void testRegisterNewAccountWithWrongEmailFormatUsingAPI(String email) throws IOException {
		HttpRegistrationSession session = APP.newRegistrationSession();
		String errMsg = "Invalid email address.";
		assertEquals(session.registerExistedAccountWithApiUsing(email), errMsg);
	}

	@Test(groups = { "GUI", "REGISTRATION",
			"DEFAULTGROUP" }, priority = 100, dataProvider = "validCredentialsForRegistrationController", dataProviderClass = TestDataProviders.class)
	public void testRegisterNewAccountUsingGUI(RegistrationFormData registrationFormData)
			throws MessagingException, IOException, InterruptedException {
		String myAccountPageTitle = "My account - My Store";
		// String link = "https://blablabla"; (not implemented yet) DO NOT DELETE
		RegistrationHelper regHelper = APP.newRegistrationSessionUI();
		// HttpSession session = APP.newSession();
		// assertTrue(session.createEmail(email));
		// assertTrue(session.signUp(email + "@mailinator.com"));
		// assertTrue(session.verifyActivationLink(link)); (automationpractice.com
		// doesn`t send an activation link) DO NOT DELETE
		regHelper.initRegistrationUsingEmailWith(registrationFormData.getEmail())
				.fillOutRegistrationFormWith(registrationFormData);
		assertTrue(regHelper.verifyWithTitle(myAccountPageTitle));
	}

}
