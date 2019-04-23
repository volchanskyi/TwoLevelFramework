package com.automationpractice.appmanager;

import java.io.IOException;
import java.net.URISyntaxException;

import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;

import com.automationpractice.model.LigalCredentials;

//Helper Class with Low Level Implementations
abstract class HttpSessionHelper extends HttpProtocolHelper implements LoginWithCredentialsInterface {

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

}