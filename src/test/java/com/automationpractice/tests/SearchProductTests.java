package com.automationpractice.tests;


import static org.testng.Assert.assertTrue;

import java.io.IOException;

import org.testng.annotations.Test;

import com.automationpractice.appmanager.HttpSession;
import com.automationpractice.model.Products;

public class SearchProductTests extends TestBase {

        @Test
        public void testProductSearchUsingAPI() throws IOException {
    	HttpSession session = APP.newSession();
    	Products newProduct =  new Products();
    	assertTrue(session.searchForProduct(newProduct.withName("blouse")));
        }
    }
