package com.automationpractice.appmanager;

import java.sql.Timestamp;

import org.apache.http.client.utils.URIBuilder;

import com.automationpractice.model.LigalCredentials;
import com.automationpractice.model.Products;
import com.google.gson.JsonElement;
import com.google.gson.JsonSyntaxException;

public class HttpWishListSessionHelper extends HttpSessionHelper
		implements HttpQueryParameterInterface, HttpHeaderParametersInterface, HttpQueryParametersInterface {

	@Override
	public void setQueryParameter(URIBuilder getRequest) {
		getRequest.setParameter("fc", "module").setParameter("module", "blockwishlist").setParameter("controller",
				"mywishlist");
	}

	@Override
	public void setQueryParamenters(URIBuilder getRequest, int rand, String wishListId, Timestamp timeStamp) {
		try {
			if (!wishListId.isEmpty() & wishListId.length() != 0) {
				getRequest.setParameter("fc", "module").setParameter("module", "blockwishlist")
						.setParameter("controller", "mywishlist").setParameter("rand", String.valueOf(rand))
						.setParameter("deleted", "1").setParameter("id_wishlist", wishListId)
						.setParameter("_", String.valueOf(timeStamp.toString()));
				return;
			}
		} catch (IllegalArgumentException e) {
			HTTP_SESSION_LOGGER.error(e.toString());
		}
	}

	@Override
	public void setQueryParameters(URIBuilder getRequest, String rand, Products products, LigalCredentials credentials,
			String timestamp) {
		getRequest.setParameter("rand", rand).setParameter("action", "add")
				.setParameter("id_product", String.valueOf(products.getId()))
				.setParameter("quantity", String.valueOf(products.getQuantity()))
				.setParameter("token", String.valueOf(credentials.getToken())).setParameter("id_product_attribute", "1")
				.setParameter("_", timestamp);
	}

	protected boolean isWishListEmpty(JsonElement addedProducts) {
		try {
			boolean wishlistProductsIds = !addedProducts.isJsonArray() ? addedProducts.getAsJsonObject().getAsBoolean()
					: false;
			return !wishlistProductsIds;
		} catch (JsonSyntaxException e) {
			HTTP_SESSION_LOGGER.error(e.toString());
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
			HTTP_SESSION_LOGGER.error(e.toString());
		}
		return false;
	}

	@Override
	public String[][] setHeaderParameters(Products products, String cookieValue) {
		return setHeaderParamsToAcceptJson("Cookie", cookieValue, "Referer",
				"http://automationpractice.com/index.php?id_product=" + String.valueOf(products.getId())
						+ "&controller=product");
	}

}
