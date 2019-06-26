package com.automationpractice.tests;

import static org.testng.Assert.assertTrue;

import java.io.IOException;

import org.testng.annotations.Test;

import com.automationpractice.appmanager.HttpSearchProductSession;
import com.automationpractice.datagenerators.TestDataProviders;
import com.automationpractice.model.Products;

public class SearchProductTests extends TestBase {

	@Test(groups = { "API",
			"SEARCH" }, priority = 1, dataProvider = "getValidProductsFromPropertyFile", dataProviderClass = TestDataProviders.class)
	public void testProductSearchForValidProductsUsingAPI(Products product) throws IOException {
		HttpSearchProductSession session = APP.newSearchProductSession();
		Products newProduct = new Products();
		assertTrue(session.searchForProduct(newProduct.withProductName(product.getProductName())));
	}
}
