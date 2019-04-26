package com.automationpractice.appmanager;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Set;

import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.client.LaxRedirectStrategy;

import com.automationpractice.model.Products;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

public class HttpCartSession extends HttpCartSessionHelper {

	HttpCartSession(ApplicationManager app) {
		this.setApp(app);
		this.getContext().setCookieStore(getCookieStore());
		// Enable following REDIRECTIONS (302) on POST
		this.setHttpClient(HttpClients.custom().setRedirectStrategy(new LaxRedirectStrategy()).build());
	}

	public Set<Products> addProductToCart(String id, String quantity, String token)
			throws JsonSyntaxException, IOException, IllegalStateException, URISyntaxException {
		URIBuilder postRequest = new URIBuilder(getApp().getProperty("web.baseUrl") + "index.php");
		// query string params
		postRequest.setParameter("rand", String.valueOf(this.getRand()));
		// request header
		String[][] headerParams = setHeaderParameter(
				getCookieValue(getCookieStore(), this.getWebCookie()));
		// Form Data
		String[][] bodyParams = setBodyParameters(id, quantity, token);
		HttpPost post = createPostRequestWithParams(postRequest.toString(), headerParams);
		post.setEntity(new UrlEncodedFormEntity(createHttpBodyParamsWith(bodyParams)));
		CloseableHttpResponse response = getHttpClient().execute(post, this.getContext());
		isHttpStatusCode(200, response);
		String json = getTextFrom(response);
		JsonElement parsed = new JsonParser().parse(json);
		JsonElement key = parsed.getAsJsonObject().get("products");
		return new Gson().fromJson(key, new TypeToken<Set<Products>>() {
		}.getType());
	}

	public Set<Products> getProductsFromCart(String token) throws IOException, URISyntaxException {
		URIBuilder postRequest = new URIBuilder(getApp().getProperty("web.baseUrl") + "index.php");
		// query string params
		postRequest.setParameter("rand", String.valueOf(this.getRand()));
		String[][] headerParams = setHeaderParameter(
				getCookieValue(getCookieStore(), this.getWebCookie()));
		// Form Data
		String[][] bodyParams = { { "controller", "cart" }, { "ajax", "true" }, { "token", token } };
		HttpPost post = createPostRequestWithParams(postRequest.toString(), headerParams);
		post.setEntity(new UrlEncodedFormEntity(createHttpBodyParamsWith(bodyParams)));
		CloseableHttpResponse response = getHttpClient().execute(post, this.getContext());
		isHttpStatusCode(200, response);
		String json = getTextFrom(response);
		JsonElement parsed = new JsonParser().parse(json);
		JsonElement key = parsed.getAsJsonObject().get("products");
		return new Gson().fromJson(key, new TypeToken<Set<Products>>() {
		}.getType());
	}

	public Products addProductToCart(Products newProduct) throws IOException {
		// Use fluent API
		String json = createFluentPostRequestWith(newProduct, getApp().getProperty("web.baseUrl"),
				this.getRand(), getCookieValue(getCookieStore(), this.getWebCookie()));
		JsonElement parsed = new JsonParser().parse(json);
		JsonArray jsonArray = parsed.getAsJsonObject().getAsJsonArray("products");
		return isAdded(newProduct, jsonArray);
	}

	public void cleanUpCart(String token) throws IOException {
		// Use fluent API
		String json = createFluentPostRequestWith(token, getApp().getProperty("web.baseUrl"), this.getRand(),
				this.getWebCookie());
		JsonElement parsed = new JsonParser().parse(json);
		JsonElement key = parsed.getAsJsonObject().get("nbTotalProducts");
		cleanUpUsing(token, parsed, key, getApp().getProperty("web.baseUrl"), this.getRand(), this.getWebCookie());
	}
}
