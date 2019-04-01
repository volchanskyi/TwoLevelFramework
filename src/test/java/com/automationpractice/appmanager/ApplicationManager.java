package com.automationpractice.appmanager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Properties;

public class ApplicationManager extends ApplicationManagerDriverWorker {

	public ApplicationManager(String browser) {
		this.setBrowser(browser);
		this.setProperties(new Properties());

	}

	public void init() {
		String target = System.getProperty("target", "local");
		try {
			getProperties().load(new FileReader(new File(String.format("src/test/resources/%s.properties", target))));
		} catch (FileNotFoundException e) {
			appManagerlogger.error(e.toString());
		} catch (IOException e) {
			appManagerlogger.error(e.toString());
		}
	}

	public void stop() {
		// Lazy init
		if (getWd() != null) {
			getWd().quit();
		}

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

	public RegistrationHelper registration() {
		// Lazy init
		if (getRegistrationHelper() == null) {
			try {
				setRegistrationHelper(new RegistrationHelper(this));
			} catch (MalformedURLException e) {
				appManagerlogger.error(e.toString());
			}
		}
		return getRegistrationHelper();
	}

}
