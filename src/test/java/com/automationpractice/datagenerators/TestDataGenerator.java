package com.automationpractice.datagenerators;

import java.util.Random;

import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestDataGenerator {

	protected static final Logger DATA_GEN_LOGGER = LoggerFactory.getLogger(TestDataGenerator.class);

	// String generators
	private String generateAscii(int min, int max) {
		return RandomStringUtils.randomAscii(min, max).toString();
	}

	private String generateAlphaNumeric(int min, int max) {
		return RandomStringUtils.randomAlphanumeric(min, max).toString();
	}

	private String generateNumeric(int min, int max) {
		return RandomStringUtils.randomNumeric(min, max).toString();
	}

	private String generateNegativeNumeric() {
		Double rand = new Random().nextDouble() - 3;
		return rand.toString();
	}

	private String generateAlphabetic(int min, int max) {
		return RandomStringUtils.randomAlphabetic(min, max).toString();
	}

	// Name
	protected String generateValidFormatName() {
		return generateAlphabetic(4, 10);
	}

	// Last Name
	protected String generateValidFormatLastName() {
		return generateAlphabetic(5, 12);
	}

	// Address
	protected String generateValidFormatAddress() {
		return generateNumeric(1, 4) + " " + generateAlphabetic(5, 10) + " Dr.";
	}

	// PhoneNumber
	protected String generateValidFormatPhoneNumber() {
		return generateNumeric(10, 10);
	}

	// Emails
	protected String generateValidFormatEmails() {
		return generateAlphaNumeric(5, 15) + getRandomValidEmailDomain();
	}

	protected String generateInvalidFormatEmails() {
		return generateAscii(5, 230) + generateNegativeNumeric() + "@" + generateAscii(3, 10) + ".com";
	}

	// Passwords
	protected String generateValidFormatPasswords() {
		return generateAlphaNumeric(5, 10);
	}

	protected String generateInvalidFormatPasswords() {
		return generateAscii(1, 4);
	}

	// Credentials
	protected String[] generateValidFormatCredentials() {
		String[] validFormatEmailAndPassword = { generateValidFormatEmails(), generateValidFormatPasswords() };
		return validFormatEmailAndPassword;
	}

	// Email domains
	protected String getRandomValidEmailDomain() {
		String[] emailDomains = { "@guerrillamailblock.com" };
		Random randomEmailDomain = new Random();
		int randomSelection = randomEmailDomain.nextInt(emailDomains.length);
		return emailDomains[randomSelection];
	}

}
