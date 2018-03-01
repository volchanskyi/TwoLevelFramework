package io.mantishub.ivan.tests;


import static org.testng.Assert.assertTrue;

import java.io.IOException;

import org.testng.annotations.Test;

import io.mantishub.ivan.appmanager.HttpSession;

public class LoginTests extends TestBase {
    
    @Test
    public void testLogin() throws IOException {
	HttpSession session = APP.newSession();
	assertTrue(session.login("volchanskij@gmail.com", "activation_51186"));
	assertTrue(session.isLoggedInAs("ivan"));
    }
}
