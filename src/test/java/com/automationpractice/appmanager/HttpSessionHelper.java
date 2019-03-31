package com.automationpractice.appmanager;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Random;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.fluent.Form;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;

import com.automationpractice.model.LigalCredentials;
import com.automationpractice.model.Products;
import com.automationpractice.model.RegistrationFormData;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

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

	// loginWith Method
	protected String[][] getBodyParamsWith(LigalCredentials credentials) {
		String[][] bodyParams = { { "email", credentials.getEmail() }, { "passwd", credentials.getPassword() },
				{ "back", "my-account" }, { "SubmitLogin", "" } };
		return bodyParams;
	}

	// signUpWith Method

	protected String[][] getBodyParamsWith(String email) {
		String[][] bodyParams = { { "controller", "authentication" }, { "SubmitCreate", "1" }, { "ajax", "true" },
				{ "email_create", email }, { "back", "my-account" }, { "token", "ce65cefcbafad255f0866d3b32d32058" } };
		return bodyParams;
	}

	// registerWith Method

	protected String[][] createHeaderParamsWithReferer() {
		String[][] headerParams = { { "Accept",
				"text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3" },
				{ "Host", "automationpractice.com" }, { "Cache-Control", "max-age=0" },
				{ "Origin", "http://automationpractice.com" }, { "Upgrade-Insecure-Requests", "1" },
				{ "Content-Type", "application/x-www-form-urlencoded" },
				{ "User-Agent",
						"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.86 Safari/537.36" },
				{ "Referer", "http://automationpractice.com/index.php?controller=authentication&back=my-account" } };
		return headerParams;
	}

	protected String[][] getBodyParamsWith(RegistrationFormData formData) {
		String[][] bodyParams = { { "customer_firstname", formData.getFirstName() },
				{ "customer_lastname", formData.getLastName() }, { "email", formData.getEmail() },
				{ "passwd", formData.getPassword() }, { "firstname", formData.getFirstName() },
				{ "lastname", formData.getLastName() }, { "days", "" }, { "months", "" }, { "years", "" },
				{ "company", "" }, { "address1", formData.getAddress() }, { "address2", "" },
				{ "city", formData.getCityName() }, { "id_state", formData.getState() },
				{ "postcode", formData.getPostCode() }, { "id_country", "21" },
				{ "phone_mobile", formData.getPhoneNumber() }, { "alias", "My address" }, { "dni", "" },
				{ "email_create", "1" }, { "is_new_customer", "1" }, { "back", "my-account" },
				{ "submitAccount", "" } };
		return bodyParams;
	}

	// createEmailWith Method

	protected String[][] getBodyParamsUsingEmailNameWith(String email) {
		String[][] bodyParams = { { "email_user", email.split("@")[0] }, { "lang", "en" },
				{ "site", "guerrillamail.com" }, { "in", "Set cancel" } };
		return bodyParams;
	}

	protected String[][] createHeaderParamsWithAuthorizationUsing(String property) {
		String[][] headerParams = { { "Accept", "application/json, text/javascript, */*; q=0.01" },
				{ "Authorization", property }, { "Content-Type", "application/x-www-form-urlencoded; charset=UTF-8" },
				{ "X-Requested-With", "XMLHttpRequest" } };
		return headerParams;
	}

	// addProductToCart Method

	protected String[][] createHeaderParamsUsingCookieWith(String cookieValue) {
		String[][] headerParams = { { "Accept", "application/json, text/javascript, */*; q=0.01" },
				{ "Content-Type", "application/x-www-form-urlencoded; charset=UTF-8" }, { "cache-control", "no-cache" },
				{ "X-Requested-With", "XMLHttpRequest" }, { "Cookie", cookieValue } };
		return headerParams;
	}

	// loginWithErrorHandling Method
	protected String createFluentPostRequestUsingEmailWith(String email, String password, String property)
			throws ClientProtocolException, IOException {
		String content = Request
				.Post(property + "index.php?controller=authentication").bodyForm(Form.form().add("email", email)
						.add("passwd", password).add("back", "").add("SubmitLogin", "").build())
				.execute().returnContent().asString();
		return content;
	}

	// requestForRegisterExistedAccountWithApiUsing Method
	protected String createFluentPostRequestWith(String email, String property)
			throws ClientProtocolException, IOException {
		String pageContent = Request.Post(property + "index.php?controller=authentication")
				.bodyForm(Form.form().add("controller", "authentication").add("SubmitCreate", "1").add("ajax", "true")
						.add("email_create", email).add("back", "my-account").build())
				.execute().returnContent().asString();
		return pageContent;
	}

	// searchForProduct Method
	protected String createFluentPostUsingProductInfoWith(String prod, String property, long timestamp)
			throws ClientProtocolException, IOException {
		String response = Request.Get(
				property + prod.replaceAll(" ", "+") + "&limit=10&timestamp=" + timestamp + "&ajaxSearch=1&id_lang=1")
				.execute().returnContent().asString();
		return response;
	}

	// verifyActivationLink Method and createEmailWith Method

	protected void addStringParamsUsingEmailNameWith(String email, URIBuilder getRequest, String timestamp) {
		getRequest.setParameter("f", "get_email_list").setParameter("offset", "0")
				.setParameter("site", "guerrillamail.com").setParameter("in", email.split("@")[0])
				.setParameter("_", timestamp);
	}

	// addProductToWishListWithNoTokenUsing Method and getProductsFromCart Method
	protected void addStringParamsUsingPdpInfoWith(Products products, LigalCredentials credentials,
			URIBuilder getRequest, String rand, String timestamp) {
		getRequest.setParameter("rand", rand).setParameter("action", "add")
				.setParameter("id_product", String.valueOf(products.getId()))
				.setParameter("quantity", String.valueOf(products.getQuantity()))
				.setParameter("token", String.valueOf(credentials.getToken())).setParameter("id_product_attribute", "1")
				.setParameter("_", timestamp);
	}

	protected void addStringParamsUsingWishListInfoWith(URIBuilder getRequest) {
		getRequest.setParameter("fc", "module").setParameter("module", "blockwishlist").setParameter("controller",
				"mywishlist");
	}

	protected void addStringParamsUsingWishListWithAddedProductInfoWith(URIBuilder getRequest, int rand,
			String wishListId, Timestamp timeStamp) {
		try {
			if (!wishListId.isEmpty() & wishListId.length() != 0) {
				getRequest.setParameter("fc", "module").setParameter("module", "blockwishlist")
						.setParameter("controller", "mywishlist").setParameter("rand", String.valueOf(rand))
						.setParameter("deleted", "1").setParameter("id_wishlist", wishListId)
						.setParameter("_", String.valueOf(timeStamp));
				return;
			}
		} catch (IllegalArgumentException e) {
			httpSessionlogger.error(e.toString());
		}
	}

	protected String[][] createHeaderParamsUsingPdpIndoWith(Products products, String cookieValue) {
		String[][] headerParams = { { "Accept", "application/json, text/javascript, */*; q=0.01" },
				{ "Content-Type", "application/x-www-form-urlencoded; charset=UTF-8" }, { "Cookie", cookieValue },
				{ "Host", "automationpractice.com" }, { "Referer", "http://automationpractice.com/index.php?id_product="
						+ String.valueOf(products.getId()) + "&controller=product" },
				{ "X-Requested-With", "XMLHttpRequest" } };
		return headerParams;
	}

	protected String createFluentPostRequestUsingProductInfoWith(Products newProduct, String property, int rand,
			String cookieValue) throws ClientProtocolException, IOException {
		String json = Request.Post(property + "index.php?rand=" + rand)
				.addHeader("Accept", "application/json, text/javascript, */*; q=0.01")
				.addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8")
				.addHeader("X-Requested-With", "XMLHttpRequest").addHeader("Cookie", cookieValue)
				.bodyForm(Form.form().add("controller", "cart").add("add", "1").add("ajax", "true")
						.add("qty", String.valueOf(newProduct.getQuantity()))
						.add("id_product", String.valueOf(newProduct.getId())).build())
				.execute().returnContent().asString();
		return json;
	}

	protected String createFluentPostRequestUsingTokenWith(String token, String property, int rand, String cookie)
			throws ClientProtocolException, IOException {
		String json = Request.Post(property + "index.php?rand=" + rand)
				.addHeader("Accept", "application/json, text/javascript, */*; q=0.01")
				.addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8")
				.addHeader("X-Requested-With", "XMLHttpRequest").addHeader("Cookie", cookie)
				.bodyForm(Form.form().add("controller", "cart").add("ajax", "true").add("token", token).build())
				.execute().returnContent().asString();
		return json;
	}

	protected void createFluentPostRequestUsingTokenWith(String token, String id, String ipa, String property, int rand,
			String cookie) throws ClientProtocolException, IOException {
		Request.Post(property + "index.php?rand=" + rand)
				.addHeader("Accept", "application/json, text/javascript, */*; q=0.01")
				.addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8")
				.addHeader("X-Requested-With", "XMLHttpRequest").addHeader("Cookie", cookie)
				.bodyForm(Form.form().add("controller", "cart").add("delete", "1").add("id_product", id).add("ipa", ipa)
						.add("token", token).add("ajax", "true").build())
				.execute().returnContent().asString();
	}

	protected String[][] createBodyParamsForAddProductToCartMethod(String id, String quantity, String token) {
		String[][] bodyParams = { { "controller", "cart" }, { "add", "1" }, { "ajax", "true" }, { "qty", quantity },
				{ "id_product", id }, { "token", token } };
		return bodyParams;
	}

	protected boolean isWishListEmpty(JsonElement addedProducts) {
		try {
			boolean wishlistProductsIds = !addedProducts.isJsonArray() ? addedProducts.getAsJsonObject().getAsBoolean()
					: false;
			return !wishlistProductsIds;
		} catch (JsonSyntaxException e) {
			httpSessionlogger.error(e.toString());
		}
		return false;
	}

	protected boolean isAdded(Products products, JsonElement parsed) {
		try {
			if (!parsed.getAsJsonArray().isJsonNull() & parsed.getAsJsonArray().size() != 0) {
				for (JsonElement jSo : parsed.getAsJsonArray()) {
					if (jSo.getAsJsonObject().get("id_product").getAsString().equals(String.valueOf(products.getId()))
							& jSo.getAsJsonObject().get("quantity").getAsString()
									.equals(String.valueOf(products.getQuantity()))) {
						return jSo.getAsJsonObject().get("name").getAsString().toLowerCase()
								.equals(String.valueOf(products.getProductName()));
					}
				}
			} else
				return false;
		} catch (JsonSyntaxException e) {
			httpSessionlogger.error(e.toString());
		}
		return false;
	}

	protected boolean isContained(String email, String link, JsonElement parsed, String emailAddrKey, boolean statusKey,
			JsonArray errorCodeKey) {
		try {
			if (statusKey == true & errorCodeKey.size() == 0) {
				JsonArray inbox = parsed.getAsJsonObject().get("list").getAsJsonArray();
				for (JsonElement jSo : inbox) {
					if (jSo.getAsJsonObject().get("mail_body").getAsString().contains(link)) {
						return emailAddrKey.equals(email);
					}
				}
			}
		} catch (JsonSyntaxException e) {
			httpSessionlogger.error(e.toString());
		} catch (IllegalArgumentException e) {
			httpSessionlogger.error(e.toString());
		}
		return false;
	}

	protected Products isAdded(Products newProduct, JsonArray jsonArray) {
		try {
			for (JsonElement jSo : jsonArray) {
				if (jSo.getAsJsonObject().get("id").getAsInt() == newProduct.getId()) {
					return new Gson().fromJson(jSo.getAsJsonObject(), new TypeToken<Products>() {
					}.getType());
				}
			}
		} catch (JsonSyntaxException e) {
			httpSessionlogger.error(e.toString());
		} catch (IllegalArgumentException e) {
			httpSessionlogger.error(e.toString());
		}
		return null;
	}

	protected void cleanUpUsing(String token, JsonElement parsed, JsonElement key, String property, int rand,
			String webCookie) {
		try {
			if (!key.isJsonNull() && key.isJsonPrimitive() && key.getAsInt() > 0) {
				JsonArray jsonArray = parsed.getAsJsonObject().getAsJsonArray("products");
				for (JsonElement jSo : jsonArray) {
					String id = jSo.getAsJsonObject().get("id").getAsString();
					String ipa = jSo.getAsJsonObject().get("idCombination").getAsString();
					createFluentPostRequestUsingTokenWith(token, id, ipa, property, rand, webCookie);

				}
			}
		} catch (JsonSyntaxException e) {
			httpSessionlogger.error(e.toString());
		} catch (ClientProtocolException e) {
			httpSessionlogger.error(e.toString());
		} catch (IllegalArgumentException e) {
			httpSessionlogger.error(e.toString());
		} catch (IOException e) {
			httpSessionlogger.error(e.toString());
		}
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

	public String getToken() {
		return getApp().getProperty("web.token");
	}

	public String getCookieName() {
		return getApp().getProperty("web.cookies");
	}

	public CloseableHttpClient getHttpClient() {
		return httpClient;
	}

	public void setHttpClient(CloseableHttpClient httpClient) {
		this.httpClient = httpClient;
	}

	public HttpClientContext getContext() {
		return context;
	}

	public void setContext(HttpClientContext context) {
		this.context = context;
	}

	public CookieStore getCookieStore() {
		return cookieStore;
	}

	public void setCookieStore(CookieStore cookieStore) {
		this.cookieStore = cookieStore;
	}

	public ApplicationManager getApp() {
		return app;
	}

	public void setApp(ApplicationManager app) {
		this.app = app;
	}

	public Timestamp getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(Timestamp timeStamp) {
		this.timeStamp = timeStamp;
	}

	public String getWebCookie() {
		return webCookie;
	}

	public void setWebCookie(String webCookie) {
		this.webCookie = webCookie;
	}

	public int getRand() {
		return rand;
	}

	public void setRand(int rand) {
		this.rand = rand;
	}

}