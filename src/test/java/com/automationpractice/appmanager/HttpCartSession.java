package com.automationpractice.appmanager;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Random;
import java.util.Set;

import org.apache.http.client.CookieStore;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.client.LaxRedirectStrategy;

import com.automationpractice.model.Products;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

public class HttpCartSession extends HttpSessionHelper {
	private CloseableHttpClient httpClient;
	private HttpClientContext context = HttpClientContext.create();
	private CookieStore cookieStore = new BasicCookieStore();
	private ApplicationManager app;
	private int rand = new Random().nextInt(99999998) + 1;
	private String webCookie;

	public HttpCartSession(ApplicationManager app) {
		this.app = app;
		this.context.setCookieStore(cookieStore);
		// Enable following REDIRECTIONS (302) on POST
		this.httpClient = HttpClients.custom().setRedirectStrategy(new LaxRedirectStrategy()).build();
	}

	public Set<Products> addProductToCart(String id, String quantity, String token)
			throws JsonSyntaxException, IOException, IllegalStateException, URISyntaxException {
		URIBuilder postRequest = new URIBuilder(app.getProperty("web.baseUrl") + "index.php");
		// query string params
		postRequest.setParameter("rand", String.valueOf(this.rand));
		// request header
		String[][] headerParams = createHeaderParamsUsingCookieWith(getCookieValue(cookieStore, this.webCookie));
		// Form Data
		String[][] bodyParams = createBodyParamsForAddProductToCartMethod(id, quantity, token);
		HttpPost post = createPostRequestWithParams(postRequest.toString(), headerParams);
		post.setEntity(new UrlEncodedFormEntity(createHttpBodyParamsWith(bodyParams)));
		CloseableHttpResponse response = httpClient.execute(post, this.context);
		isHttpStatusCodeOK(response);
		String json = getTextFrom(response);
		JsonElement parsed = new JsonParser().parse(json);
		JsonElement key = parsed.getAsJsonObject().get("products");
		return new Gson().fromJson(key, new TypeToken<Set<Products>>() {
		}.getType());
	}

	public Set<Products> getProductsFromCart(String token) throws IOException, URISyntaxException {
		URIBuilder postRequest = new URIBuilder(app.getProperty("web.baseUrl") + "index.php");
		// query string params
		postRequest.setParameter("rand", String.valueOf(this.rand));
		String[][] headerParams = createHeaderParamsUsingCookieWith(getCookieValue(cookieStore, this.webCookie));
		// Form Data
		String[][] bodyParams = { { "controller", "cart" }, { "ajax", "true" }, { "token", token } };
		HttpPost post = createPostRequestWithParams(postRequest.toString(), headerParams);
		post.setEntity(new UrlEncodedFormEntity(createHttpBodyParamsWith(bodyParams)));
		CloseableHttpResponse response = httpClient.execute(post, this.context);
		isHttpStatusCodeOK(response);
		String json = getTextFrom(response);
		JsonElement parsed = new JsonParser().parse(json);
		JsonElement key = parsed.getAsJsonObject().get("products");
		return new Gson().fromJson(key, new TypeToken<Set<Products>>() {
		}.getType());
	}

	public Products addProductToCart(Products newProduct) throws IOException {
		// Use fluent API
		String json = createFluentPostRequestUsingProductInfoWith(newProduct, app.getProperty("web.baseUrl"), this.rand,
				getCookieValue(cookieStore, this.webCookie));
		JsonElement parsed = new JsonParser().parse(json);
		JsonArray jsonArray = parsed.getAsJsonObject().getAsJsonArray("products");
		return isAdded(newProduct, jsonArray);
	}

	public void cleanUpCart(String token) throws IOException {
		// Use fluent API
		String json = createFluentPostRequestUsingTokenWith(token, app.getProperty("web.baseUrl"), this.rand,
				this.webCookie);
		JsonElement parsed = new JsonParser().parse(json);
		JsonElement key = parsed.getAsJsonObject().get("nbTotalProducts");
		cleanUpUsing(token, parsed, key, app.getProperty("web.baseUrl"), this.rand, this.webCookie);
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
