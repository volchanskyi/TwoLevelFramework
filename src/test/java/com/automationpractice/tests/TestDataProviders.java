package com.automationpractice.tests;

import org.testng.annotations.DataProvider;

public class TestDataProviders extends TestDataGenerator {

	// **************INVALID/ILLIGAL TEST DATA********************//


	@DataProvider(name = "illigalCredentials")
	public static Object[][] getIlligalCredentials() {
		Object[][] generatedTestData = new Object[20][2];
		// loop over 2D array
		for (int row = 0; row < generatedTestData.length; row++) {
			generatedTestData[row] = generateValidFormatCredentials();
		}
		return generatedTestData;
	}

	@DataProvider(name = "invalidPassword")
	public static Object[][] generateInvalidPassword() {
		Object[][] generatedTestData = new Object[20][2];
		// loop over 2D array
		for (int row = 0; row < generatedTestData.length; row++) {
			generatedTestData[row][0] = generateValidFormatEmails();
			generatedTestData[row][1] = generateInvalidFormatPasswords();
		}
		return generatedTestData;
	}

	@DataProvider(name = "invalidEmail")
	public static Object[][] generateInvalidEmail() {
		Object[][] generatedTestData = new Object[20][2];
		// loop over 2D array
		for (int row = 0; row < generatedTestData.length; row++) {
			generatedTestData[row][0] = generateInvalidFormatEmails();
			generatedTestData[row][1] = generateValidFormatPasswords();
		}
		return generatedTestData;
	}

	// **************VALID TEST DATA********************//

	@DataProvider(name = "validProducts")
	public static Object[] generateValidProduct() {
		return new Object[] { "blouse", "printed summer dress", "printed chiffon dress" };
	}

	@DataProvider(name = "validCredentialsForRegistrationPage")
	public static Object[][] generateligalCredentialsForRegistrationPage() {
		Object[][] generatedTestData = new Object[1][9];
		// loop over 2D array
		for (int row = 0; row < generatedTestData.length; row++) {
			generatedTestData[row][0] = generateValidFormatEmails();
			generatedTestData[row][1] = generateValidFormatName();
			generatedTestData[row][2] = generateValidFormatLastName();
			generatedTestData[row][3] = generateValidFormatPasswords();
			generatedTestData[row][4] = generateValidFormatAddress();
			generatedTestData[row][5] = "San Francisco";
			generatedTestData[row][6] = generateValidFormatPostalCode();
			generatedTestData[row][7] = "California";
			generatedTestData[row][8] = generateValidFormatPhoneNumber();
		}
		return generatedTestData;
	}
	
	@DataProvider(name = "validCredentialsForRegistrationController")
	public static Object[][] generateLigalCredentialsForController() {
		Object[][] generatedTestData = new Object[10][9];
		// loop over 2D array
		for (int row = 0; row < generatedTestData.length; row++) {
			generatedTestData[row][0] = generateValidFormatEmails();
			generatedTestData[row][1] = generateValidFormatName();
			generatedTestData[row][2] = generateValidFormatLastName();
			generatedTestData[row][3] = generateValidFormatPasswords();
			generatedTestData[row][4] = generateValidFormatAddress();
			generatedTestData[row][5] = "San Francisco";
			generatedTestData[row][6] = generateValidFormatPostalCode();
			generatedTestData[row][7] = "5";
			generatedTestData[row][8] = generateValidFormatPhoneNumber();
		}
		return generatedTestData;
	}
}