package com.automationpractice.tests;

import static org.testng.Assert.assertTrue;

import java.io.IOException;
import java.net.URISyntaxException;

import org.testng.annotations.Test;

import com.automationpractice.appmanager.HttpSession;
import com.automationpractice.model.LigalCredentials;

//@Guice
public class LoginTests extends TestBase {

	@Test(dataProvider = "getLigalCredentialsForAuthenticationControllerFromPropertyFile", dataProviderClass = TestDataProviders.class)
	public void testLoginWithExistedAccountUsingAPI(LigalCredentials credentials)
			throws IOException, URISyntaxException {
		HttpSession session = APP.newSession();
		String pageTitle = "My account - My Store";
		assertTrue(session.loginWith(credentials, pageTitle));
		assertTrue(session.isLoggedInAs(credentials));
	}

	@Test
	public void testLoginWithEmptyEmailUsingAPI() throws IOException {
		HttpSession session = APP.newSession();
		String errMsg = "An email address required.";
		assertTrue(session.loginWithErrorHandling("", "", errMsg));
	}

	@Test
	public void testLoginWithEmptyPasswordUsingAPI() throws IOException {
		HttpSession session = APP.newSession();
		String errMsg = "Password is required.";
		assertTrue(session.loginWithErrorHandling("whatever@gmail.com", "", errMsg));
	}

	@Test(dataProvider = "illegalCredentials", dataProviderClass = TestDataProviders.class)
	public void testLoginWithIlligalCredentialsUsingAPI(String email, String password) throws IOException {
		HttpSession session = APP.newSession();
		String errMsg = "Authentication failed.";
		assertTrue(session.loginWithErrorHandling(email, password, errMsg));
	}

	@Test(dataProvider = "invalidPasswordAndValidEmail", dataProviderClass = TestDataProviders.class)
	public void testLoginWithInvalidPasswordUsingAPI(String email, String password) throws IOException {
		HttpSession session = APP.newSession();
		String errMsg = "Invalid password.";
		assertTrue(session.loginWithErrorHandling(email, password, errMsg));
	}

	@Test(dataProvider = "invalidEmailAndValidPassword", dataProviderClass = TestDataProviders.class)
	public void testLoginWithInvalidEmailUsingAPI(String email, String password) throws IOException {
		HttpSession session = APP.newSession();
		String errMsg = "Invalid email address.";
		assertTrue(session.loginWithErrorHandling(email, password, errMsg));
	}
}
