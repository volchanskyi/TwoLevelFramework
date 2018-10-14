package com.automationpractice.appmanager;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.fluent.Form;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.utils.URIBuilder;

import com.automationpractice.model.LigalCredentials;
import com.automationpractice.model.PDP;
import com.automationpractice.model.Products;

//Helper Class with Low Level Implementations
class HttpSessionHelper extends HttpProtocolHelper {

	// loginWith Method

	protected String[][] getBodyParamsWith(LigalCredentials credentials) {
		String[][] bodyParams = { { "email", credentials.getEmail() }, { "passwd", credentials.getPassword() },
				{ "back", "my-account" }, { "SubmitLogin", "" } };
		return bodyParams;
	}

	// signUpWith Method

	protected String[][] getBodyParamsWith(String email) {
		String[][] bodyParams = { { "controller", "authentication" }, { "SubmitCreate", "1" }, { "ajax", "true" },
				{ "email_create", email }, { "back", "my-account" }, { "token", "ce65cefcbafad255f0866d3b32d32058" } };
		return bodyParams;
	}

	// registerWith Method

	protected String[][] getHeaderParamsWithNoProperties() {
		String[][] headerParams = { { "Accept", "application/json, text/javascript" },
				{ "Content-Type", "application/x-www-form-urlencoded" } };
		return headerParams;
	}

	protected String[][] getBodyParamsWith(String fName, String lName, String password, String address,
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

	protected String[][] getBodyParamsUsingEmailNameWith(String email) {
		String[][] bodyParams = { { "email_user", email.split("@")[0] }, { "lang", "en" },
				{ "site", "guerrillamail.com" }, { "in", "Set cancel" } };
		return bodyParams;
	}

	protected String[][] createHeaderParamsWith(String property) {
		String[][] headerParams = { { "Accept", "application/json, text/javascript, */*; q=0.01" },
				{ "Authorization", property }, { "Content-Type", "application/x-www-form-urlencoded; charset=UTF-8" },
				{ "X-Requested-With", "XMLHttpRequest" } };
		return headerParams;
	}

	// addProductToCart Method

	protected String[][] createHeaderParamsUsingCookieWith(String cookieValue) {
		String[][] headerParams = { { "Accept", "application/json, text/javascript, */*; q=0.01" },
				{ "Content-Type", "application/x-www-form-urlencoded; charset=UTF-8" },
				{ "X-Requested-With", "XMLHttpRequest" }, { "Cookie", cookieValue } };
		return headerParams;
	}

	// loginWithErrorHandling Method
	protected String createFluentPostRequestUsingEmailWith(String email, String password, String property)
			throws ClientProtocolException, IOException {
		String content = Request
				.Post(property + "index.php?controller=authentication").bodyForm(Form.form().add("email", email)
						.add("passwd", password).add("back", "").add("SubmitLogin", "").build())
				.execute().returnContent().asString();
		return content;
	}

	// requestForRegisterExistedAccountWithApiUsing Method
	protected String createFluentPostRequestWith(String email, String property)
			throws ClientProtocolException, IOException {
		String pageContent = Request.Post(property + "index.php?controller=authentication")
				.bodyForm(Form.form().add("controller", "authentication").add("SubmitCreate", "1").add("ajax", "true")
						.add("email_create", email).add("back", "my-account").build())
				.execute().returnContent().asString();
		return pageContent;
	}

	// searchForProduct Method
	protected String createFluentPostUsingProductInfoWith(String prod, String property, long timestamp)
			throws ClientProtocolException, IOException {
		String response = Request.Get(
				property + prod.replaceAll(" ", "+") + "&limit=10&timestamp=" + timestamp + "&ajaxSearch=1&id_lang=1")
				.execute().returnContent().asString();
		return response;
	}

	// verifyActivationLink Method and createEmailWith Method

	protected void addStringParamsUsingEmailNameWith(String email, URIBuilder getRequest,
			String timestamp) {
		getRequest.setParameter("f", "get_email_list").setParameter("offset", "0")
				.setParameter("site", "guerrillamail.com").setParameter("in", email.split("@")[0])
				.setParameter("_", timestamp);
	}

	// addProductToWishListWithNoTokenUsing Method and getProductsFromCart Method
	protected void addStringParamsUsingPdpInfoWith(PDP pdp,
			URIBuilder getRequest, String rand, String timestamp) {
		getRequest.setParameter("rand", rand).setParameter("action", "add")
				.setParameter("id_product", String.valueOf(pdp.getId()))
				.setParameter("quantity", String.valueOf(pdp.getQuantity()))
				.setParameter("token", String.valueOf(pdp.getToken())).setParameter("id_product_attribute", "1")
				.setParameter("_", timestamp);
	}

	protected String[][] createHeaderParamsUsingPdpIndoWith(PDP pdp, String cookieValue) {
		String[][] headerParams = { { "Accept", "application/json, text/javascript, */*; q=0.01" },
				{ "Content-Type", "application/x-www-form-urlencoded; charset=UTF-8" }, { "Cookie", cookieValue },
				{ "Host", "automationpractice.com" }, { "Referer", "http://automationpractice.com/index.php?id_product="
						+ String.valueOf(pdp.getId()) + "&controller=product" },
				{ "X-Requested-With", "XMLHttpRequest" } };
		return headerParams;
	}

	protected String createFluentPostRequestUsingProductInfoWith(Products newProduct, String property, int rand, String cookieValue)
			throws ClientProtocolException, IOException {
		String json = Request.Post(property + "index.php?rand=" + rand)
				.addHeader("Accept", "application/json, text/javascript, */*; q=0.01")
				.addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8")
				.addHeader("X-Requested-With", "XMLHttpRequest").addHeader("Cookie", cookieValue)
				.bodyForm(Form.form().add("controller", "cart").add("add", "1").add("ajax", "true")
						.add("qty", String.valueOf(newProduct.getQuantity()))
						.add("id_product", String.valueOf(newProduct.getId())).build())
				.execute().returnContent().asString();
		return json;
	}

	protected String createFluentPostRequestUsingTokenWith(String token, String property, int rand, String cookie)
			throws ClientProtocolException, IOException {
		String json = Request.Post(property + "index.php?rand=" + rand)
				.addHeader("Accept", "application/json, text/javascript, */*; q=0.01")
				.addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8")
				.addHeader("X-Requested-With", "XMLHttpRequest").addHeader("Cookie", cookie)
				.bodyForm(Form.form().add("controller", "cart").add("ajax", "true").add("token", token).build())
				.execute().returnContent().asString();
		return json;
	}

	protected void createFluentPostRequestUsingTokenWith(String token, String id, String ipa, String property, int rand,
			String cookie) throws ClientProtocolException, IOException {
		Request.Post(property + "index.php?rand=" + rand)
				.addHeader("Accept", "application/json, text/javascript, */*; q=0.01")
				.addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8")
				.addHeader("X-Requested-With", "XMLHttpRequest").addHeader("Cookie", cookie)
				.bodyForm(Form.form().add("controller", "cart").add("delete", "1").add("id_product", id).add("ipa", ipa)
						.add("token", token).add("ajax", "true").build())
				.execute().returnContent().asString();
	}

}
