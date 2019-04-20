package com.automationpractice.appmanager;

import java.io.IOException;
import java.net.URISyntaxException;

import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;

import com.automationpractice.model.LigalCredentials;
import com.automationpractice.model.Products;

//Helper Class with Low Level Implementations
public class HttpSessionHelper extends HttpProtocolHelper {

	// Cart and WishList helpers use this method
	protected void addStringParamsUsingPdpInfoWith(Products products, LigalCredentials credentials,
			URIBuilder getRequest, String rand, String timestamp) {
		getRequest.setParameter("rand", rand).setParameter("action", "add")
				.setParameter("id_product", String.valueOf(products.getId()))
				.setParameter("quantity", String.valueOf(products.getQuantity()))
				.setParameter("token", String.valueOf(credentials.getToken())).setParameter("id_product_attribute", "1")
				.setParameter("_", timestamp);
	}

	protected String[][] createHeaderParamsToAcceptTextHtml() {
		String[][] headerParams = { { "Accept", "text/html,application/xhtml+xml,application/xml" },
				{ "Content-Type", "application/x-www-form-urlencoded" }, { "Host", "automationpractice.com" } };
		return headerParams;
	}

	protected String[][] createHeaderParamsToAcceptJson() {
		String[][] headerParams = { { "Accept", "application/json, text/javascript, */*; q=0.01" },
				{ "Host", "automationpractice.com" } };
		return headerParams;
	}

	// Login and WishList helpers use this method
	protected String[][] getBodyParamsWith(LigalCredentials credentials) {
		String[][] bodyParams = { { "email", credentials.getEmail() }, { "passwd", credentials.getPassword() },
				{ "back", "my-account" }, { "SubmitLogin", "" } };
		return bodyParams;
	}

	public boolean loginWith(LigalCredentials credentials, String pageTitle) throws IOException, URISyntaxException {
		URIBuilder postRequest = new URIBuilder(getApp().getProperty("web.baseUrl") + "index.php");
		// query string params
		postRequest.setParameter("controller", "authentication");
		// request header
		String[][] headerParams = createHeaderParamsToAcceptTextHtml();
		// Form Data
		String[][] bodyParams = getBodyParamsWith(credentials);
		HttpPost post = createPostRequestWithParams(postRequest.toString(), headerParams);
		post.setEntity(new UrlEncodedFormEntity(createHttpBodyParamsWith(bodyParams)));
		CloseableHttpResponse response = this.getHttpClient().execute(post, this.getContext());
		String body = getTextFrom(response);
		isHttpStatusCode(200, response);
		return body.contains(String.format("<title>%s</title>", pageTitle));
	}

}