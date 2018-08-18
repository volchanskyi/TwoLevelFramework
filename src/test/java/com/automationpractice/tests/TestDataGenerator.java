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

	// Emails
	private static String generateValidFormatEmails() {
		return generateAlphaNumeric(5, 15) + "@mailinator.com";
	}

	public static String generateInvalidFormatEmails() {
		return generateAscii(5, 30) + "@" + generateAscii(3, 10) + ".com";
	}

	// Passwords
	private static String generateValidFormatPasswords() {
		return generateAlphaNumeric(5, 10);
	}

	public static String generateInvalidFormatPasswords() {
		return generateAscii(1, 4).replace("\\", ":");
	}

	// Credentials
	public static String[] generateValidFormatCredentials() {
		String[] validFormatEmailAndPassword = { generateValidFormatEmails(), generateValidFormatPasswords() };
		return validFormatEmailAndPassword;
	}


}
