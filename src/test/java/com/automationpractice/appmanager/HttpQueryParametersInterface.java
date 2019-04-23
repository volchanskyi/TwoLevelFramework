package com.automationpractice.appmanager;

import java.sql.Timestamp;

import org.apache.http.client.utils.URIBuilder;

import com.automationpractice.model.LigalCredentials;
import com.automationpractice.model.Products;

interface HttpQueryParametersInterface {

	void setQueryParamenters(URIBuilder getRequest, int rand, String wishListId, Timestamp timeStamp);

	void setQueryParameters(URIBuilder getRequest, String rand, Products products, LigalCredentials credentials,
			String timestamp);

}
