package com.automationpractice.tests;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.LinkedHashSet;
import java.util.Random;

import org.apache.commons.lang3.RandomStringUtils;

import com.automationpractice.model.LigalCredentials;
import com.automationpractice.model.Products;

public abstract class TestDataGenerator {

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
		return generateAlphaNumeric(5, 15) + "@mailinator.com";
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

	protected static LinkedHashSet<LigalCredentials> readLigalCredentialsList() throws InterruptedException, IOException {
		String productListFile = "src/test/resources/validAccounts.csv";
		LinkedHashSet<LigalCredentials> set = new LinkedHashSet<LigalCredentials>();
		String line;
		try (
				// open an input stream
				InputStream fileInputStream = new FileInputStream(productListFile);
				// read file as UTF-8
				InputStreamReader reader = new InputStreamReader(fileInputStream, Charset.forName("UTF-8"));
				// open a BufferedReader to read line-by-line
				BufferedReader br = new BufferedReader(reader);) {
			while ((line = br.readLine()) != null) {
				// split objects by ","
				String[] objects = line.split(",");
				String email = objects[0];
				String password = objects[1];
				String name = objects[2].replaceAll("_", " ");
				// create a model object (new product item with real ID nad QTY)
				set.add(new LigalCredentials().withEmail(email).withPassword(password).withName(name));

			}
		}
		return set;

	}

	// Products
	protected static LinkedHashSet<Products> readProductList() throws InterruptedException, IOException {
		String productListFile = "src/test/resources/validProducts.csv";
		LinkedHashSet<Products> set = new LinkedHashSet<Products>();
		String line;
		try (
				// open an input stream
				InputStream fileInputStream = new FileInputStream(productListFile);
				// read file as UTF-8
				InputStreamReader reader = new InputStreamReader(fileInputStream, Charset.forName("UTF-8"));
				// open a BufferedReader to read line-by-line
				BufferedReader br = new BufferedReader(reader);) {
			while ((line = br.readLine()) != null) {
				// split objects by ","
				String[] objects = line.split(",");
				int id = Integer.parseInt(objects[0]);
				int qty = Integer.parseInt(objects[1]);
				String name = objects[2].toLowerCase().replaceAll("_", " ");
				// create a model object (new product item with real ID nad QTY)
				set.add(new Products().withId(id).withQuantity(qty).withName(name));
			}
		}
		return set;

	}

}
