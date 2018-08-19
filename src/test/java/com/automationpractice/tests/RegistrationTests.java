package com.automationpractice.tests;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.io.IOException;

import javax.mail.MessagingException;

import org.testng.annotations.Test;

import com.automationpractice.appmanager.HttpSession;
import com.automationpractice.appmanager.RegistrationHelper;

public class RegistrationTests extends TestBase {

	@Test(groups = { "API" }, dataProvider = "validCredentialsForRegistrationController", dataProviderClass = TestDataProviders.class)
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
		assertTrue(session.register(fName, lName, password, address, city, postalCode, state, phone, title, newEmail));
	}

	@Test(groups = { "API" })
	public void testRegisterNewAccountWithExistedCredentialsUsingAPI() throws IOException {
		HttpSession session = APP.newSession();
		String apiMsg = session.registerExistedAccountWithAPI("volchanskij@gmail.com");
		assertEquals(apiMsg,
				"An account using this email address has already been registered. Please enter a valid password or request a new one. ");
	}

	@Test(groups = { "API" })
	public void testRegisterNewAccountWithWrongEmailFormatUsingAPI() throws IOException {
		HttpSession session = APP.newSession();
		String apiMsg = session.registerExistedAccountWithAPI("volchanskij@");
		assertEquals(apiMsg, "Invalid email address.");
	}

	@Test(groups = { "GUI" }, dataProvider = "validCredentialsForRegistrationPage", dataProviderClass = TestDataProviders.class)
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
