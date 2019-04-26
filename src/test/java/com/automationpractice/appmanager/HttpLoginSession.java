package com.automationpractice.appmanager;

import java.io.IOException;
import java.net.URISyntaxException;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.client.LaxRedirectStrategy;

import com.automationpractice.model.LigalCredentials;

public class HttpLoginSession extends HttpLoginSessionHelper {

	HttpLoginSession(ApplicationManager app) {
		this.setApp(app);
		this.getContext().setCookieStore(getCookieStore());
		// Enable following REDIRECTIONS (302) on POST
		this.setHttpClient(HttpClients.custom().setRedirectStrategy(new LaxRedirectStrategy()).build());
	}
	
	public boolean loginWithErrorHandling(String email, String password, String errorMsg) throws IOException {
		String content = createFluentPostRequestWith(email, password, getApp().getProperty("web.baseUrl"));
		return content.contains(errorMsg);

	}

	public boolean isLoggedInAs(LigalCredentials credentials) throws IOException, URISyntaxException {
		URIBuilder getRequest = new URIBuilder(getApp().getProperty("web.baseUrl") + "/index.php");
		// query string params
		getRequest.setParameter("controller", "my-account");
		// request header
		String[][] headerParams = setHeaderParamsToAcceptHtml();
		HttpGet get = createGetRequestWithParams(getRequest.toString(), headerParams);
		CloseableHttpResponse response = this.getHttpClient().execute(get, this.getContext());
		isHttpStatusCode(200, response);
		String body = getTextFrom(response);
		return body.contains(String.format("<span>%s</span>", credentials.getAccountName()));
	}
}
