package com.automationpractice.appmanager;

import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.Timestamp;
import java.util.Random;
import java.util.Set;

import org.apache.http.client.ClientProtocolException;
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

import com.automationpractice.model.LigalCredentials;
import com.automationpractice.model.Products;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

public class HttpSession extends HttpSessionHelper {
	private CloseableHttpClient httpClient;
	private HttpClientContext context = HttpClientContext.create();
	private CookieStore cookieStore = new BasicCookieStore();
	private ApplicationManager app;
	private Timestamp timeStamp = new Timestamp(System.currentTimeMillis());
	private int rand = new Random().nextInt(99999998) + 1;
	private String webCookie;

	public HttpSession(ApplicationManager app) {
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

	// TODO refactor token
	public boolean signUpWith(String email) throws IOException {
		HttpPost post = new HttpPost(app.getProperty("web.baseUrl") + "index.php");
		// Form Data
		String[][] bodyParams = getBodyParamsWith(email);
		post.setEntity(new UrlEncodedFormEntity(createHttpBodyParamsWith(bodyParams)));
		CloseableHttpResponse response = httpClient.execute(post);
		isHttpStatusCodeOK(response);
		String body = getTextFrom(response);
		return body.contains(String.format("<h1 class=\\\"page-heading\\\">%s<\\/h1>", "Create an account"));
	}

	// TODO refactor generated data
	public boolean registerWith(String fName, String lName, String password, String address, String city,
			String postcode, String state, String phone, String title, String email)
			throws IOException, URISyntaxException {
		URIBuilder postRequest = new URIBuilder(app.getProperty("web.baseUrl") + "index.php");
		// query string params
		postRequest.setParameter("controller", "authentication").setParameter("back", "my-account#account-creation");
		// header params
		String[][] headerParams = getHeaderParamsWithNoProperties();
		String[][] bodyParams = getBodyParamsWith(fName, lName, password, address, city, postcode, state, phone, email);
		HttpPost post = createPostRequestWithParams(postRequest.toString(), headerParams);
		post.setEntity(new UrlEncodedFormEntity(createHttpBodyParamsWith(bodyParams)));
		CloseableHttpResponse response = httpClient.execute(post);
		isHttpStatusCodeOK(response);
		String body = getTextFrom(response);
		return body.contains(String.format("<title>%s</title>", title));
	}

	public String registerExistedAccountWithApiUsing(String email)
			throws JsonSyntaxException, IOException, IllegalStateException {
		// Use Fluent API
		String pageContent = createFluentPostRequestWith(email, app.getProperty("web.baseUrl"));
		JsonElement parsed = new JsonParser().parse(pageContent);
		JsonElement key = parsed.getAsJsonObject().get("errors");
		return key.getAsJsonArray().getAsString();
	}

	public boolean createEmailWith(String email) throws IOException, InterruptedException, URISyntaxException {
		URIBuilder postRequest = new URIBuilder(app.getProperty("web.emailGenerator") + "/ajax.php");
		// query string params
		postRequest.setParameter("f", "set_email_user");
		// request header
		String[][] headerParams = createHeaderParamsWith(app.getProperty("web.emailGeneratorApiToken"));
		// Form Data
		String[][] bodyParams = getBodyParamsUsingEmailNameWith(email);
		HttpPost post = createPostRequestWithParams(postRequest.toString(), headerParams);
		post.setEntity(new UrlEncodedFormEntity(createHttpBodyParamsWith(bodyParams)));
		CloseableHttpResponse response = httpClient.execute(post, this.context);
		isHttpStatusCodeOK(response);
		String json = getTextFrom(response);
		JsonElement parsed = new JsonParser().parse(json);
		String emailAddrKey = parsed.getAsJsonObject().get("email_addr").getAsString();
		JsonElement authKey = parsed.getAsJsonObject().get("auth");
		boolean statusKey = authKey.getAsJsonObject().get("success").getAsBoolean();
		JsonArray errorCodeKey = authKey.getAsJsonObject().get("error_codes").getAsJsonArray();
		if (statusKey == true & errorCodeKey.size() == 0)
			return emailAddrKey.equals(email);
		else
			return false;
	}

	// TODO IT IS A STUB
	// THIS LOGIC DOESNT CHECK VERIFICATION LINK
	// CHECKS ONLY FOR ABSENCE OF ERRORS
	public boolean verifyActivationLink(String email, String link) throws IOException, URISyntaxException {
		URIBuilder getRequest = new URIBuilder(app.getProperty("web.emailGenerator") + "/ajax.php");
		// query string params
		addStringParamsUsingEmailNameWith(email, getRequest, String.valueOf(timeStamp.getTime()));
		String[][] headerParams = createHeaderParamsWith(app.getProperty("web.emailGeneratorApiToken"));
		HttpGet get = createGetRequestWithParams(getRequest.toString(), headerParams);
		CloseableHttpResponse response = httpClient.execute(get, this.context);
		isHttpStatusCodeOK(response);
		String json = getTextFrom(response);
		JsonElement parsed = new JsonParser().parse(json);
		String emailAddrKey = parsed.getAsJsonObject().get("email").getAsString();
		JsonElement authKey = parsed.getAsJsonObject().get("auth");
		boolean statusKey = authKey.getAsJsonObject().get("success").getAsBoolean();
		JsonArray errorCodeKey = authKey.getAsJsonObject().get("error_codes").getAsJsonArray();
		return isContained(email, link, parsed, emailAddrKey, statusKey, errorCodeKey);
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
		JsonElement parsed = new JsonParser().parse(parsePureHtmlWithRegExUsing("^.*(\\[.*\\])\\;$", json));
		return isAdded(products, parsed);
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

	public boolean searchForProduct(Products product) throws IOException {
		// Use fluent API
		String prod = product.getProductName();
		String response = createFluentPostUsingProductInfoWith(prod, app.getProperty("web.searchUrl"),
				timeStamp.getTime());
		return response.contains(prod.replaceAll("-", "").replaceAll(" ", "-").toLowerCase());

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
