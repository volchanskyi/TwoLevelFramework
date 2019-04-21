package com.automationpractice.appmanager;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.fluent.Form;
import org.apache.http.client.fluent.Request;

import com.automationpractice.model.Products;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

abstract class HttpCartSessionHelper extends HttpSessionHelper {
	
	@Override
	protected String[][] setHeaderParameters(String cookieValue) {
		return setHeaderParamsToAcceptJson("Cookie", cookieValue, "Connection", "keep-alive");
	}

	protected String[][] setBodyParameters(String id, String quantity, String token) {
		String[][] bodyParams = { { "controller", "cart" }, { "add", "1" }, { "ajax", "true" }, { "qty", quantity },
				{ "id_product", id }, { "token", token } };
		return bodyParams;
	}

	protected void createFluentPostRequest(String token, String id, String ipa, String property, int rand,
			String cookie) throws ClientProtocolException, IOException {
		Request.Post(property + "index.php?rand=" + rand)
				.addHeader("Accept", "application/json, text/javascript, */*; q=0.01")
				.addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8")
				.addHeader("X-Requested-With", "XMLHttpRequest").addHeader("Cookie", cookie)
				.bodyForm(Form.form().add("controller", "cart").add("delete", "1").add("id_product", id).add("ipa", ipa)
						.add("token", token).add("ajax", "true").build())
				.execute().returnContent().asString();
	}

	protected void cleanUpUsing(String token, JsonElement parsed, JsonElement key, String property, int rand,
			String webCookie) {
		try {
			if (!key.isJsonNull() && key.isJsonPrimitive() && key.getAsInt() > 0) {
				JsonArray jsonArray = parsed.getAsJsonObject().getAsJsonArray("products");
				for (JsonElement jSo : jsonArray) {
					String id = jSo.getAsJsonObject().get("id").getAsString();
					String ipa = jSo.getAsJsonObject().get("idCombination").getAsString();
					createFluentPostRequest(token, id, ipa, property, rand, webCookie);

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

	protected String createFluentPostRequestWith(Products newProduct, String property, int rand,
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

	protected String createFluentPostRequestWith(String token, String property, int rand, String cookie)
			throws ClientProtocolException, IOException {
		String json = Request.Post(property + "index.php?rand=" + rand)
				.addHeader("Accept", "application/json, text/javascript, */*; q=0.01")
				.addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8")
				.addHeader("X-Requested-With", "XMLHttpRequest").addHeader("Cookie", cookie)
				.bodyForm(Form.form().add("controller", "cart").add("ajax", "true").add("token", token).build())
				.execute().returnContent().asString();
		return json;
	}

}
