package com.automationpractice.appmanager;

import org.openqa.selenium.By;

public class RegistrationHelper extends HelperBase {

    public RegistrationHelper(ApplicationManager app) {
	super(app);

    }

    public void start(String fName, String lName, String password, String address, String city, String postcode,
	    String state, String phone) {
	wd.get(app.getProperty("web.baseUrl") + "index.php?controller=authentication&back=my-account#account-creation");
	type(By.name("customer_firstname"), fName);
	type(By.name("customer_lastname"), lName);
	type(By.cssSelector("#passwd"), password);
	type(By.cssSelector("firstname"), fName);
	type(By.cssSelector("lastname"), lName);
	type(By.cssSelector("address1"), address);
	type(By.cssSelector("city"), city);
	type(By.cssSelector("postcode"), postcode);
	type(By.cssSelector("id_state"), state);
	type(By.cssSelector("phone_mobile"), phone);
	type(By.cssSelector("alias"), address);
	click(By.cssSelector("submitAccount"));

    }

    //TODO Method is not finished!
    public void finish(String confirmationLink, String password) {
	wd.get(confirmationLink);
	type(By.name("password"), password);
	type(By.name("password_confirm"), password);
	click(By.xpath("//*[@id='SubmitCreate']/span"));

    }

}
