package com.automationpractice.appmanager;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.NameValuePair;
import org.apache.http.ProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.fluent.Executor;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class HttpProtocolHelper {

	private CloseableHttpResponse httpResponse;

	// Init Logger for TestBase.class
	final protected Logger httpSessionlogger = LoggerFactory.getLogger(HttpProtocolHelper.class);

	private ApplicationManager app;

	private CloseableHttpClient httpClient;

	private HttpClientContext context = HttpClientContext.create();

	private Timestamp timeStamp = new Timestamp(System.currentTimeMillis());

	private int rand = new Random().nextInt(99999998) + 1;

	private CookieStore cookieStore = new BasicCookieStore();

	private String webCookie;

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
		} catch (IllegalStateException e) {
			httpSessionlogger.error(e.toString());
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

	protected boolean isHttpStatusCode(int statusCode, CloseableHttpResponse response) {
		try {
			setHttpResponse(response);
			if (statusCode != response.getStatusLine().getStatusCode()) {
				throw new ProtocolException(
						statusCode + " doesnt match the response code " + response.getStatusLine().getStatusCode());
			} else
				return statusCode == response.getStatusLine().getStatusCode();
		} catch (Exception e) {
			httpSessionlogger.error(e.toString());
		}
		return false;
	}

	public String getToken() {
		return getApp().getProperty("web.token");
	}

	public CloseableHttpClient getHttpClient() {
		return this.httpClient;
	}

	public void setHttpClient(CloseableHttpClient httpClient) {
		this.httpClient = httpClient;
	}

	public HttpClientContext getContext() {
		return this.context;
	}

	public void setContext(HttpClientContext context) {
		this.context = context;
	}

	public ApplicationManager getApp() {
		return this.app;
	}

	public void setApp(ApplicationManager app) {
		this.app = app;
	}

	public Timestamp getTimeStamp() {
		return this.timeStamp;
	}

	public void setTimeStamp(Timestamp timeStamp) {
		this.timeStamp = timeStamp;
	}

	public int getRand() {
		return this.rand;
	}

	public void setRand(int rand) {
		this.rand = rand;
	}

	public void insertCookie(String cookie) {
		this.setWebCookie(cookie);
	}

	public String getCookieValue(CookieStore cookieStore, String cookieName) {
		String value = null;
		for (Cookie cookie : cookieStore.getCookies()) {
			if (cookie.getName().equals(cookieName)) {
				value = cookie.getName() + "=" + cookie.getValue();
				break;
			}
		}
		return value;
	}

	public void initCookie(String cookieName) throws IOException {
		HttpGet get = new HttpGet(getApp().getProperty("web.baseUrl"));
		get.setHeader("Upgrade-Insecure-Requests", "1");
		get.setHeader("Host", "automationpractice.com");
		getHttpClient().execute(get, getContext());
		this.setWebCookie(cookieName);
	}

	public String getCookieName() {
		return getApp().getProperty("web.cookies");
	}

	public CookieStore getCookieStore() {
		return this.cookieStore;
	}

	public void setCookieStore(CookieStore cookieStore) {
		this.cookieStore = cookieStore;
	}

	public String getWebCookie() {
		return this.webCookie;
	}

	public void setWebCookie(String webCookie) {
		this.webCookie = webCookie;
	}

	public CloseableHttpResponse getHttpResponse() {
		return httpResponse;
	}

	public void setHttpResponse(CloseableHttpResponse httpResponse) {
		this.httpResponse = httpResponse;
	}

}
