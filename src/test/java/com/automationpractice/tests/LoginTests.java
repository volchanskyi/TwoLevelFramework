package com.automationpractice.tests;

import static org.testng.Assert.assertTrue;

import java.io.IOException;

import org.testng.annotations.Guice;
import org.testng.annotations.Test;

import com.automationpractice.appmanager.HttpSession;

//@Guice
public class LoginTests extends TestBase {

	@Test
	public void testLoginWithExistedAccountUsingAPI() throws IOException {
		HttpSession session = APP.newSession();
		assertTrue(session.loginWith("volchanskij@gmail.com", "testPWD001", "My account - My Store"));
		assertTrue(session.isLoggedInAs("Ivan Volchanskyi"));
	}

	@Test
	public void testLoginWithEmptyEmailUsingAPI() throws IOException {
		HttpSession session = APP.newSession();
		assertTrue(session.loginWithErrorHandling("", "", "An email address required."));
	}

	@Test
	public void testLoginWithEmptyPasswordUsingAPI() throws IOException {
		HttpSession session = APP.newSession();
		assertTrue(session.loginWithErrorHandling("volchanskij@gmail.com", "", "Password is required."));
	}

	@Test(dataProvider = "invalidCredentials", dataProviderClass = TestDataProviders.class)
	public void testLoginWithIlligalCredentialsUsingAPI(String email, String password) throws IOException {
		HttpSession session = APP.newSession();
		String errMsg = "Authentication failed.";
		assertTrue(session.loginWithErrorHandling(email, password, errMsg));
	}

	@Test(dataProvider = "invalidPassword", dataProviderClass = TestDataProviders.class)
	public void testLoginWithInvalidPasswordUsingAPI(String email, String password) throws IOException {
		HttpSession session = APP.newSession();
		String errMsg = "Invalid password.";
		assertTrue(session.loginWithErrorHandling(email, password, errMsg));
	}
	
	@Test(dataProvider = "invalidEmail", dataProviderClass = TestDataProviders.class)
	public void testLoginWithInvalidEmailUsingAPI(String email, String password) throws IOException {
		HttpSession session = APP.newSession();
		String errMsg = "Invalid email address.";
		assertTrue(session.loginWithErrorHandling(email, password, errMsg));
	}

}
