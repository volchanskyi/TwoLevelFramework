package com.automationpractice.tests;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

import org.apache.commons.lang3.RandomStringUtils;

public abstract class TestDataGenerator {

	  String ascii = RandomStringUtils.randomAscii(5, 20);
      String alnum = RandomStringUtils.randomAlphanumeric(20, 50);
      String num = RandomStringUtils.randomNumeric(10, 20);
      String graph = RandomStringUtils.randomGraph(10, 20);

    
    private static String generateAscii (int min, int max) {
    	return  RandomStringUtils.randomAscii(min, max).toString();
    	}

    private static String generateAlphaNumeric (int min, int max) {
    	return  RandomStringUtils.randomAlphanumeric(min, max).toString();
    }
		
    private static String generateEmails() {
    	return generateAlphaNumeric(5, 15) + "@gmail.com";
    		}

    private static String generatePasswords() {
    	return generateAlphaNumeric(5, 10);
    }
    

	public static String[] generateIlligalCredentials() {
		String [] invalidEmailAndPassword = {generateEmails(), generatePasswords()};
		return invalidEmailAndPassword;
	}	
    
}
