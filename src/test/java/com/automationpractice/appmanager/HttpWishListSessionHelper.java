package com.automationpractice.appmanager;

import java.sql.Timestamp;

import org.apache.http.client.utils.URIBuilder;

import com.automationpractice.model.Products;
import com.google.gson.JsonElement;
import com.google.gson.JsonSyntaxException;

public class HttpWishListSessionHelper extends HttpSessionHelper {

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

	protected String[][] createHeaderParamsUsingPdpIndoWith(Products products, String cookieValue) {
		String[][] headerParams = { { "Accept", "application/json, text/javascript, */*; q=0.01" },
				{ "Content-Type", "application/x-www-form-urlencoded; charset=UTF-8" }, { "Cookie", cookieValue },
				{ "Host", "automationpractice.com" }, { "Referer", "http://automationpractice.com/index.php?id_product="
						+ String.valueOf(products.getId()) + "&controller=product" },
				{ "X-Requested-With", "XMLHttpRequest" } };
		return headerParams;
	}

}
