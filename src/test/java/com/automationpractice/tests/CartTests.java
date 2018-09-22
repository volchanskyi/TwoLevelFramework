package com.automationpractice.tests;

import static org.testng.Assert.assertEquals;

import java.util.Set;

import org.testng.annotations.Test;

import com.automationpractice.appmanager.HttpSession;
import com.automationpractice.model.Products;

public class CartTests extends TestBase {

	@Test(dataProvider = "getValidProductsFromPropertyFile", dataProviderClass = TestDataProviders.class)
	public void testAddProductToCart(Products product) throws Exception {
		// Init HTTP session
		HttpSession session = APP.newSession();
		// read token
		String token = session.getToken();
		// read cookie PREFIX (Cookie name)
		String cookieName = session.getCookieName();
		// pass cookie name to make Server Side generate cookie and pass them to
		// CONTEXT
		session.initCookie(cookieName);
		// add sample item to the cart
		session.addProductToCart("1", "99", token);
		// get items from the cart and save them to the init value
		Set<Products> oldCart = session.getProductsFromCart(token);
		// add the product to the cart and save it to var
		Products newAddedProduct = session.addProductToCart(product);
		// get items from the cart and save them to the new value
		Set<Products> newCart = session.getProductsFromCart(token);
		// use model object (new product item with real ID)
		// and add it to the the init value of the cart
		oldCart.add(newAddedProduct);
		// verify the init and new cart values are equal
		assertEquals(newCart, oldCart);
	}
}
