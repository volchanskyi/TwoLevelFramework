package com.automationpractice.appmanager;

import java.net.MalformedURLException;
import java.util.Properties;

public class ApplicationManager extends WebDriverWorker {

	public ApplicationManager(String browser) {
		this.setBrowser(browser);
		this.setProperties(new Properties());

	}
	
	// HTTP session management section
	public HttpSearchProductSession newSearchProductSession() {
		return new HttpSearchProductSession(this);
	}

	public HttpRegistrationSession newRegistrationSession() {
		return new HttpRegistrationSession(this);
	}

	public HttpLoginSession newLoginSession() {
		return new HttpLoginSession(this);
	}

	public HttpWishListSession newWishListSession() {
		return new HttpWishListSession(this);
	}

	public HttpCartSession newCartSession() {
		return new HttpCartSession(this);
	}

	public RegistrationHelper newRegistrationSessionUI() {
		// Lazy init
		if (getRegistrationHelper() == null) {
			try {
				setRegistrationHelper(new RegistrationHelper(this));
			} catch (MalformedURLException e) {
				APP_MANAGER_LOGGER.error(e.toString());
			}
		}
		return getRegistrationHelper();
	}

}
