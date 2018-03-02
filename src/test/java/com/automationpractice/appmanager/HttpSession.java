package com.automationpractice.appmanager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.client.LaxRedirectStrategy;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class HttpSession {
    private CloseableHttpClient httpClient;
    private ApplicationManager app;

    public HttpSession(ApplicationManager app) {
	this.app = app;
	httpClient = HttpClients.custom().setRedirectStrategy(new LaxRedirectStrategy()).build();
    }

    public boolean login(String email, String password, String title) throws IOException {
	HttpPost post = new HttpPost(
		app.getProperty("web.baseUrl") + "index.php?controller=authentication");
	List<NameValuePair> params = new ArrayList<>();
	params.add(new BasicNameValuePair("email", email));
	params.add(new BasicNameValuePair("passwd", password));
	params.add(new BasicNameValuePair("back", "my-account"));
	params.add(new BasicNameValuePair("SubmitLogin", ""));
	post.setEntity(new UrlEncodedFormEntity(params));
	CloseableHttpResponse response = httpClient.execute(post);
	String body = getTextFrom(response);
//	return body.contains(String.format("<span class=\"italic\">%s</span>", username));
	return body.contains(String.format("<title>%s</title>", title));	
    }

    private String getTextFrom(CloseableHttpResponse response) throws IOException {
	try {
	    return EntityUtils.toString(response.getEntity());
	} finally {
	    response.close();
	}
    }

    public boolean isLoggedInAs(String username) throws IOException {
	HttpGet get = new HttpGet(
		app.getProperty("web.baseUrl") + "/index.php?controller=my-account");
	CloseableHttpResponse response = httpClient.execute(get);
	String body = getTextFrom(response);
	return body.contains(String.format("<span>%s</span>", username));
    }

}
