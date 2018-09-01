package com.automationpractice.appmanager;

import java.io.IOException;
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
	protected static final Products PRODUCTS = new Products();
	private Timestamp timeStamp = new Timestamp(System.currentTimeMillis());
	private int rand = new Random().nextInt(99999998) + 1;
	private String webCookie;

	public HttpSession(ApplicationManager app) {
		this.app = app;
		this.context.setCookieStore(cookieStore);
		// Enable following REDIRECTIONS on POST
		httpClient = HttpClients.custom().setRedirectStrategy(new LaxRedirectStrategy()).build();
	}

	public boolean loginWith(LigalCredentials credentials, String pageTitle) throws IOException {
		HttpPost post = new HttpPost(app.getProperty("web.baseUrl") + "index.php?controller=authentication");
		String[][] bodyParams = { { "email", credentials.getEmail() }, { "passwd", credentials.getPassword() },
				{ "back", "my-account" }, { "SubmitLogin", "" } };
		post.setEntity(new UrlEncodedFormEntity(createHttpBodyParamsWith(bodyParams)));
		CloseableHttpResponse response = httpClient.execute(post);
		// this.webCookie = response.getFirstHeader("Set-Cookie").getValue();
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

	public boolean signUpWith(String email) throws IOException {
		HttpPost post = new HttpPost(app.getProperty("web.baseUrl") + "index.php");
		String[][] bodyParams = { { "controller", "authentication" }, { "SubmitCreate", "1" }, { "ajax", "true" },
				{ "email_create", email }, { "back", "my-account" }, { "token", "ce65cefcbafad255f0866d3b32d32058" } };
		post.setEntity(new UrlEncodedFormEntity(createHttpBodyParamsWith(bodyParams)));
		CloseableHttpResponse response = httpClient.execute(post);
		String body = getTextFrom(response);
		return body.contains(String.format("<h1 class=\\\"page-heading\\\">%s<\\/h1>", "Create an account"));
	}

	public boolean registerWith(String fName, String lName, String password, String address, String city,
			String postcode, String state, String phone, String title, String email) throws IOException {
		String postRequest = app.getProperty("web.baseUrl")
				+ "index.php?controller=authentication&back=my-account#account-creation";
		String[][] headerParams = { { "Accept", "application/json, text/javascript" },
				{ "Content-Type", "application/x-www-form-urlencoded" } };
		String[][] bodyParams = { { "customer_firstname", fName }, { "customer_lastname", lName },
				{ "passwd", password }, { "firstname", fName }, { "lastname", lName }, { "email", email },
				{ "days", "" }, { "months", "" }, { "years", "" }, { "company", "" }, { "address1", address },
				{ "address2", "" }, { "city", city }, { "id_state", state }, { "postcode", postcode },
				{ "id_country", "21" }, { "phone_mobile", phone }, { "alias", "My address" }, { "back", "my-account" },
				{ "dni", "" }, { "email_create", "1" }, { "is_new_customer", "1" }, { "submitAccount", "" } };
		HttpPost post = createPostRequestWithParams(postRequest, headerParams);
		post.setEntity(new UrlEncodedFormEntity(createHttpBodyParamsWith(bodyParams)));
		CloseableHttpResponse response = httpClient.execute(post);
		String body = getTextFrom(response);
		return body.contains(String.format("<title>%s</title>", title));
	}

	public String registerExistedAccountWithApiUsing(String email)
			throws JsonSyntaxException, IOException, IllegalStateException {
		String pageContent = Request.Post(app.getProperty("web.baseUrl") + "index.php?controller=authentication")
				.bodyForm(Form.form().add("controller", "authentication").add("SubmitCreate", "1").add("ajax", "true")
						.add("email_create", email).add("back", "my-account").build())
				.execute().returnContent().asString();
		JsonElement parsed = new JsonParser().parse(pageContent);
		JsonElement key = parsed.getAsJsonObject().get("errors");
		return key.getAsJsonArray().getAsString();
	}

	public boolean createEmailWith(String email) throws IOException, InterruptedException {
		String getRequest = app.getProperty("web.mailinator") + "v2/inbox.jsp?zone=public&query=" + email;
		String[][] headerParams = { { ":authority", "www.mailinator.com" }, { ":method", "GET" },
				{ ":scheme", "https" }, { ":path", "/v2/inbox.jsp?zone=public&query=" + email } };
		HttpGet get = createGetRequestWithParams(getRequest, headerParams);
		CloseableHttpResponse response = httpClient.execute(get);
		String body = getTextFrom(response);
		String inboxMsg = "[ This Inbox channel is currently Empty ]";
		return body.contains(String.format("%s", inboxMsg));
	}

	public Set<Products> addProductToCart(String id, String quantity, String token)
			throws JsonSyntaxException, IOException, IllegalStateException {
		String postRequest = app.getProperty("web.baseUrl") + "index.php?rand=" + this.rand;
		String[][] headerParams = { { "Accept", "application/json, text/javascript, */*; q=0.01" },
				{ "Content-Type", "application/x-www-form-urlencoded; charset=UTF-8" },
				{ "X-Requested-With", "XMLHttpRequest" }, { "Cookie", getCookieValue(cookieStore, this.webCookie) } };
		String[][] bodyParams = { { "controller", "cart" }, { "add", "1" }, { "ajax", "true" }, { "qty", quantity },
				{ "id_product", id }, { "token", token } };
		HttpPost post = createPostRequestWithParams(postRequest, headerParams);
		post.setEntity(new UrlEncodedFormEntity(createHttpBodyParamsWith(bodyParams)));
		CloseableHttpResponse response = httpClient.execute(post, this.context);
		String json = getTextFrom(response);
		JsonElement parsed = new JsonParser().parse(json);
		JsonElement key = parsed.getAsJsonObject().get("products");
		return new Gson().fromJson(key, new TypeToken<Set<Products>>() {
		}.getType());
	}

	public Set<Products> getProductsFromCart(String token) throws IOException {
		String postRequest = app.getProperty("web.baseUrl") + "index.php?rand=" + this.rand;
		String[][] headerParams = { { "Accept", "application/json, text/javascript, */*; q=0.01" },
				{ "Content-Type", "application/x-www-form-urlencoded; charset=UTF-8" },
				{ "X-Requested-With", "XMLHttpRequest" }, { "Cookie", getCookieValue(cookieStore, this.webCookie) } };
		String[][] bodyParams = { { "controller", "cart" }, { "ajax", "true" }, { "token", token } };
		HttpPost post = createPostRequestWithParams(postRequest, headerParams);
		post.setEntity(new UrlEncodedFormEntity(createHttpBodyParamsWith(bodyParams)));
		CloseableHttpResponse response = httpClient.execute(post, this.context);
		String json = getTextFrom(response);
		JsonElement parsed = new JsonParser().parse(json);
		JsonElement key = parsed.getAsJsonObject().get("products");
		return new Gson().fromJson(key, new TypeToken<Set<Products>>() {
		}.getType());
	}

	public Products addProductToCart(Products newProduct) throws IOException {
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
		String prod = product.getName().toString();
		String response = Request.Get(app.getProperty("web.searchUrl") + prod.replaceAll(" ", "+")
				+ "&limit=10&timestamp=" + timeStamp.getTime() + "&ajaxSearch=1&id_lang=1").execute().returnContent()
				.asString();
		return response.contains(prod.replaceAll(" ", "-"));

	}

	public boolean isLoggedInAs(LigalCredentials credentials) throws IOException {
		HttpGet get = new HttpGet(app.getProperty("web.baseUrl") + "/index.php?controller=my-account");
		CloseableHttpResponse response = httpClient.execute(get);
		String body = getTextFrom(response);
		return body.contains(String.format("<span>%s</span>", credentials.getName()));
	}

	// TODO implement link verification
	public boolean verifyActivationLink(String link) throws IOException {

		return false;
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
