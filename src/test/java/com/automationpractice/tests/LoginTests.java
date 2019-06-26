package com.automationpractice.tests;

import static org.testng.Assert.assertTrue;

import java.io.IOException;
import java.net.URISyntaxException;

import org.testng.annotations.Test;

import com.automationpractice.appmanager.HttpLoginSession;
import com.automationpractice.datagenerators.TestDataProviders;
import com.automationpractice.model.LigalCredentials;

//@Guice
public class LoginTests extends TestBase {

	@Test(groups = { "API",
			"LOGIN" }, priority = 1, dataProvider = "getLigalCredentialsForAuthenticationControllerFromPropertyFile", dataProviderClass = TestDataProviders.class)
	public void testLoginWithExistedAccountUsingAPI(LigalCredentials credentials)
			throws IOException, URISyntaxException {
		HttpLoginSession session = APP.newLoginSession();
		String pageTitle = "My account - My Store";
		assertTrue(session.loginWith(credentials, pageTitle));
		assertTrue(session.isLoggedInAs(credentials));
	}

	@Test(groups = { "API", "LOGIN" }, priority = 4)
	public void testLoginWithEmptyEmailUsingAPI() throws IOException {
		HttpLoginSession session = APP.newLoginSession();
		String errMsg = "An email address required.";
		assertTrue(session.loginWithErrorHandling("", "", errMsg));
	}

	@Test(groups = { "API", "LOGIN" }, priority = 4)
	public void testLoginWithEmptyPasswordUsingAPI() throws IOException {
		HttpLoginSession session = APP.newLoginSession();
		String errMsg = "Password is required.";
		assertTrue(session.loginWithErrorHandling("whatever@gmail.com", "", errMsg));
	}

	@Test(groups = { "API",
			"LOGIN" }, priority = 2, dataProvider = "illegalCredentials", dataProviderClass = TestDataProviders.class)
	public void testLoginWithIlligalCredentialsUsingAPI(String email, String password) throws IOException {
		HttpLoginSession session = APP.newLoginSession();
		String errMsg = "Authentication failed.";
		assertTrue(session.loginWithErrorHandling(email, password, errMsg));
	}

	@Test(groups = { "API",
			"LOGIN" }, priority = 3, dataProvider = "invalidPasswordAndValidEmail", dataProviderClass = TestDataProviders.class)
	public void testLoginWithInvalidPasswordUsingAPI(String email, String password) throws IOException {
		HttpLoginSession session = APP.newLoginSession();
		String errMsg = "Invalid password.";
		assertTrue(session.loginWithErrorHandling(email, password, errMsg));
	}

	@Test(groups = { "API",
			"LOGIN" }, priority = 3, dataProvider = "invalidEmailAndValidPassword", dataProviderClass = TestDataProviders.class)
	public void testLoginWithInvalidEmailUsingAPI(String email, String password) throws IOException {
		HttpLoginSession session = APP.newLoginSession();
		String errMsg = "Invalid email address.";
		assertTrue(session.loginWithErrorHandling(email, password, errMsg));
	}
}
