package com.automationpractice.tests;

import static org.testng.Assert.assertEquals;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Set;

import org.apache.http.client.fluent.Form;
import org.apache.http.client.fluent.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.automationpractice.appmanager.HttpSession;
import com.automationpractice.model.Products;

public class CartTests extends TestBase {

    final private Logger logger = LoggerFactory.getLogger(TestBase.class);

    @BeforeMethod
    private void beforeMethod(Method method, Object[] parameters) {
	logger.debug("Start test " + method.getName() + " with params " + Arrays.asList(parameters));

    }

    @Test
    public void testAddProductToCart() throws IOException {
	HttpSession session = APP.newSession();
	String token = "75bcfffc7e0bb8dec3cd64163aeff58c";
	session.addCartItemsWithIdAndQuantity("3", "1", token);
	Set<Products> oldCart = session.getCartItemsWithIdAndQuantity(token);
	Products newProduct = new Products().withId(7).withQuantity(1);
	int productId = session.addCartItemsWithIdAndQuantity(newProduct);
	Set<Products> newCart = session.getCartItemsWithIdAndQuantity(token);
	oldCart.add(newProduct.withId(productId));	
	assertEquals(newCart, oldCart);
    }
    
    




    @AfterMethod(alwaysRun = true)
    private void logTestStop(Method method, Object[] parameters) {
	logger.debug("Stop test " + method.getName());

    }

}
