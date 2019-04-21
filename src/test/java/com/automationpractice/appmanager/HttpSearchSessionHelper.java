package com.automationpractice.appmanager;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.fluent.Request;

abstract class HttpSearchSessionHelper extends HttpSessionHelper {
	// searchForProduct Method
	protected String createFluentPostUsingProductInfoWith(String prod, String property, long timestamp)
			throws ClientProtocolException, IOException {
		String response = Request.Get(
				property + prod.replaceAll(" ", "+") + "&limit=10&timestamp=" + timestamp + "&ajaxSearch=1&id_lang=1")
				.execute().returnContent().asString();
		return response;
	}

}
