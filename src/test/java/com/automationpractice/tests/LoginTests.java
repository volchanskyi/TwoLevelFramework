package com.automationpractice.tests;


import static org.testng.Assert.assertTrue;

import java.io.IOException;

import org.testng.annotations.Test;

import com.automationpractice.appmanager.HttpSession;

public class LoginTests extends TestBase {
    
    @Test
    public void testLoginWithHTTP() throws IOException {
	HttpSession session = APP.newSession();
	assertTrue(session.loginWithHTTP("volchanskij@gmail.com", "testPWD001", "My account - My Store"));
	assertTrue(session.isLoggedInAs("Ivan Volchanskyi"));
    }
    
    
}
