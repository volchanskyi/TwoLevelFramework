package com.automationpractice.appmanager;

import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.Timestamp;
import java.util.Random;
import java.util.Set;

import org.apache.http.client.CookieStore;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.fluent.Form;
import org.apache.http.client.fluent.Request;
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
import com.automationpractice.model.PDP;
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
		String[][] bodyParams = { { "email", credentials.getEmail() }, { "passwd", credentials.getPassword() },
				{ "back", "my-account" }, { "SubmitLogin", "" } };
		HttpPost post = createPostRequestWithParams(postRequest.toString(), headerParams);
		post.setEntity(new UrlEncodedFormEntity(createHttpBodyParamsWith(bodyParams)));
		CloseableHttpResponse response = this.httpClient.execute(post);
		String body = getTextFrom(response);
		return body.contains(String.format("<title>%s</title>", pageTitle));
	}

	public boolean loginWithErrorHandling(String email, String password, String errorMsg) throws IOException {
		String content = Request
				.Post(app.getProperty("web.baseUrl") + "index.php?controller=authentication").bodyForm(Form.form()
						.add("email", email).add("passwd", password).add("back", "").add("SubmitLogin", "").build())
				.execute().returnContent().asString();
		return content.contains(errorMsg);

	}

	// TODO refactor token
	public boolean signUpWith(String email) throws IOException {
		HttpPost post = new HttpPost(app.getProperty("web.baseUrl") + "index.php");
		// Form Data
		String[][] bodyParams = { { "controller", "authentication" }, { "SubmitCreate", "1" }, { "ajax", "true" },
				{ "email_create", email }, { "back", "my-account" }, { "token", "ce65cefcbafad255f0866d3b32d32058" } };
		post.setEntity(new UrlEncodedFormEntity(createHttpBodyParamsWith(bodyParams)));
		CloseableHttpResponse response = httpClient.execute(post);
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
		String[][] headerParams = { { "Accept", "application/json, text/javascript" },
				{ "Content-Type", "application/x-www-form-urlencoded" } };
		String[][] bodyParams = { { "customer_firstname", fName }, { "customer_lastname", lName },
				{ "passwd", password }, { "firstname", fName }, { "lastname", lName }, { "email", email },
				{ "days", "" }, { "months", "" }, { "years", "" }, { "company", "" }, { "address1", address },
				{ "address2", "" }, { "city", city }, { "id_state", state }, { "postcode", postcode },
				{ "id_country", "21" }, { "phone_mobile", phone }, { "alias", "My address" }, { "back", "my-account" },
				{ "dni", "" }, { "email_create", "1" }, { "is_new_customer", "1" }, { "submitAccount", "" } };
		HttpPost post = createPostRequestWithParams(postRequest.toString(), headerParams);
		post.setEntity(new UrlEncodedFormEntity(createHttpBodyParamsWith(bodyParams)));
		CloseableHttpResponse response = httpClient.execute(post);
		String body = getTextFrom(response);
		return body.contains(String.format("<title>%s</title>", title));
	}

	public String registerExistedAccountWithApiUsing(String email)
			throws JsonSyntaxException, IOException, IllegalStateException {
		// Use Fluent API
		String pageContent = Request.Post(app.getProperty("web.baseUrl") + "index.php?controller=authentication")
				.bodyForm(Form.form().add("controller", "authentication").add("SubmitCreate", "1").add("ajax", "true")
						.add("email_create", email).add("back", "my-account").build())
				.execute().returnContent().asString();
		JsonElement parsed = new JsonParser().parse(pageContent);
		JsonElement key = parsed.getAsJsonObject().get("errors");
		return key.getAsJsonArray().getAsString();
	}

	public boolean createEmailWith(String email) throws IOException, InterruptedException, URISyntaxException {
		URIBuilder postRequest = new URIBuilder(app.getProperty("web.emailGenerator") + "/ajax.php");
		// query string params
		postRequest.setParameter("f", "set_email_user");
		// request header
		String[][] headerParams = { { "Accept", "application/json, text/javascript, */*; q=0.01" },
				{ "Authorization", "ApiToken 6cecc4da415fcd63b23a6993cbf429ea620b086a0adaefe73f3143b3cdf086ef" },
				{ "Content-Type", "application/x-www-form-urlencoded; charset=UTF-8" },
				{ "X-Requested-With", "XMLHttpRequest" } };
		// Form Data
		String[][] bodyParams = { { "email_user", email.split("@")[0] }, { "lang", "en" },
				{ "site", "guerrillamail.com" }, { "in", "Set cancel" } };
		HttpPost post = createPostRequestWithParams(postRequest.toString(), headerParams);
		post.setEntity(new UrlEncodedFormEntity(createHttpBodyParamsWith(bodyParams)));
		CloseableHttpResponse response = httpClient.execute(post, this.context);
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
		getRequest.setParameter("f", "get_email_list").setParameter("offset", "0")
				.setParameter("site", "guerrillamail.com").setParameter("in", email.split("@")[0])
				.setParameter("_", String.valueOf(timeStamp.getTime()));
		// request header
		String[][] headerParams = { { "Accept", "application/json, text/javascript, */*; q=0.01" },
				{ "Authorization", "ApiToken 6cecc4da415fcd63b23a6993cbf429ea620b086a0adaefe73f3143b3cdf086ef" },
				{ "Content-Type", "application/x-www-form-urlencoded; charset=UTF-8" },
				{ "X-Requested-With", "XMLHttpRequest" } };
		HttpGet get = createGetRequestWithParams(getRequest.toString(), headerParams);
		CloseableHttpResponse response = httpClient.execute(get, this.context);
		String json = getTextFrom(response);
		JsonElement parsed = new JsonParser().parse(json);
		String emailAddrKey = parsed.getAsJsonObject().get("email").getAsString();
		JsonElement authKey = parsed.getAsJsonObject().get("auth");
		boolean statusKey = authKey.getAsJsonObject().get("success").getAsBoolean();
		JsonArray errorCodeKey = authKey.getAsJsonObject().get("error_codes").getAsJsonArray();
		if (statusKey == true & errorCodeKey.size() == 0) {
			JsonArray inbox = parsed.getAsJsonObject().get("list").getAsJsonArray();
			for (JsonElement jSo : inbox) {
				if (jSo.getAsJsonObject().get("mail_body").getAsString().contains(link)) {
					return emailAddrKey.equals(email);
				}
			}
		}
		return false;
	}

	public Set<Products> addProductToCart(String id, String quantity, String token)
			throws JsonSyntaxException, IOException, IllegalStateException, URISyntaxException {
		URIBuilder postRequest = new URIBuilder(app.getProperty("web.baseUrl") + "index.php");
		// query string params
		postRequest.setParameter("rand", String.valueOf(this.rand));
		// request header
		String[][] headerParams = { { "Accept", "application/json, text/javascript, */*; q=0.01" },
				{ "Content-Type", "application/x-www-form-urlencoded; charset=UTF-8" },
				{ "X-Requested-With", "XMLHttpRequest" }, { "Cookie", getCookieValue(cookieStore, this.webCookie) } };
		// Form Data
		String[][] bodyParams = { { "controller", "cart" }, { "add", "1" }, { "ajax", "true" }, { "qty", quantity },
				{ "id_product", id }, { "token", token } };
		HttpPost post = createPostRequestWithParams(postRequest.toString(), headerParams);
		post.setEntity(new UrlEncodedFormEntity(createHttpBodyParamsWith(bodyParams)));
		CloseableHttpResponse response = httpClient.execute(post, this.context);
		String json = getTextFrom(response);
		JsonElement parsed = new JsonParser().parse(json);
		JsonElement key = parsed.getAsJsonObject().get("products");
		return new Gson().fromJson(key, new TypeToken<Set<Products>>() {
		}.getType());
	}

	public boolean navigateToPdpUsing(PDP pdp)
			throws JsonSyntaxException, IOException, IllegalStateException, URISyntaxException {
		URIBuilder getRequest = new URIBuilder(app.getProperty("web.baseUrl") + "index.php");
		// query string params
		getRequest.setParameter("id_product", String.valueOf(pdp.getId())).setParameter("controller", "product");
		// request header
		String[][] headerParams = { { "Cookie", getCookieValue(cookieStore, this.webCookie) } };
		HttpGet get = createGetRequestWithParams(getRequest.toString(), headerParams);
		CloseableHttpResponse response = httpClient.execute(get, this.context);
		String body = getTextFrom(response);
		return body.toLowerCase().contains(String.format("<title>%s</title>", pdp.getProductName() + " - my store"));
	}

	public String addProductToWishListWithNoTokenUsing(PDP pdp) throws IOException, URISyntaxException {
		URIBuilder getRequest = new URIBuilder(app.getProperty("web.baseUrl") + "modules/blockwishlist/cart.php");
		// query string params
		getRequest.setParameter("rand", String.valueOf(this.rand)).setParameter("action", "add")
				.setParameter("id_product", String.valueOf(pdp.getId()))
				.setParameter("quantity", String.valueOf(pdp.getQuantity()))
				.setParameter("token", String.valueOf(pdp.getToken())).setParameter("id_product_attribute", "1")
				.setParameter("_", String.valueOf((timeStamp.getTime())));
		// request header
		String[][] headerParams = { { "Accept", "application/json, text/javascript, */*; q=0.01" },
				{ "Content-Type", "application/x-www-form-urlencoded; charset=UTF-8" },
				{ "Cookie", getCookieValue(cookieStore, this.webCookie) }, { "Host", "automationpractice.com" },
				{ "Referer", "http://automationpractice.com/index.php?id_product=" + String.valueOf(pdp.getId())
						+ "&controller=product" },
				{ "X-Requested-With", "XMLHttpRequest" } };
		HttpGet get = createGetRequestWithParams(getRequest.toString(), headerParams);
		CloseableHttpResponse response = httpClient.execute(get, this.context);
		return getTextFrom(response);
	}

	public Set<Products> getProductsFromCart(String token) throws IOException, URISyntaxException {
		URIBuilder postRequest = new URIBuilder(app.getProperty("web.baseUrl") + "index.php");
		// query string params
		postRequest.setParameter("rand", String.valueOf(this.rand));
		// request header
		String[][] headerParams = { { "Accept", "application/json, text/javascript, */*; q=0.01" },
				{ "Content-Type", "application/x-www-form-urlencoded; charset=UTF-8" },
				{ "X-Requested-With", "XMLHttpRequest" }, { "Cookie", getCookieValue(cookieStore, this.webCookie) } };
		// Form Data
		String[][] bodyParams = { { "controller", "cart" }, { "ajax", "true" }, { "token", token } };
		HttpPost post = createPostRequestWithParams(postRequest.toString(), headerParams);
		post.setEntity(new UrlEncodedFormEntity(createHttpBodyParamsWith(bodyParams)));
		CloseableHttpResponse response = httpClient.execute(post, this.context);
		String json = getTextFrom(response);
		JsonElement parsed = new JsonParser().parse(json);
		JsonElement key = parsed.getAsJsonObject().get("products");
		return new Gson().fromJson(key, new TypeToken<Set<Products>>() {
		}.getType());
	}

	public Products addProductToCart(Products newProduct) throws IOException {
		// Use fluent API
		String json = Request.Post(app.getProperty("web.baseUrl") + "index.php?rand=" + this.rand)
				.addHeader("Accept", "application/json, text/javascript, */*; q=0.01")
				.addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8")
				.addHeader("X-Requested-With", "XMLHttpRequest")
				.addHeader("Cookie", getCookieValue(cookieStore, this.webCookie))
				.bodyForm(Form.form().add("controller", "cart").add("add", "1").add("ajax", "true")
						.add("qty", String.valueOf(newProduct.getQuantity()))
						.add("id_product", String.valueOf(newProduct.getId())).build())
				.execute().returnContent().asString();
		JsonElement parsed = new JsonParser().parse(json);
		JsonArray jsonArray = parsed.getAsJsonObject().getAsJsonArray("products");
		for (JsonElement jSo : jsonArray) {
			if (jSo.getAsJsonObject().get("id").getAsInt() == newProduct.getId()) {
				return new Gson().fromJson(jSo.getAsJsonObject(), new TypeToken<Products>() {
				}.getType());
			}
		}
		return null;

	}

	public void cleanUpCart(String token) throws IOException {
		// Use fluent API
		String json = Request.Post(app.getProperty("web.baseUrl") + "index.php?rand=" + this.rand)
				.addHeader("Accept", "application/json, text/javascript, */*; q=0.01")
				.addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8")
				.addHeader("X-Requested-With", "XMLHttpRequest").addHeader("Cookie", this.webCookie)
				.bodyForm(Form.form().add("controller", "cart").add("ajax", "true").add("token", token).build())
				.execute().returnContent().asString();
		JsonElement parsed = new JsonParser().parse(json);
		JsonElement key = parsed.getAsJsonObject().get("nbTotalProducts");
		if (!key.isJsonNull() && key.isJsonPrimitive() && key.getAsInt() > 0) {
			JsonArray jsonArray = parsed.getAsJsonObject().getAsJsonArray("products");
			for (JsonElement jSo : jsonArray) {
				String id = jSo.getAsJsonObject().get("id").getAsString();
				String ipa = jSo.getAsJsonObject().get("idCombination").getAsString();
				Request.Post(app.getProperty("web.baseUrl") + "index.php?rand=" + this.rand)
						.addHeader("Accept", "application/json, text/javascript, */*; q=0.01")
						.addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8")
						.addHeader("X-Requested-With", "XMLHttpRequest").addHeader("Cookie", this.webCookie)
						.bodyForm(Form.form().add("controller", "cart").add("delete", "1").add("id_product", id)
								.add("ipa", ipa).add("token", token).add("ajax", "true").build())
						.execute().returnContent().asString();

			}
		}
	}

	public boolean searchForProduct(Products product) throws IOException {
		// Use fluent API
		String prod = product.getProductName();
		String response = Request.Get(app.getProperty("web.searchUrl") + prod.replaceAll(" ", "+")
				+ "&limit=10&timestamp=" + timeStamp.getTime() + "&ajaxSearch=1&id_lang=1").execute().returnContent()
				.asString();
		return response.contains(prod.replaceAll("-", "").replaceAll(" ", "-").toLowerCase());

	}

	public boolean isLoggedInAs(LigalCredentials credentials) throws IOException, URISyntaxException {
		URIBuilder getRequest = new URIBuilder(app.getProperty("web.baseUrl") + "/index.php");
		// query string params
		getRequest.setParameter("controller", "my-account");
		// request header
		String[][] headerParams = { { "Host", "automationpractice.com" } };
		HttpGet get = createGetRequestWithParams(getRequest.toString(), headerParams);
		CloseableHttpResponse response = this.httpClient.execute(get);
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
