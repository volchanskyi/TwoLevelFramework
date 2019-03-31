package com.automationpractice.appmanager;

import java.io.IOException;

import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.client.LaxRedirectStrategy;

import com.automationpractice.model.Products;

public class HttpSearchProductSession extends HttpSessionHelper {

	public HttpSearchProductSession(ApplicationManager app) {
		this.setApp(app);
		this.getContext().setCookieStore(getCookieStore());
		// Enable following REDIRECTIONS (302) on POST
		this.setHttpClient(HttpClients.custom().setRedirectStrategy(new LaxRedirectStrategy()).build());
	}

	public boolean searchForProduct(Products product) throws IOException {
		// Use fluent API
		String prod = product.getProductName();
		String response = createFluentPostUsingProductInfoWith(prod, getApp().getProperty("web.searchUrl"),
				getTimeStamp().getTime());
		return response.contains(prod.replaceAll("-", "").replaceAll(" ", "-").toLowerCase());

	}

}
