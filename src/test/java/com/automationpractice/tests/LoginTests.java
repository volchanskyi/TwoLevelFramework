package com.automationpractice.tests;

import static org.testng.Assert.assertTrue;

import java.io.IOException;

import org.testng.annotations.Test;

import com.automationpractice.appmanager.HttpSession;

public class LoginTests extends TestBase {

    @Test
    public void testLoginWithExistedAccount() throws IOException {
	HttpSession session = APP.newSession();
	assertTrue(session.loginWith("volchanskij@gmail.com", "testPWD001", "My account - My Store"));
	assertTrue(session.isLoggedInAs("Ivan Volchanskyi"));
    }

    @Test
    public void testLoginWithNoEmail() throws IOException {
	HttpSession session = APP.newSession();
	assertTrue(session.loginWithErrorHandling("", "", "An email address required."));
    }
    
    @Test
    public void testLoginWithNoPassword() throws IOException {
	HttpSession session = APP.newSession();
	assertTrue(session.loginWithErrorHandling("volchanskij@gmail.com", "", "Password is required."));
    }
    
    @Test
    public void testLoginWithIlligalPassword() throws IOException {
	HttpSession session = APP.newSession();
	assertTrue(session.loginWithErrorHandling("volchanskij@gmail.com", "brbrbr", "Authentication failed."));
    }

}
