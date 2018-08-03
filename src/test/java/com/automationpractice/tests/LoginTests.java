package com.automationpractice.tests;

import static org.testng.Assert.assertTrue;

import java.io.IOException;

import org.testng.annotations.Test;

import com.automationpractice.appmanager.HttpSession;

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
    
    @Test
    public void testLoginWithIlligalPasswordUsingAPI() throws IOException {
	HttpSession session = APP.newSession();
	assertTrue(session.loginWithErrorHandling("volchanskij@gmail.com", "brbrbr", "Authentication failed."));
    }

}
