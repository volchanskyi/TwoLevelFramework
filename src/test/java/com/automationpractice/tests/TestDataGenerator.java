package com.automationpractice.tests;

import org.apache.commons.lang3.RandomStringUtils;

public abstract class TestDataGenerator {

//	  String ascii = RandomStringUtils.randomAscii(5, 20);
//      String alnum = RandomStringUtils.randomAlphanumeric(20, 50);
//      String num = RandomStringUtils.randomNumeric(10, 20);
//      String graph = RandomStringUtils.randomGraph(10, 20);

	// String generators
	private static String generateAscii(int min, int max) {
		return RandomStringUtils.randomAscii(min, max).toString();
	}

	private static String generateAlphaNumeric(int min, int max) {
		return RandomStringUtils.randomAlphanumeric(min, max).toString();
	}
	
	private static String generateNumeric(int min, int max) {
		return RandomStringUtils.randomNumeric(min, max).toString();
	}
	
	private static String generateAlphabetic(int min, int max) {
		return RandomStringUtils.randomAlphabetic(min, max).toString();
	}

	//Name
	protected static String generateValidFormatName() {
		return generateAlphabetic(4, 10);
	}
	
	//Last Name
	protected static String generateValidFormatLastName() {
		return generateAlphabetic(5, 12);
	}
	
	//Address
	protected static String generateValidFormatAddress() {
		return generateNumeric(1, 4) + " " + generateAlphabetic(5, 10) + " Dr.";
	}
	
	//PostalCode
	protected static String generateValidFormatPostalCode() {
		return generateNumeric(5, 5);
	}
	
	//PhoneNumber
	protected static String generateValidFormatPhoneNumber() {
		return generateNumeric(10, 10);
	}
	
	// Emails
	protected static String generateValidFormatEmails() {
		return generateAlphaNumeric(5, 15) + "@mailinator.com";
	}

	protected static String generateInvalidFormatEmails() {
		return generateAscii(5, 30) + "@" + generateAscii(3, 10) + ".com";
	}

	// Passwords
	protected static String generateValidFormatPasswords() {
		return generateAlphaNumeric(5, 10);
	}

	protected static String generateInvalidFormatPasswords() {
		return generateAscii(1, 4);
	}

	// Credentials
	protected static String[] generateValidFormatCredentials() {
		String[] validFormatEmailAndPassword = { generateValidFormatEmails(), generateValidFormatPasswords() };
		return validFormatEmailAndPassword;
	}


}
