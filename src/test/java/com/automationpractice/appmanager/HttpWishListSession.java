package com.automationpractice.appmanager;

import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.Timestamp;
import java.util.Random;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.client.LaxRedirectStrategy;

import com.automationpractice.model.LigalCredentials;
import com.automationpractice.model.Products;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

public class HttpWishListSession extends HttpSessionHelper {
	private CloseableHttpClient httpClient;
	private HttpClientContext context = HttpClientContext.create();
	private CookieStore cookieStore = new BasicCookieStore();
	private ApplicationManager app;
	private Timestamp timeStamp = new Timestamp(System.currentTimeMillis());
	private int rand = new Random().nextInt(99999998) + 1;
	private String webCookie;

	public HttpWishListSession(ApplicationManager app) {
		this.app = app;
		this.context.setCookieStore(cookieStore);
		// Enable following REDIRECTIONS (302) on POST
		this.httpClient = HttpClients.custom().setRedirectStrategy(new LaxRedirectStrategy()).build();
	}

	public boolean navigateToPdpUsing(Products products)
			throws JsonSyntaxException, IOException, IllegalStateException, URISyntaxException {
		URIBuilder getRequest = new URIBuilder(app.getProperty("web.baseUrl") + "index.php");
		// query string params
		getRequest.setParameter("id_product", String.valueOf(products.getId())).setParameter("controller", "product");
		// request header
		String[][] headerParams = { { "Cookie", getCookieValue(cookieStore, this.webCookie) } };
		HttpGet get = createGetRequestWithParams(getRequest.toString(), headerParams);
		CloseableHttpResponse response = httpClient.execute(get, this.context);
		isHttpStatusCodeOK(response);
		String body = getTextFrom(response);
		return body.toLowerCase()
				.contains(String.format("<title>%s</title>", products.getProductName() + " - my store"));
	}

	public String addProductToWishListUsing(Products products, LigalCredentials credentials)
			throws IOException, URISyntaxException {
		URIBuilder getRequest = new URIBuilder(app.getProperty("web.baseUrl") + "modules/blockwishlist/cart.php");
		// query string params
		addStringParamsUsingPdpInfoWith(products, credentials, getRequest, String.valueOf(this.rand),
				String.valueOf((timeStamp.getTime())));
		// request header
		String[][] headerParams = createHeaderParamsUsingPdpIndoWith(products,
				getCookieValue(cookieStore, this.webCookie));
		HttpGet get = createGetRequestWithParams(getRequest.toString(), headerParams);
		CloseableHttpResponse response = httpClient.execute(get, this.context);
		isHttpStatusCodeOK(response);
		return getTextFrom(response);
	}

	public boolean addedToWishListAs(Products products)
			throws ClientProtocolException, IOException, URISyntaxException {
		URIBuilder getRequest = new URIBuilder(app.getProperty("web.baseUrl"));
		// query string params
		addStringParamsUsingWishListInfoWith(getRequest);
		// request header
		String[][] headerParams = createHeaderParamsUsingCookieWith(getCookieValue(cookieStore, this.webCookie));
		HttpGet get = createGetRequestWithParams(getRequest.toString(), headerParams);
		CloseableHttpResponse response = httpClient.execute(get, this.context);
		isHttpStatusCodeOK(response);
		String json = getTextFrom(response);
		JsonElement addedProducts = new JsonParser().parse(parsePureHtmlWithRegExUsing("^.*(\\[.*\\])\\;$", json));
		String wishListId = parsePureHtmlWithRegExUsing("^.*\\('block-order-detail'\\,\\s\\'(\\d{4}).*$", json);
		boolean bool = isAdded(products, addedProducts) ? deleteWishListWith(wishListId) : false;
		return bool;
	}

	private boolean deleteWishListWith(String wishListId)
			throws URISyntaxException, ClientProtocolException, IOException {
		URIBuilder getRequest = new URIBuilder(app.getProperty("web.baseUrl"));
		// query string params
		addStringParamsUsingWishListWithAddedProductInfoWith(getRequest, this.rand, wishListId, timeStamp);
		// request header
		String[][] headerParams = createHeaderParamsUsingCookieWith(getCookieValue(cookieStore, this.webCookie));
		HttpGet get = createGetRequestWithParams(getRequest.toString(), headerParams);
		CloseableHttpResponse response = httpClient.execute(get, this.context);
		isHttpStatusCodeOK(response);
		String json = getTextFrom(response);
		JsonElement addedProducts = new JsonParser().parse(parsePureHtmlWithRegExUsing("^.*(\\[.*\\])\\;$", json));
		return isWishListEmpty(addedProducts);
	}

	// Cookie management section
	public void insertCookie(String cookie) {
		this.webCookie = cookie;
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
		HttpGet get = new HttpGet(app.getProperty("web.baseUrl"));
		get.setHeader("Upgrade-Insecure-Requests", "1");
		get.setHeader("Host", "automationpractice.com");
		httpClient.execute(get, context);
		this.webCookie = cookieName;
	}

	public String getToken() {
		return app.getProperty("web.token");
	}

	public String getCookieName() {
		return app.getProperty("web.cookies");
	}
}
