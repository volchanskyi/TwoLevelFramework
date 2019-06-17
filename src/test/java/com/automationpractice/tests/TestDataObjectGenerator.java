package com.automationpractice.tests;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.security.AccessControlException;
import java.util.HashSet;
import java.util.LinkedHashSet;

import com.automationpractice.model.LigalCredentials;
import com.automationpractice.model.Products;
import com.automationpractice.model.RegistrationFormData;

public class TestDataObjectGenerator extends TestDataObjectGeneratorHelper {

	// Credentials
	protected static HashSet<LigalCredentials> readLigalCredentialsList() {
		String accountListFile = "src/test/resources/validAccounts.csv";
		LinkedHashSet<LigalCredentials> set = new LinkedHashSet<LigalCredentials>();
		String line;
		// use a try-with-resource statement
		try (
				// Create a connection stream | Knows how to connect to the file
				InputStream fileInputStream = new FileInputStream(accountListFile);
				// Chaining to a connection stream | read object as UTF-8
				InputStreamReader reader = new InputStreamReader(fileInputStream, Charset.forName("UTF-8"));
				// open a BufferedReader to read stream line-by-line
				BufferedReader br = new BufferedReader(reader);) {
			while ((line = br.readLine()) != null) {
				// split objects by ","
				String[] objects = line.split(",");
				String email = objects[0];
				String password = objects[1];
				String name = objects[2].replaceAll("_", " ");
				String token = objects[3];
				// create a model object (new product item with real email, name and password)
				set.add(new LigalCredentials().withEmail(email).withPassword(password).withAccountName(name)
						.withToken(token));
			}
		} catch (FileNotFoundException e) {
			DATA_GEN_LOGGER.error(e.toString());
		} catch (AccessControlException e) {
			DATA_GEN_LOGGER.error(e.toString());
		} catch (IOException e) {
			DATA_GEN_LOGGER.error(e.toString());
		}
		return set;
	}

	// Products
	protected static HashSet<Products> readProductList() {
		String productListFile = "src/test/resources/validProducts.csv";
		LinkedHashSet<Products> set = new LinkedHashSet<Products>();
		String line;
		// use a try-with-resource statement
		try (
				// Create a connection stream | Knows how to connect to the file
				InputStream fileInputStream = new FileInputStream(productListFile);
				// Chaining to a connection stream | read object as UTF-8
				InputStreamReader reader = new InputStreamReader(fileInputStream, Charset.forName("UTF-8"));
				// open a BufferedReader to read stream line-by-line
				BufferedReader br = new BufferedReader(reader);) {
			while ((line = br.readLine()) != null) {
				// split objects by ","
				String[] objects = line.split(",");
				int id = Integer.parseInt(objects[0]);
				int qty = Integer.parseInt(objects[1]);
				String name = objects[2].toLowerCase().replaceAll("_", " ");
				// create a model object (new product item with real ID nad QTY)
				set.add(new Products().withId(id).withQuantity(qty).withProductName(name));
			}
		} catch (FileNotFoundException e) {
			DATA_GEN_LOGGER.error(e.toString());
		} catch (AccessControlException e) {
			DATA_GEN_LOGGER.error(e.toString());
		} catch (IOException e) {
			DATA_GEN_LOGGER.error(e.toString());
		}
		return set;
	}

	// Registration Form Data
	protected static HashSet<RegistrationFormData> generateRegistrationFormData() {
		LinkedHashSet<RegistrationFormData> set = new LinkedHashSet<RegistrationFormData>();
		// temp counter == 1 (fix the test first)
		for (int i = 1; i > 0; --i) {
			// update zip, city and state names.
			String[] locationData = getLocationData();
			set.add(new RegistrationFormData().withEmail(generateValidFormatEmails())
					.withFirstName(generateValidFormatName()).withLastName(generateValidFormatLastName())
					.withPassword(generateValidFormatPasswords()).withAddress(generateValidFormatAddress())
					.withCityName(getLocationData()[1]).withPostCode(locationData[0]).withState(getLocationData()[2])
					.withPhoneNumber(generateValidFormatPhoneNumber()));
		}
		return set;
	}

}
