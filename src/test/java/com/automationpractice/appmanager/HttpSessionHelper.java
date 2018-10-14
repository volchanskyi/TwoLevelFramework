package com.automationpractice.appmanager;

import com.automationpractice.model.LigalCredentials;

//Helper Class with Low Level Implementations
class HttpSessionHelper extends HttpProtocolHelper {

	// loginWith Method

	protected String[][] getBodyParamsForLoginWithMethod(LigalCredentials credentials) {
		String[][] bodyParams = { { "email", credentials.getEmail() }, { "passwd", credentials.getPassword() },
				{ "back", "my-account" }, { "SubmitLogin", "" } };
		return bodyParams;
	}

	// signUpWith Method

	protected String[][] getBodyParamsForsignUpWithMethod(String email) {
		String[][] bodyParams = { { "controller", "authentication" }, { "SubmitCreate", "1" }, { "ajax", "true" },
				{ "email_create", email }, { "back", "my-account" }, { "token", "ce65cefcbafad255f0866d3b32d32058" } };
		return bodyParams;
	}

	// registerWith Method

	protected String[][] getHeaderParamsForRegisterWithMethod() {
		String[][] headerParams = { { "Accept", "application/json, text/javascript" },
				{ "Content-Type", "application/x-www-form-urlencoded" } };
		return headerParams;
	}

	protected String[][] getBodyParamsForRegisterWithMethod(String fName, String lName, String password, String address,
			String city, String postcode, String state, String phone, String email) {
		String[][] bodyParams = { { "customer_firstname", fName }, { "customer_lastname", lName },
				{ "passwd", password }, { "firstname", fName }, { "lastname", lName }, { "email", email },
				{ "days", "" }, { "months", "" }, { "years", "" }, { "company", "" }, { "address1", address },
				{ "address2", "" }, { "city", city }, { "id_state", state }, { "postcode", postcode },
				{ "id_country", "21" }, { "phone_mobile", phone }, { "alias", "My address" }, { "back", "my-account" },
				{ "dni", "" }, { "email_create", "1" }, { "is_new_customer", "1" }, { "submitAccount", "" } };
		return bodyParams;
	}

	// createEmailWith Method

	protected String[][] getBodyParamsForCreateEmailWithMethod(String email) {
		String[][] bodyParams = { { "email_user", email.split("@")[0] }, { "lang", "en" },
				{ "site", "guerrillamail.com" }, { "in", "Set cancel" } };
		return bodyParams;
	}

	// addProductToCart Method

}
