package com.automationpractice.appmanager;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Random;

import org.apache.http.client.CookieStore;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;

import com.automationpractice.model.LigalCredentials;
import com.automationpractice.model.Products;

//Helper Class with Low Level Implementations
public class HttpSessionHelper extends HttpProtocolHelper {

	// HttpSession classes use this fields
	private ApplicationManager app;
	private CloseableHttpClient httpClient;
	private HttpClientContext context = HttpClientContext.create();
	private CookieStore cookieStore = new BasicCookieStore();
	private String webCookie;
	private Timestamp timeStamp = new Timestamp(System.currentTimeMillis());
	private int rand = new Random().nextInt(99999998) + 1;

	// Cart and WishList helpers use this method

	protected String[][] createHeaderParamsToAcceptJsonUsingCookieWith(String cookieValue) {
		String[][] headerParams = { { "Accept", "application/json, text/javascript, */*; q=0.01" },
				{ "Content-Type", "application/x-www-form-urlencoded; charset=UTF-8" }, { "cache-control", "no-cache" },
				{ "X-Requested-With", "XMLHttpRequest" }, { "Cookie", cookieValue } };
		return headerParams;
	}

	// Cart and WishList helpers use this method
	protected void addStringParamsUsingPdpInfoWith(Products products, LigalCredentials credentials,
			URIBuilder getRequest, String rand, String timestamp) {
		getRequest.setParameter("rand", rand).setParameter("action", "add")
				.setParameter("id_product", String.valueOf(products.getId()))
				.setParameter("quantity", String.valueOf(products.getQuantity()))
				.setParameter("token", String.valueOf(credentials.getToken())).setParameter("id_product_attribute", "1")
				.setParameter("_", timestamp);
	}

	protected String[][] createHeaderParamsToAcceptTextHtml() {
		String[][] headerParams = { { "Accept",
				"text/html,application/xhtml+xml,application/xml"},
				{ "Content-Type", "application/x-www-form-urlencoded" }, {"Host", "automationpractice.com"} };
		return headerParams;
	}

	// Cookie management section
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

	// Getters and Setters
	public String getToken() {
		return getApp().getProperty("web.token");
	}

	public String getCookieName() {
		return getApp().getProperty("web.cookies");
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

	public CookieStore getCookieStore() {
		return this.cookieStore;
	}

	public void setCookieStore(CookieStore cookieStore) {
		this.cookieStore = cookieStore;
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

	public String getWebCookie() {
		return this.webCookie;
	}

	public void setWebCookie(String webCookie) {
		this.webCookie = webCookie;
	}

	public int getRand() {
		return this.rand;
	}

	public void setRand(int rand) {
		this.rand = rand;
	}

}