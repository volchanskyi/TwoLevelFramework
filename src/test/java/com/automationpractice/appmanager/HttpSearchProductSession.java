package com.automationpractice.appmanager;

import java.io.IOException;
import java.sql.Timestamp;

import org.apache.http.client.CookieStore;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.client.LaxRedirectStrategy;

import com.automationpractice.model.Products;

public class HttpSearchProductSession extends HttpSessionHelper {
	private CloseableHttpClient httpClient;
	private HttpClientContext context = HttpClientContext.create();
	private CookieStore cookieStore = new BasicCookieStore();
	private ApplicationManager app;
	private Timestamp timeStamp = new Timestamp(System.currentTimeMillis());

	public HttpSearchProductSession(ApplicationManager app) {
		this.app = app;
		this.context.setCookieStore(cookieStore);
		// Enable following REDIRECTIONS (302) on POST
		this.setHttpClient(HttpClients.custom().setRedirectStrategy(new LaxRedirectStrategy()).build());
	}

	public boolean searchForProduct(Products product) throws IOException {
		// Use fluent API
		String prod = product.getProductName();
		String response = createFluentPostUsingProductInfoWith(prod, app.getProperty("web.searchUrl"),
				timeStamp.getTime());
		return response.contains(prod.replaceAll("-", "").replaceAll(" ", "-").toLowerCase());

	}

	public CloseableHttpClient getHttpClient() {
		return httpClient;
	}

	public void setHttpClient(CloseableHttpClient httpClient) {
		this.httpClient = httpClient;
	}


}
