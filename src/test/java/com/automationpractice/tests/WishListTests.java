package com.automationpractice.tests;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.io.IOException;

import org.testng.annotations.Test;

import com.automationpractice.appmanager.HttpSession;
import com.automationpractice.model.Products;

public class WishListTests extends TestBase {

	// TODO implement test
	@Test(groups = {
			"API" }, dataProvider = "getValidProductsFromPropertyFile", dataProviderClass = TestDataProviders.class)
	public void testAddProductToWishListWithOutLoggingInUsingAPI(Products product) throws IOException {
		// Init HTTP session
		HttpSession session = APP.newSession();
		// read token
		String token = session.getToken();
		String errMsg = "You must be logged in to manage your wishlist.";
		// read cookie PREFIX (Cookie name)
		String cookieName = session.getCookieName();
		// pass cookie name to make Server Side generate cookie and pass them to
		// CONTEXT
		session.initCookie(cookieName);
		assertTrue(session.navigateToPdpUsing(product));
		assertEquals(session.addProductToWishListUsing(product, token), errMsg);
	}

	@Test(groups = {
			"API" }, dataProvider = "getValidProductsFromPropertyFile", dataProviderClass = TestDataProviders.class)
	public void testAddProductToWishListWhileLoggedInUsingAPI(Products product) throws IOException {
		// Init HTTP session
		HttpSession session = APP.newSession();
		// read token
//		String token = session.getToken();
		String token = "75bcfffc7e0bb8dec3cd64163aeff58c";
		// read cookie PREFIX (Cookie name)
		String cookieName = session.getCookieName();
		String errMsg = "expected message";
		// pass cookie name to make Server Side generate cookie and pass them to
		// CONTEXT
		//75bcfffc7e0bb8dec3cd64163aeff58c
		session.initCookie(cookieName);
		assertTrue(session.navigateToPdpUsing(product));
		assertEquals(session.addProductToWishListUsing(product, token), errMsg);
	}

//	@Test(groups = {
//			"GUI" }, priority = 100, dataProvider = "getLigalCredentialsAndProductsForPdpControllerFromPropertyFiles", dataProviderClass = TestDataProviders.class)
//	public void testAddProductToWishListWhileLoggedInUsingGUI(Object something) throws Exception {
//		String overlayMsg = "Added to your wishlist.";
//		PdpHelper pdpHelper = APP.pdp();
//		pdpHelper.loginUsing(something).navigateToPdpUsing(something).addProductToWishList();
//		assertTrue(pdpHelper.verifyWithOverlay(overlayMsg));
//	}

}
