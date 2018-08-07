package com.automationpractice.tests;

import org.testng.annotations.DataProvider;

public class TestDataProviders {

	// GUI tests Data Provider that reads params from an excel file
	@DataProvider(name = "invalidCredentials")
	// TODO implement data generator to generate illigal credentials here
	public static Object[][] generateIlligalCredentials() {
		return new Object[][] { { "volchanskij@gmail.com", "brbDbr" }, { "sqd123@gmail.com", "gz?}{:>gz21312" },
				{ "sdq2131@yandex.com", "thZZZZth" },
				{ "zsew@mail.ru", "tht!@#$%^&*()_+=-0987654321`h213" },
				{ "sdq2131@gmail.com", "thtAAAAAAAAAAAAAAAAAAAAAh" } };
	}

	@DataProvider(name = "invalidPassword")
	// TODO implement data generator to generate illigal credentials here
	public static Object[][] generateInvalidPassword() {
		return new Object[][] { { "volchanskij@gmail.com", "`" }, { "volck@gmail.com", "1z" },
				{ "karamba@gmail.com", "_0?A" }, { "set@gmail.com", "+__@" }, };
	}

	@DataProvider(name = "invalidEmail")
	// TODO implement data generator to generate illigal credentials here
	public static Object[][] generateInvalidEmail() {
		return new Object[][] { { "!@#$%^&*()(*&^%$#@!QWERTYUIOP{}:LKJHGFDSAZXCVBNM<>?com", "tht147896523+-*/h" },
			 { "~!@#$%^&*()_+@gmail.com", "t~!@#$%^&*()_hth" }, { "sd?}{:>q2131@gmail.com", "_0?A" },
				{ "~!@#$%^&*()_+@gmail.com", "+__@" }, };
	}

	// protected Object[][] getDataFromUiDataProvider() throws IOException {
//	Object[][] object = null;
//	TCreader file = new TCreader();

	// Read keyword sheet
//	if (isTestCase("TESTCASE.XLSX")) {
//	    if (isType("POSITIVE")) {
//		Sheet ivSheet = file.readExcel(System.getProperty("user.dir") + "//", "TestCase.xlsx", "Positive");
//		object = readExcel(ivSheet);
//	    } else if (isType("ERRORHANDLING")) {
//		Sheet ivSheet = file.readExcel(System.getProperty("user.dir") + "//", "TestCase.xlsx", "ErrorHandling");
//		object = readExcel(ivSheet);
//	    }
//	} else {
//	    //source file for testing and developing new features purposes
//	    Sheet ivSheet = file.readExcel(System.getProperty("user.dir") + "//", "test.xlsx", "ErrorHandling");
//	    object = readExcel(ivSheet);
//	}
//	return object;
//    }
//
//    private Object[][] readExcel(Sheet ivSheet) {
//	Object[][] object;
//	// Find number of rows in excel file
//	int rowCount = ivSheet.getLastRowNum() - ivSheet.getFirstRowNum();
//	object = new Object[rowCount][5];
//	for (int i = 0; i < rowCount; i++) {
//	    // Loop over all the rows
//	    Row row = ivSheet.getRow(i + 1);
//	    // Create a loop to print cell values in a row
//	    for (int j = 0; j < row.getLastCellNum(); j++) {
//		// Print excel data in console
//		object[i][j] = row.getCell(j).toString();
//	    }
//	}
//	return object;
//    }
//    //add source file for passing params
//    private boolean isTestCase(String testCase) {
//	return System.getProperty("testcase").toUpperCase().contains(testCase);
//    }
//    //choose a tab in the source excel file
//    private boolean isType(String tcType) {
//	return System.getProperty("tctype").toUpperCase().contains(tcType);
//    }

}