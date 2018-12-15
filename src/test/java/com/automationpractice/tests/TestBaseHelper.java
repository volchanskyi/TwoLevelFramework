package com.automationpractice.tests;

public class TestBaseHelper {

	protected boolean isDebugEnabled() {
		try {
			if (System.getProperty("debug") == null || System.getProperty("debug").isEmpty()
					|| System.getProperty("debug").contains("disabled")) {
				return false;
			} else if (System.getProperty("debug").equalsIgnoreCase("enabled"))
				return true;
			else
				return false;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	
	}

}
