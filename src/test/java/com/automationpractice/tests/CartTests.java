package com.automationpractice.tests;

import static org.testng.Assert.assertEquals;

import java.util.Set;

import org.testng.annotations.Test;

import com.automationpractice.appmanager.HttpCartSession;
import com.automationpractice.model.LigalCredentials;
import com.automationpractice.model.Products;

public class CartTests extends TestBase {

//	@Test(groups = { "API",
//			"CART" }, priority = 1, dataProvider = "getValidProductsFromPropertyFile", dataProviderClass = TestDataProviders.class)
//	public void testAddProductToCart(Products product) throws Exception {
//		// Init HTTP session
//		HttpCartSession session = APP.newCartSession();
//		// read token
//		String token = session.getToken();
//		// read cookie PREFIX (Cookie name)
//		String cookieName = session.getCookieName();
//		// pass cookie name to make Server Side generate cookie and pass them to
//		// CONTEXT
//		session.initCookie(cookieName);
//		// add sample item to the cart
//		session.addProductToCart("6", "99", token);
//		// get items from the cart and save them to the init value
//		Set<Products> oldCart = session.getProductsFromCart(token);
//		// add the product to the cart and save it to var
//		Products newAddedProduct = session.addProductToCart(product);
//		// get items from the cart and save them to the new value
//		Set<Products> newCart = session.getProductsFromCart(token);
//		// use model object (new product item with real ID)
//		// and add it to the the init value of the cart
//		oldCart.add(newAddedProduct);
//		// verify the init and new cart values are equal
//		assertEquals(newCart, oldCart);
//	}

	// TODO same test but for registered users
	@Test(groups = { "API",
			"CART" }, priority = 1, dataProvider = "getLigalCredentialsAndProductIdsForPdpControllerFromPropertyFiles", dataProviderClass = TestDataProviders.class)
	public void testAddProductToCartWhileLoggedIn(LigalCredentials credentials, Products product) throws Exception {
		// Init HTTP session
		HttpCartSession session = APP.newCartSession();
		String pageTitle = "My account - My Store";
		// read token
//		String token = session.getRegisteredCartToken();
		// read cookie PREFIX (Cookie name)
		String cookieName = session.getCookieName();
		// pass cookie name to make Server Side generate cookie and pass them to
		// CONTEXT
		session.initCookie(cookieName);
		//login into account
		session.loginWith(credentials, pageTitle);
		// add sample item to the cart
		session.addProductToCart("6", "99", credentials.getToken());
		// get items from the cart and save them to the init value
		Set<Products> oldCart = session.getProductsFromCart(credentials.getToken());
		// add the product to the cart and save it to var
		Products newAddedProduct = session.addProductToCart(product);
		// get items from the cart and save them to the new value
		Set<Products> newCart = session.getProductsFromCart(credentials.getToken());
		// use model object (new product item with real ID)
		// and add it to the the init value of the cart
		oldCart.add(newAddedProduct);
		// verify the init and new cart values are equal
		assertEquals(newCart, oldCart);
	}

}
