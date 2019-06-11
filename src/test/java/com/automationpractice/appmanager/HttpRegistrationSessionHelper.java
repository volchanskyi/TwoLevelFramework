package com.automationpractice.appmanager;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.fluent.Form;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.utils.URIBuilder;

import com.automationpractice.model.RegistrationFormData;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonSyntaxException;

abstract class HttpRegistrationSessionHelper extends HttpSessionHelper
		implements HttpHeaderParameterInterface, HttpBodyParametersInterface {

	// requestForRegisterExistedAccountWithApiUsing Method
	protected static String createFluentPostRequestWith(String email, String property)
			throws ClientProtocolException, IOException {
		String pageContent = Request.Post(property + "index.php?controller=authentication")
				.bodyForm(Form.form().add("controller", "authentication").add("SubmitCreate", "1").add("ajax", "true")
						.add("email_create", email).add("back", "my-account").build())
				.execute().returnContent().asString();
		return pageContent;
	}

	// signUpWith Method
	@Override
	public String[][] setBodyParameters(String email, String token) {
		String[][] bodyParams = { { "controller", "authentication" }, { "SubmitCreate", "1" }, { "ajax", "true" },
				{ "email_create", email }, { "back", "my-account" }, { "token", token } };
		return bodyParams;
	}

	// registerWith Method
	@Override
	public String[][] setBodyParameters(RegistrationFormData formData) {
		String[][] bodyParams = { { "customer_firstname", formData.getFirstName() },
				{ "customer_lastname", formData.getLastName() }, { "email", formData.getEmail() },
				{ "passwd", formData.getPassword() }, { "firstname", formData.getFirstName() },
				{ "lastname", formData.getLastName() }, { "days", "" }, { "months", "" }, { "years", "" },
				{ "company", "" }, { "address1", formData.getAddress() }, { "address2", "" },
				{ "city", formData.getCityName() }, { "id_state", formData.getState() },
				{ "postcode", formData.getPostCode() }, { "id_country", "21" },
				{ "phone_mobile", formData.getPhoneNumber() }, { "alias", "My address" }, { "dni", "" },
				{ "email_create", "1" }, { "is_new_customer", "1" }, { "back", "my-account" },
				{ "submitAccount", "" } };
		return bodyParams;
	}

	// createEmailWith Method

	protected String[][] getBodyParamsUsingEmailNameWith(String email) {
		String[][] bodyParams = { { "email_user", email.split("@")[0] }, { "lang", "en" },
				{ "site", "guerrillamail.com" }, { "in", "Set cancel" } };
		return bodyParams;
	}

	// For creating emails ONLY (overrides "Host" parameter)
	@Override
	public String[][] setHeaderParameter(String property) {
		return setHeaderParamsToAcceptJson("Authorization", property, "Host", "www.guerrillamail.com");
	}

	// verifyActivationLink Method

	protected void setQueryParameters(String email, URIBuilder getRequest, String timestamp) {
		getRequest.setParameter("f", "get_email_list").setParameter("offset", "0")
				.setParameter("site", "guerrillamail.com").setParameter("in", email.split("@")[0])
				.setParameter("_", timestamp);
	}

	protected boolean isContained(String email, String link, JsonElement parsed, String emailAddrKey, boolean statusKey,
			JsonArray errorCodeKey) {
		try {
			if (statusKey == true & errorCodeKey.size() == 0) {
				JsonArray inbox = parsed.getAsJsonObject().get("list").getAsJsonArray();
				for (JsonElement jSo : inbox) {
					if (jSo.getAsJsonObject().get("mail_body").getAsString().contains(link)) {
						return emailAddrKey.equals(email);
					}
				}
			}
		} catch (JsonSyntaxException e) {
			HTTP_SESSION_LOGGER.error(e.toString());
		} catch (IllegalArgumentException e) {
			HTTP_SESSION_LOGGER.error(e.toString());
		}
		return false;
	}

}
