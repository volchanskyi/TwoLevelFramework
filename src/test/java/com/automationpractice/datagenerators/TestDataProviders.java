package com.automationpractice.datagenerators;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.testng.annotations.DataProvider;

import com.automationpractice.model.LigalCredentials;
import com.automationpractice.model.Products;
import com.automationpractice.model.RegistrationFormData;

public class TestDataProviders {

	//init TDMG
	TestDataModelGenerator tdmg = new TestDataModelGenerator();
	
	//init TDG
	TestDataGenerator tdg = new TestDataGenerator();
	
	// **************INVALID/ILLEGAL TEST DATA********************//

	@DataProvider(name = "illegalCredentials")
	protected String[][] getIlligalCredentials() {
		String[][] generatedTestData = new String[20][2];
		// loop over 2D array
		for (int row = 0; row < generatedTestData.length; row++) {
			generatedTestData[row] = tdg.generateValidFormatCredentials();
		}
		return generatedTestData;
	}

	@DataProvider(name = "invalidEmail")
	protected String[] generateInvalidEmail() {
		String[] generatedTestData = new String[40];
		// loop over 2D array
		for (int row = 0; row < generatedTestData.length; row++) {
			generatedTestData[row] = tdg.generateInvalidFormatEmails();
		}
		return generatedTestData;
	}

	@DataProvider(name = "invalidPasswordAndValidEmail")
	protected String[][] generateInvalidPasswordAndValidEmail() {
		String[][] generatedTestData = new String[20][2];
		// loop over 2D array
		for (int row = 0; row < generatedTestData.length; row++) {
			generatedTestData[row][0] = tdg.generateValidFormatEmails();
			generatedTestData[row][1] = tdg.generateInvalidFormatPasswords();
		}
		return generatedTestData;
	}

	@DataProvider(name = "invalidEmailAndValidPassword")
	protected String[][] generateInvalidEmailAndValidPassword() {
		String[][] generatedTestData = new String[20][2];
		// loop over 2D array
		for (int row = 0; row < generatedTestData.length; row++) {
			generatedTestData[row][0] = tdg.generateInvalidFormatEmails();
			generatedTestData[row][1] = tdg.generateValidFormatPasswords();
		}
		return generatedTestData;
	}

	// **************VALID/LEGAL TEST DATA********************//

	@DataProvider(name = "validCredentialsForRegistrationController")
	protected Iterator<RegistrationFormData> generateRegistrationFormDataForRegistratonPage()
			throws InterruptedException, IOException {
		return tdmg.generateRegistrationFormData().iterator();
	}

	@DataProvider(name = "getValidProductsFromPropertyFile")
	protected Iterator<Products> generateValidProducts() throws InterruptedException, IOException {
		return tdmg.readProductList().iterator();
	}

	@DataProvider(name = "getLigalCredentialsForAuthenticationControllerFromPropertyFile")
	protected Iterator<LigalCredentials> generateValidCredentialsForController()
			throws InterruptedException, IOException {
		return tdmg.readLigalCredentialsList().iterator();
	}

	@DataProvider(name = "getLigalCredentialsAndProductIdsForPdpControllerFromPropertyFiles")
	protected Object[][] generatePdpDataForWishListController() throws InterruptedException, IOException {
		// Create a dynamic 2D array with the size of the validCredentials
		// entries(accounts)
		Object[][] generatedTestData = new Object[tdmg.readLigalCredentialsList().size()][2];
		List<LigalCredentials> ligalCredentialsList = new ArrayList<LigalCredentials>();
		List<Products> productList = new ArrayList<Products>();
		// convert sets to lists
		ligalCredentialsList.addAll(tdmg.readLigalCredentialsList());
		productList.addAll(tdmg.readProductList());
		// fill out the array with the lists values (sets values)
		for (int i = 0; i < ligalCredentialsList.size(); i++) {
			generatedTestData[i][0] = ligalCredentialsList.get(i);
			generatedTestData[i][1] = productList.get(i);
		}
		return generatedTestData;
	}
}