package com.automationpractice.tests;

import org.testng.annotations.Test;

public class RegistrationTests extends TestBase {
    
    @Test
    public void testRegistration() {
	APP.registration().start("user1", "user@localhost.localdomain");
    }
    
}
