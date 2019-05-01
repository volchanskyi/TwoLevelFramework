package com.automationpractice.appmanager;

import java.io.IOException;
import java.net.URISyntaxException;

import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;

import com.automationpractice.model.LigalCredentials;
import com.automationpractice.model.Products;
import com.google.gson.JsonSyntaxException;

//Helper Class with Low Level Implementations
abstract class HttpSessionHelper extends HttpProtocolHelper implements LoginWithCredentialsInterface, NavigateToPdpInterface {

	final protected String[][] setHeaderParamsToAcceptHtml() {
		String[][] headerParams = { { "Accept", "text/html,application/xhtml+xml,application/xml" },
				{ "Content-Type", "application/x-www-form-urlencoded" }, { "Host", "automationpractice.com" } };
		return headerParams;
	}

	final protected String[][] setHeaderParamsToAcceptJson(String fOp, String fV, String sOp, String sV) {
		String[][] basicHeaderParams = { { "Accept", "application/json, text/javascript, */*; q=0.01" },
				{ "Host", "automationpractice.com" }, { fOp, fV }, { sOp, sV } };
		return basicHeaderParams;
	}

	// Login and WishList helpers use this method
	protected String[][] setBodyParameters(LigalCredentials credentials) {
		String[][] bodyParams = { { "email", credentials.getEmail() }, { "passwd", credentials.getPassword() },
				{ "back", "my-account" }, { "SubmitLogin", "" } };
		return bodyParams;
	}

	@Override
	final public boolean loginWith(LigalCredentials credentials, String pageTitle)
			throws IOException, URISyntaxException {
		URIBuilder postRequest = new URIBuilder(getApp().getProperty("web.baseUrl") + "index.php");
		// query string params
		postRequest.setParameter("controller", "authentication");
		// request header
		String[][] headerParams = setHeaderParamsToAcceptHtml();
		// Form Data
		String[][] bodyParams = setBodyParameters(credentials);
		HttpPost post = createPostRequestWithParams(postRequest.toString(), headerParams);
		post.setEntity(new UrlEncodedFormEntity(createHttpBodyParamsWith(bodyParams)));
		CloseableHttpResponse response = this.getHttpClient().execute(post, this.getContext());
		String body = getTextFrom(response);
		isHttpStatusCode(200, response);
		return body.contains(String.format("<title>%s</title>", pageTitle));
	}
	
	@Override
	final public boolean navigateToPdpUsing(Products products)
			throws JsonSyntaxException, IOException, IllegalStateException, URISyntaxException {
				URIBuilder getRequest = new URIBuilder(getApp().getProperty("web.baseUrl") + "index.php");
				// query string params
				getRequest.setParameter("id_product", String.valueOf(products.getId())).setParameter("controller", "product");
				// request header
				String[][] headerParams = { { "Cookie", getCookieValue(getCookieStore(), this.getWebCookie()) } };
				HttpGet get = createGetRequestWithParams(getRequest.toString(), headerParams);
				CloseableHttpResponse response = getHttpClient().execute(get, this.getContext());
				isHttpStatusCode(200, response);
				String body = getTextFrom(response);
				return body.toLowerCase()
						.contains(String.format("<title>%s</title>", products.getProductName() + " - my store"));
			}

}