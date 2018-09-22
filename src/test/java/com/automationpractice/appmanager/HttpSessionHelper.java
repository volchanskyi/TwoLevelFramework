package com.automationpractice.appmanager;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.client.fluent.Executor;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class HttpSessionHelper {

	protected ArrayList<NameValuePair> createHttpBodyParamsWith(String paramPairs[][]) {
		ArrayList<NameValuePair> params = new ArrayList<>();
		for (int row = 0; row < paramPairs.length; row++) {
			params.add(new BasicNameValuePair(paramPairs[row][0], paramPairs[row][1]));
		}
		return params;
	}

	protected HttpPost createPostRequestWithParams(String post, String[][] params) {
		HttpPost postWithParams = new HttpPost(post);
		for (int row = 0; row < params.length; row++) {
			postWithParams.setHeader(params[row][0], params[row][1]);
		}
		return postWithParams;
	}

	protected HttpGet createGetRequestWithParams(String get, String[][] params) {
		HttpGet getWithParams = new HttpGet(get);
		for (int row = 0; row < params.length; row++) {
			getWithParams.setHeader(params[row][0], params[row][1]);
		}
		return getWithParams;
	}

	protected String getTextFrom(CloseableHttpResponse response) throws IOException {
		try {
			return EntityUtils.toString(response.getEntity());
		} finally {
			response.close();
		}
	}

	protected Executor getExecutor(CloseableHttpClient httpClient) {
		return Executor.newInstance(httpClient);
	}
}
