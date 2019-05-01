package com.automationpractice.appmanager;

import java.io.IOException;
import java.net.URISyntaxException;

import com.automationpractice.model.Products;
import com.google.gson.JsonSyntaxException;

interface NavigateToPdpInterface {

	boolean navigateToPdpUsing(Products products)
			throws JsonSyntaxException, IOException, IllegalStateException, URISyntaxException;

}
