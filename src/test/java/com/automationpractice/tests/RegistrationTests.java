package com.automationpractice.tests;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Arrays;

import javax.mail.MessagingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.automationpractice.appmanager.HttpSession;
import com.automationpractice.appmanager.RegistrationHelper;

public class RegistrationTests extends TestBase {

	// final private Logger logger = LoggerFactory.getLogger(TestBase.class);
	//
	// @BeforeMethod
	// private void beforeMethod(Method method, Object[] parameters) {
	// logger.debug("Start test " + method.getName() + " with params " +
	// Arrays.asList(parameters));
	//
	// }

	@Test(groups = { "API" })
	public void testRegisterNewAccountUsingAPI() throws MessagingException, IOException, InterruptedException {
		int now = (int) System.currentTimeMillis();
		String email = "automationpractice_" + now;
		String password = "testPWD" + now;
		// String link = "https://blablabla"; (not implemented yet) DO NOT DELETE
		HttpSession session = APP.newSession();
		assertTrue(session.createEmail(email));
		assertTrue(session.signUp(email + "@mailinator.com"));
		// assertTrue(session.verifyActivationLink(link)); (automationpractice.com
		// doesn`t send an activation link) DO NOT DELETE
		assertTrue(session.register(email + "@mailinator.com", "My account - My Store", "Ivan", "lastName", password,
				"178 Somewhere Dr.", "San Francisco", "94132", "5", "4158962578"));
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

	@Test(groups = { "GUI" }, dataProvider = "validCredentials", dataProviderClass = TestDataProviders.class)
	public void testRegisterNewAccountUsingGUI(String email, String fName, String lName, String password,
			String address, String city, String postalCode, String state, String phone)
			throws MessagingException, IOException, InterruptedException {

		// String password = "testPWD" + now;
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
		// "My account - My Store"
	}
	//
	// @AfterMethod(alwaysRun = true)
	// private void logTestStop(Method method, Object[] parameters) {
	// logger.debug("Stop test " + method.getName());
	//
	// }

}
