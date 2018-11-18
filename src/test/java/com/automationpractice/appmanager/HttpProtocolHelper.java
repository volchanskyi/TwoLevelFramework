package com.automationpractice.appmanager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.NameValuePair;
import org.apache.http.client.fluent.Executor;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

class HttpProtocolHelper {

	protected CloseableHttpResponse httpResponse;
	private int statusCodeOK = 200;

	protected String getTextFrom(CloseableHttpResponse response) throws IOException {
		try {
			return EntityUtils.toString(response.getEntity());
		} finally {
			response.close();
		}
	}
	
	// RegEX parser for pure HTML
	protected String parsePureHtmlWithRegExUsing(String regex, String stringToApplyRegexOn) {
		try {
			// Generate pattern to catch the string
			Pattern pattern = Pattern.compile(regex, Pattern.MULTILINE);
			// Apply pattern to the string
			Matcher matcher = pattern.matcher(stringToApplyRegexOn);
			matcher.find();
			return matcher.group(1);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "Couldn`t find RegEx pattern";

	}

	protected Executor getExecutor(CloseableHttpClient httpClient) {
		return Executor.newInstance(httpClient);
	}

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

	protected boolean isHttpStatusCodeOK(CloseableHttpResponse response) {
		try {
			httpResponse = response;
			int statusCode = response.getStatusLine().getStatusCode();
			return statusCode == statusCodeOK;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

}
