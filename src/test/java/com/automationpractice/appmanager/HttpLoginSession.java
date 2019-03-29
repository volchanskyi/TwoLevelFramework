package com.automationpractice.appmanager;

import java.io.IOException;
import java.net.URISyntaxException;

import org.apache.http.client.CookieStore;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.client.LaxRedirectStrategy;

import com.automationpractice.model.LigalCredentials;

public class HttpLoginSession extends HttpSessionHelper {
	private CloseableHttpClient httpClient;
	private HttpClientContext context = HttpClientContext.create();
	private CookieStore cookieStore = new BasicCookieStore();
	private ApplicationManager app;

	public HttpLoginSession(ApplicationManager app) {
		this.app = app;
		this.context.setCookieStore(cookieStore);
		// Enable following REDIRECTIONS (302) on POST
		this.httpClient = HttpClients.custom().setRedirectStrategy(new LaxRedirectStrategy()).build();
	}

	public boolean loginWith(LigalCredentials credentials, String pageTitle) throws IOException, URISyntaxException {
		URIBuilder postRequest = new URIBuilder(app.getProperty("web.baseUrl") + "index.php");
		// query string params
		postRequest.setParameter("controller", "authentication");
		// request header
		String[][] headerParams = { { "Host", "automationpractice.com" } };
		// Form Data
		String[][] bodyParams = getBodyParamsWith(credentials);
		HttpPost post = createPostRequestWithParams(postRequest.toString(), headerParams);
		post.setEntity(new UrlEncodedFormEntity(createHttpBodyParamsWith(bodyParams)));
		CloseableHttpResponse response = this.httpClient.execute(post, this.context);
		String body = getTextFrom(response);
		isHttpStatusCodeOK(response);
		return body.contains(String.format("<title>%s</title>", pageTitle));
	}

	public boolean loginWithErrorHandling(String email, String password, String errorMsg) throws IOException {
		String content = createFluentPostRequestUsingEmailWith(email, password, app.getProperty("web.baseUrl"));
		return content.contains(errorMsg);

	}

	public boolean isLoggedInAs(LigalCredentials credentials) throws IOException, URISyntaxException {
		URIBuilder getRequest = new URIBuilder(app.getProperty("web.baseUrl") + "/index.php");
		// query string params
		getRequest.setParameter("controller", "my-account");
		// request header
		String[][] headerParams = { { "Host", "automationpractice.com" } };
		HttpGet get = createGetRequestWithParams(getRequest.toString(), headerParams);
		CloseableHttpResponse response = this.httpClient.execute(get, this.context);
		isHttpStatusCodeOK(response);
		String body = getTextFrom(response);
		return body.contains(String.format("<span>%s</span>", credentials.getAccountName()));
	}
}
