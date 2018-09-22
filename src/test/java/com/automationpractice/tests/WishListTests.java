package com.automationpractice.tests;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.io.IOException;

import org.testng.annotations.Test;

import com.automationpractice.appmanager.HttpSession;
import com.automationpractice.appmanager.PdpHelper;
import com.automationpractice.model.LigalCredentials;
import com.automationpractice.model.Products;

public class WishListTests extends TestBase {

	// TODO implement test
	@Test(dataProvider = "getValidProductsFromPropertyFile", dataProviderClass = TestDataProviders.class)
	public void testAddProductToWishListWithOutLoggingInUsingAPI(Products product) throws IOException {
		// Init HTTP session
		HttpSession session = APP.newSession();
		String errMsg = "You must be logged in to manage your wishlist.";
		// read cookie PREFIX (Cookie name)
		String cookieName = session.getCookieName();
		// pass cookie name to make Server Side generate cookie and pass them to
		// CONTEXT
		session.initCookie(cookieName);
		// add item to the cart and verify the error message
		assertEquals(session.addProductToWishList(product), errMsg);
	}

	@Test(groups = {
			"GUI" }, priority = 100, dataProvider = "getValidProductsFromPropertyFile", dataProviderClass = TestDataProviders.class)
	public void testAddProductToWishListWhileLoggedInUsingAPI(LigalCredentials creds, Products product)
			throws Exception {
		String overlayMsg = "Added to your wishlist.";
		PdpHelper pdpHelper = APP.pdp();
		pdpHelper.navigateToPdpUsingExistedCredentials(creds, product).addProductToWishList();
		assertTrue(pdpHelper.verifyWithOverlay(overlayMsg));
	}

}
