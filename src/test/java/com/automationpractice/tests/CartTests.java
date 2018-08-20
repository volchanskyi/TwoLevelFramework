package com.automationpractice.tests;

import static org.testng.Assert.assertEquals;

import java.io.IOException;
import java.util.Set;

import org.testng.annotations.Test;

import com.automationpractice.appmanager.HttpSession;
import com.automationpractice.model.Products;

public class CartTests extends TestBase {

	@Test
	public void testAddProductToCart() throws IOException {
		// Init HTTP session
		HttpSession session = APP.newSession();
		// read token
		String token = session.getToken();
		// read cookie PREFIX (Cookie name)
		String cookieName = session.getCookieName();
		// pass cookie name to make Server Side generate cookie and pass them to
		// CONTEXT
		session.initCookie(cookieName);
		// String token = "75bcfffc7e0bb8dec3cd64163aeff58c";
		// add item to the cart
		session.addProductToCart("3", "1", token);
		// get items from the cart and save them to the init value
		Set<Products> oldCart = session.getProductsFromCart(token);
		// create a model object (new product item with real ID)
		Products newProduct = new Products().withId(6).withQuantity(1);
		// add the product from the previous stage to the cart and save it to var
		Products newAddedProduct = session.addProductToCart(newProduct);
		// get items from the cart and save them to the new value
		Set<Products> newCart = session.getProductsFromCart(token);
		// use model object (new product item with real ID)
		// and add it to the the init value of the cart
		oldCart.add(newAddedProduct);
		// clean up cart after test
		session.cleanUpCart(token);
		// verify the init and new cart values are equal
		assertEquals(newCart, oldCart);
	}

}
