package com.automationpractice.tests;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.LinkedHashSet;

import com.automationpractice.model.LigalCredentials;
import com.automationpractice.model.PDP;
import com.automationpractice.model.Products;

public class TestDataObjectGenerator extends TestDataObjectGeneratorHelper {

	// Credentials
	protected static LinkedHashSet<LigalCredentials> readLigalCredentialsList()
			throws InterruptedException, IOException {
		String accountListFile = "src/test/resources/validAccounts.csv";
		LinkedHashSet<LigalCredentials> set = new LinkedHashSet<LigalCredentials>();
		String line;
		try (
				// open an input stream
				InputStream fileInputStream = new FileInputStream(accountListFile);
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
				String token = objects[3];
				// create a model object (new product item with real email, name and password)
				set.add(new LigalCredentials().withEmail(email).withPassword(password).withAccountName(name)
						.withToken(token));

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
				set.add(new Products().withId(id).withQuantity(qty).withProductName(name));
			}
		}
		return set;

	}

	// PDP
	protected static LinkedHashSet<PDP> generatePDP() throws InterruptedException, IOException {
		String productListFile = "src/test/resources/validProducts.csv";
		LinkedHashSet<PDP> set3 = new LinkedHashSet<PDP>();
		String accountListFile = "src/test/resources/validAccounts.csv";
		String line;
		String line2;
		try (
				// open an input stream
				InputStream fileInputStream = new FileInputStream(productListFile);
				// read file as UTF-8
				InputStreamReader reader = new InputStreamReader(fileInputStream, Charset.forName("UTF-8"));
				// open a BufferedReader to read line-by-line
				BufferedReader br = new BufferedReader(reader);
				// open an input stream
				InputStream fileInputStream2 = new FileInputStream(accountListFile);
				// read file as UTF-8
				InputStreamReader reader2 = new InputStreamReader(fileInputStream2, Charset.forName("UTF-8"));
				// open a BufferedReader to read line-by-line
				BufferedReader br2 = new BufferedReader(reader2);) {
			while ((line = br.readLine()) != null && (line2 = br2.readLine()) != null) {
				// split objects by ","
				String[] objects = line.split(",");
				String[] objects2 = line2.split(",");
				int id = Integer.parseInt(objects[0]);
				int qty = Integer.parseInt(objects[1]);
				String productName = objects[2].toLowerCase().replaceAll("_", " ");
				String token = objects2[3].toLowerCase();
				// create a model object (new product item with real ID nad QTY)
				set3.add(new PDP().withId(id).withQuantity(qty).withProductName(productName).withToken(token));
			}
		}
		return set3;

	}

}
