package com.automationpractice.appmanager;

import java.io.IOException;
import java.net.URISyntaxException;

import com.automationpractice.model.LigalCredentials;

interface LoginWithCredentialsInterface {

	boolean loginWith(LigalCredentials credentials, String pageTitle) throws IOException, URISyntaxException;

}