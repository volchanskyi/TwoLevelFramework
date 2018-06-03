package com.automationpractice.appmanager;

import java.io.IOException;
import java.net.URI;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.fluent.Executor;
import org.apache.http.client.fluent.Form;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.client.LaxRedirectStrategy;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.automationpractice.model.Products;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

public class HttpSession {
    private CloseableHttpClient httpClient;
    private ApplicationManager app;
    protected static final Products PRODUCTS = new Products();
    private Timestamp timeStamp = new Timestamp(System.currentTimeMillis());
    private int rand = new Random().nextInt(99999998) + 1;

    public HttpSession(ApplicationManager app) {
	this.app = app;
	// Enable following REDIRECTIONS on POST
	httpClient = HttpClients.custom().setRedirectStrategy(new LaxRedirectStrategy()).build();
    }

    public boolean loginWithHTTP(String email, String password, String title) throws IOException {
	HttpPost post = new HttpPost(app.getProperty("web.baseUrl") + "index.php?controller=authentication");
	List<NameValuePair> params = new ArrayList<>();
	params.add(new BasicNameValuePair("email", email));
	params.add(new BasicNameValuePair("passwd", password));
	params.add(new BasicNameValuePair("back", "my-account"));
	params.add(new BasicNameValuePair("SubmitLogin", ""));
	post.setEntity(new UrlEncodedFormEntity(params));
	CloseableHttpResponse response = httpClient.execute(post);
	String body = getTextFrom(response);
	return body.contains(String.format("<title>%s</title>", title));
    }

    public boolean signUp(String email) throws IOException {
	HttpPost post = new HttpPost(app.getProperty("web.baseUrl") + "index.php");
	List<NameValuePair> params = new ArrayList<>();
	params.add(new BasicNameValuePair("controller", "authentication"));
	params.add(new BasicNameValuePair("SubmitCreate", "1"));
	params.add(new BasicNameValuePair("ajax", "true"));
	params.add(new BasicNameValuePair("email_create", email));
	params.add(new BasicNameValuePair("back", "my-account"));
	params.add(new BasicNameValuePair("token", "ce65cefcbafad255f0866d3b32d32058"));
	post.setEntity(new UrlEncodedFormEntity(params));
	CloseableHttpResponse response = httpClient.execute(post);
	String body = getTextFrom(response);
	return body.contains(String.format("<h1 class=\\\"page-heading\\\">%s<\\/h1>", "Create an account"));
    }

    public boolean register(String email, String title, String fName, String lName, String password, String address,
	    String city, String postcode, String state, String phone) throws IOException {
	HttpPost post = new HttpPost(app.getProperty("web.baseUrl")
		+ "index.php?controller=authentication&back=my-account#account-creation");
	List<NameValuePair> params = new ArrayList<>();
	params.add(new BasicNameValuePair("customer_firstname", fName));
	params.add(new BasicNameValuePair("customer_lastname", lName));
	params.add(new BasicNameValuePair("email", email));
	params.add(new BasicNameValuePair("passwd", password));
	params.add(new BasicNameValuePair("days", ""));
	params.add(new BasicNameValuePair("months", ""));
	params.add(new BasicNameValuePair("years", ""));
	params.add(new BasicNameValuePair("firstname", fName));
	params.add(new BasicNameValuePair("lastname", lName));
	params.add(new BasicNameValuePair("company", ""));
	params.add(new BasicNameValuePair("address1", address));
	params.add(new BasicNameValuePair("address2", ""));
	params.add(new BasicNameValuePair("city", city));
	params.add(new BasicNameValuePair("id_state", state));
	params.add(new BasicNameValuePair("postcode", postcode));
	params.add(new BasicNameValuePair("id_country", "21"));
	params.add(new BasicNameValuePair("phone_mobile", phone));
	params.add(new BasicNameValuePair("alias", "My address"));
	params.add(new BasicNameValuePair("back", "my-account"));
	params.add(new BasicNameValuePair("dni", ""));
	params.add(new BasicNameValuePair("email_create", "1"));
	params.add(new BasicNameValuePair("is_new_customer", "1"));
	params.add(new BasicNameValuePair("submitAccount", ""));
	post.setEntity(new UrlEncodedFormEntity(params));
	CloseableHttpResponse response = httpClient.execute(post);
	String body = getTextFrom(response);
	return body.contains(String.format("<title>%s</title>", title));
    }

    public String registerExistedAccountWithAPI(String email) throws IOException {
	String pageContent = Request.Post(app.getProperty("web.baseUrl") + "index.php?controller=authentication")
		.bodyForm(Form.form().add("controller", "authentication").add("SubmitCreate", "1").add("ajax", "true")
			.add("email_create", email).add("back", "my-account").build())
		.execute().returnContent().asString();
	JsonElement parsed = new JsonParser().parse(pageContent);
	JsonElement key = parsed.getAsJsonObject().get("errors");
	return key.getAsJsonArray().getAsString();
    }

    public boolean createEmail(String email) throws IOException, InterruptedException {
	HttpGet get = new HttpGet(app.getProperty("web.mailinator") + "v2/inbox.jsp?zone=public&query=" + email);
	get.setHeader(":authority", "www.mailinator.com");
	get.setHeader(":method", "GET");
	get.setHeader(":scheme", "https");
	get.setHeader(":path", "/v2/inbox.jsp?zone=public&query=" + email);
	CloseableHttpResponse response = httpClient.execute(get);
	String body = getTextFrom(response);
	String inboxMsg = "[ This Inbox channel is currently Empty ]";
	return body.contains(String.format("%s", inboxMsg));
    }
    public Set<Products> addCartItemsWithAPI(String quantity, String id)  throws IOException {
	HttpPost post = new HttpPost(app.getProperty("web.baseUrl") + "index.php?rand=" + this.rand);
	List<NameValuePair> params = new ArrayList<>();
	params.add(new BasicNameValuePair("controller", "cart"));
	params.add(new BasicNameValuePair("add", "1"));
	params.add(new BasicNameValuePair("ajax", "true"));
	params.add(new BasicNameValuePair("qty", quantity));
	params.add(new BasicNameValuePair("id_product", id));
	post.setEntity(new UrlEncodedFormEntity(params));
	CloseableHttpResponse response = httpClient.execute(post);
	String body = getTextFrom(response);
	return null;
//	return body.contains(String.format("<title>%s</title>", quantity));
    }
//    public Set<Products> addCartItemsWithAPI(String quantity, String id) throws IOException {
//	// Use fluent API and save result to json
//	String json = Request.Post(app.getProperty("web.baseUrl") + "index.php?rand=" + this.rand)
//		.bodyForm(Form.form().add("controller", "cart").add("add", "1").add("ajax", "true").add("qty", quantity)
//			.add("id_product", id).build())
//		.addHeader("Accept", "application/json, text/javascript")
//		.addHeader("Content-Type", "multipart/form-data; boundary=----WebKitFormBoundary7MA4YWxkTrZu0gW")
//		.addHeader("X-Requested-With", "XMLHttpRequest").addHeader("cache-control", "no-cache")
//		.addHeader("Connection", "keep-alive").execute().returnContent().asString();
//
//	JsonElement parsed = new JsonParser().parse(json);
//	JsonElement key = parsed.getAsJsonObject().get("products");
//	return new Gson().fromJson(key, new TypeToken<Set<Products>>() {
//	}.getType());
//    }
//
//    public int createProduct(Products newProduct) throws ClientProtocolException, IOException {
//	String json = Request.Post(app.getProperty("web.baseUrl") + "index.php?rand=" + this.rand)
//		.bodyForm(Form.form().add("controller", "cart").add("add", "1").add("ajax", "true")
//			.add("qty", String.valueOf(newProduct.getQuantity())).add("id_product", String.valueOf(newProduct.getId()))
//			.build())
//		.execute().returnContent().asString();
//	JsonElement parsed = new JsonParser().parse(json);
//
//	return parsed.getAsJsonObject().getAsJsonArray("products").get(0)
//		.getAsJsonObject().get("id").getAsInt();
//    }

    private Executor getExecutor() {
	return Executor.newInstance(httpClient);
    }

 

    public boolean searchForProduct(Products product) throws IOException {
	// Use fluent API
	String prod = product.getName().toString();
	String response = Request.Get(app.getProperty("web.searchUrl") + prod + "&limit=10&timestamp="
		+ timeStamp.getTime() + "&ajaxSearch=1&id_lang=1").execute().returnContent().asString();
	return response.contains(prod);

    }

    private String getTextFrom(CloseableHttpResponse response) throws IOException {
	try {
	    return EntityUtils.toString(response.getEntity());
	} finally {
	    response.close();
	}
    }

    public boolean isLoggedInAs(String username) throws IOException {
	HttpGet get = new HttpGet(app.getProperty("web.baseUrl") + "/index.php?controller=my-account");
	CloseableHttpResponse response = httpClient.execute(get);
	String body = getTextFrom(response);
	return body.contains(String.format("<span>%s</span>", username));
    }

    // TODO implement link verification
    public boolean verifyActivationLink(String link) throws IOException {

	return false;
    }

}
