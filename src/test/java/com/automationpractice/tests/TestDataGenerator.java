package com.automationpractice.tests;

import java.util.Random;

import org.apache.commons.lang3.RandomStringUtils;

public abstract class TestDataGenerator extends TestDataObjectGenerator {

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

	private static String generateNegativeNumeric() {
		Double rand = new Random().nextDouble() - 3;
		return rand.toString();
	}

	private static String generateAlphabetic(int min, int max) {
		return RandomStringUtils.randomAlphabetic(min, max).toString();
	}

	// Name
	protected static String generateValidFormatName() {
		return generateAlphabetic(4, 10);
	}

	// Last Name
	protected static String generateValidFormatLastName() {
		return generateAlphabetic(5, 12);
	}

	// Address
	protected static String generateValidFormatAddress() {
		return generateNumeric(1, 4) + " " + generateAlphabetic(5, 10) + " Dr.";
	}

	// PostalCode
	protected static String generateValidFormatPostalCode() {
		return generateNumeric(5, 5);
	}

	// PhoneNumber
	protected static String generateValidFormatPhoneNumber() {
		return generateNumeric(10, 10);
	}

	// Emails
	protected static String generateValidFormatEmails() {
		return generateAlphaNumeric(5, 15) + "@guerrillamailblock.com";
	}

	protected static String generateInvalidFormatEmails() {
		return generateAscii(5, 230) + generateNegativeNumeric() + "@" + generateAscii(3, 10) + ".com";
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

	protected static String[] generateValidFormatCredentialsForRegistrationPage() {
		String[] validFormatCredentialsForRegistrationPage = { generateValidFormatEmails(), generateValidFormatName(),
				generateValidFormatLastName(), generateValidFormatPasswords(), generateValidFormatAddress(),
				"San Francisco", generateValidFormatPostalCode(), "California", generateValidFormatPhoneNumber() };
		return validFormatCredentialsForRegistrationPage;
	}

	protected static String[] generateValidFormatCredentialsForRegistrationController() {
		String[] validFormatCredentialsForRegistrationController = { generateValidFormatEmails(),
				generateValidFormatName(), generateValidFormatLastName(), generateValidFormatPasswords(),
				generateValidFormatAddress(), "San Francisco", generateValidFormatPostalCode(), "5",
				generateValidFormatPhoneNumber() };
		return validFormatCredentialsForRegistrationController;
	}

}