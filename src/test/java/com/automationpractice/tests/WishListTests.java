package com.automationpractice.tests;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotEquals;
import static org.testng.Assert.assertTrue;

import java.io.IOException;
import java.net.URISyntaxException;

import org.testng.annotations.Test;

import com.automationpractice.appmanager.HttpWishListSession;
import com.automationpractice.model.LigalCredentials;
import com.automationpractice.model.Products;
import com.google.gson.JsonSyntaxException;

public class WishListTests extends TestBase {

	@Test(groups = { "API",
			"WISHLIST" }, dataProvider = "getLigalCredentialsAndProductIdsForPdpControllerFromPropertyFiles", dataProviderClass = TestDataProviders.class)
	public void testAddProductToWishListWithOutLoggingInUsingAPI(LigalCredentials credentials, Products products)
			throws IOException, JsonSyntaxException, IllegalStateException, URISyntaxException {
		// Init HTTP session
		HttpWishListSession session = APP.newWishListSession();
		// read token
//		String token = session.getToken();
		String errMsg = "You must be logged in to manage your wishlist.";
	// read cookie PREFIX (Cookie name)
		String cookieName = session.getCookieName();
		// pass cookie name to make Server Side generate cookie and pass them to
		// CONTEXT
		session.initCookie(cookieName);
		assertTrue(session.navigateToPdpUsing(products));
		assertEquals(session.addProductToWishListUsing(products, credentials), errMsg);
	}

//	@Test(groups = {
//			"API", "WISHLIST" }, dataProvider = "getLigalCredentialsAndProductIdsForPdpControllerFromPropertyFiles", dataProviderClass = TestDataProviders.class)
//	public void testAddProductToWishListWhileLoggedInUsingAPI(LigalCredentials credentials, Products products)
//			throws IOException, URISyntaxException {
//		// Init HTTP session
//		HttpWishListSession session = APP.newWishListSession();
//		// read cookie PREFIX (Cookie name)
//		String cookieName = session.getCookieName();
//		String errMsg = "You must be logged in to manage your wishlist.";
//		String pageTitle = "My account - My Store";
//		// pass cookie name to make Server Side generate cookie and pass them to
//		// CONTEXT
//		session.initCookie(cookieName);
//		session.loginWith(credentials, pageTitle);
//		session.navigateToPdpUsing(products);
//		assertNotEquals(session.addProductToWishListUsing(products, credentials), errMsg);
//		assertTrue(session.addedToWishListAs(products));
//	}

}
