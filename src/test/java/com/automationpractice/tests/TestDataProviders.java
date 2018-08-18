package com.automationpractice.tests;

import org.testng.annotations.DataProvider;

public class TestDataProviders extends TestDataGenerator {

	// **************INVALID/ILLIGAL TEST DATA********************//

	// GUI tests Data Provider that reads params from an excel file
	@DataProvider(name = "illigalCredentials")
	// TODO implement data generator to generate illigal credentials here
	public static Object[][] getIlligalCredentials() {
		return new Object[][] { generateValidFormatCredentials(), generateValidFormatCredentials(),
				generateValidFormatCredentials() };
	}

	@DataProvider(name = "invalidPassword")
	// TODO implement data generator to generate illigal credentials here
	public static Object[][] generateInvalidPassword() {
		return new Object[][] { { "volchanskij@gmail.com", generateInvalidFormatPasswords() },
				{ "volck@gmail.com", generateInvalidFormatPasswords() },
				{ "karamba@gmail.com", generateInvalidFormatPasswords() },
				{ "set@gmail.com", generateInvalidFormatPasswords() }, };
	}

	@DataProvider(name = "invalidEmail")
	// TODO implement data generator to generate illigal credentials here
	public static Object[][] generateInvalidEmail() {
		return new Object[][] { { generateInvalidFormatEmails(), "tht147896523+-*/h" },
				{ generateInvalidFormatEmails(), "t~!@#$%^&*()_hth" }, { generateInvalidFormatEmails(), "_0?A" },
				{ generateInvalidFormatEmails(), "+__@" }, };
	}

	// **************VALID TEST DATA********************//

	@DataProvider(name = "validProducts")
	// TODO implement data generator to generate illigal credentials here
	public static Object[] generateValidProduct() {
		return new Object[] { "blouse", "printed summer dress", "printed chiffon dress" };
	}

	// GUI tests Data Provider that reads params from an excel file
	@DataProvider(name = "validCredentials")
	// TODO implement data generator to generate illigal credentials here
	public static Object[][] generateligalCredentials() {
		int now = (int) System.currentTimeMillis();
		String email = "automationpractice_" + now + "@mailinator.com";
		return new Object[][] { { email, "Ivan", "lastName", "testPWD" + now, "178 Somewhere Dr.", "San Francisco",
				"94132", "California", "4158962578" } };
	}

	// protected Object[][] getDataFromUiDataProvider() throws IOException {
	// Object[][] object = null;
	// TCreader file = new TCreader();

	// Read keyword sheet
	// if (isTestCase("TESTCASE.XLSX")) {
	// if (isType("POSITIVE")) {
	// Sheet ivSheet = file.readExcel(System.getProperty("user.dir") + "//",
	// "TestCase.xlsx", "Positive");
	// object = readExcel(ivSheet);
	// } else if (isType("ERRORHANDLING")) {
	// Sheet ivSheet = file.readExcel(System.getProperty("user.dir") + "//",
	// "TestCase.xlsx", "ErrorHandling");
	// object = readExcel(ivSheet);
	// }
	// } else {
	// //source file for testing and developing new features purposes
	// Sheet ivSheet = file.readExcel(System.getProperty("user.dir") + "//",
	// "test.xlsx", "ErrorHandling");
	// object = readExcel(ivSheet);
	// }
	// return object;
	// }
	//
	// private Object[][] readExcel(Sheet ivSheet) {
	// Object[][] object;
	// // Find number of rows in excel file
	// int rowCount = ivSheet.getLastRowNum() - ivSheet.getFirstRowNum();
	// object = new Object[rowCount][5];
	// for (int i = 0; i < rowCount; i++) {
	// // Loop over all the rows
	// Row row = ivSheet.getRow(i + 1);
	// // Create a loop to print cell values in a row
	// for (int j = 0; j < row.getLastCellNum(); j++) {
	// // Print excel data in console
	// object[i][j] = row.getCell(j).toString();
	// }
	// }
	// return object;
	// }
	// //add source file for passing params
	// private boolean isTestCase(String testCase) {
	// return System.getProperty("testcase").toUpperCase().contains(testCase);
	// }
	// //choose a tab in the source excel file
	// private boolean isType(String tcType) {
	// return System.getProperty("tctype").toUpperCase().contains(tcType);
	// }

}