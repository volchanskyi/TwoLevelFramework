package com.automationpractice.tests;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.http.client.fluent.Form;
import org.apache.http.client.fluent.Request;
import org.openqa.selenium.json.JsonException;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.*;

import com.automationpractice.model.LocationData;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

public class TestDataObjectGeneratorHelper {

	protected static final Logger DATA_GEN_LOGGER = LoggerFactory.getLogger(TestDataObjectGenerator.class);

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
		return generateAlphaNumeric(5, 15) + getRandomValidEmailDomain();
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

	// Email domains
	protected static String getRandomValidEmailDomain() {
		String[] emailDomains = { "@guerrillamailblock.com" };
		Random randomEmailDomain = new Random();
		int randomSelection = randomEmailDomain.nextInt(emailDomains.length);
		return emailDomains[randomSelection];
	}

	// Location Data Helper
	protected static String[] getLocationData() {
		try {
			InputStream xml = Request.Post("http://production.shippingapis.com/ShippingAPITest.dll?API=CityStateLookup")
					.addHeader("accept", "text/html,application/xhtml+xml,application/xml;q=0.9")
					.bodyForm(Form.form().add("API", "CityStateLookup")
							.add("XML", "<CityStateLookupRequest USERID=\"559REMOT6381\"><ZipCode ID=\"0\"><Zip5>"
									+ generateValidFormatPostalCode() + "</Zip5></ZipCode></CityStateLookupRequest>")
							.build())
					.execute().returnContent().asStream();
			Serializer serializer = new Persister();
			LocationData parsedXml = serializer.read(LocationData.class, xml);
			String zip = parsedXml.getZip();
			String city = parsedXml.getCity();
			String state = parsedXml.getState();
			String[] locationData = { zip, city, state };
			return locationData;
		} catch (IOException e) {
			DATA_GEN_LOGGER.error(e.toString());
		} catch (Exception e) {
			DATA_GEN_LOGGER.error(e.toString());
		}
		return null;
	}

}
