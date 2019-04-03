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

public class HttpRegistrationSessionHelper extends HttpSessionHelper {

	// requestForRegisterExistedAccountWithApiUsing Method
	protected String createFluentPostRequestWith(String email, String property)
			throws ClientProtocolException, IOException {
		String pageContent = Request.Post(property + "index.php?controller=authentication")
				.bodyForm(Form.form().add("controller", "authentication").add("SubmitCreate", "1").add("ajax", "true")
						.add("email_create", email).add("back", "my-account").build())
				.execute().returnContent().asString();
		return pageContent;
	}

	// signUpWith Method

	protected String[][] getBodyParamsWith(String email) {
		String[][] bodyParams = { { "controller", "authentication" }, { "SubmitCreate", "1" }, { "ajax", "true" },
				{ "email_create", email }, { "back", "my-account" }, { "token", "ce65cefcbafad255f0866d3b32d32058" } };
		return bodyParams;
	}

	// registerWith Method

	protected String[][] createHeaderParamsWithReferer() {
		String[][] headerParams = { { "Accept",
				"text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3" },
				{ "Host", "automationpractice.com" }, { "Cache-Control", "max-age=0" },
				{ "Origin", "http://automationpractice.com" }, { "Upgrade-Insecure-Requests", "1" },
				{ "Content-Type", "application/x-www-form-urlencoded" },
				{ "User-Agent",
						"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.86 Safari/537.36" },
				{ "Referer", "http://automationpractice.com/index.php?controller=authentication&back=my-account" } };
		return headerParams;
	}

	protected String[][] getBodyParamsWith(RegistrationFormData formData) {
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

	protected String[][] createHeaderParamsWithAuthorizationUsing(String property) {
		String[][] headerParams = { { "Accept", "application/json, text/javascript, */*; q=0.01" },
				{ "Authorization", property }, { "Content-Type", "application/x-www-form-urlencoded; charset=UTF-8" },
				{ "X-Requested-With", "XMLHttpRequest" } };
		return headerParams;
	}

	// verifyActivationLink Method

	protected void addStringParamsUsingEmailNameWith(String email, URIBuilder getRequest, String timestamp) {
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
			httpSessionlogger.error(e.toString());
		} catch (IllegalArgumentException e) {
			httpSessionlogger.error(e.toString());
		}
		return false;
	}

}
