package com.automationpractice.appmanager;

import java.io.IOException;
import java.net.URISyntaxException;

import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.client.LaxRedirectStrategy;

import com.automationpractice.model.RegistrationFormData;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

public class HttpRegistrationSession extends HttpRegistrationSessionHelper {

	HttpRegistrationSession(ApplicationManager app) {
		this.setApp(app);
		this.getContext().setCookieStore(getCookieStore());
		// Enable following REDIRECTIONS (302) on POST
		this.setHttpClient(HttpClients.custom().setRedirectStrategy(new LaxRedirectStrategy()).build());

	}

	public boolean signUpWith(RegistrationFormData registrationFormData, String token) throws IOException, URISyntaxException {
		URIBuilder postRequest = new URIBuilder(getApp().getProperty("web.baseUrl") + "index.php");
		// header params
		String[][] headerParams = setHeaderParamsToAcceptJson("Connection", "keep-alive", "Accept-Encoding",
				"gzip, deflate");
		// Form Data
		String[][] bodyParams = setBodyParameters(registrationFormData.getEmail(), token);
		HttpPost post = createPostRequestWithParams(postRequest.toString(), headerParams);
		post.setEntity(new UrlEncodedFormEntity(createHttpBodyParamsWith(bodyParams)));
		CloseableHttpResponse response = this.getHttpClient().execute(post, this.getContext());
		isHttpStatusCode(200, response);
		String body = getTextFrom(response);
		return body.contains(String.format("<h1 class=\\\"page-heading\\\">%s<\\/h1>", "Create an account"));
	}

	public boolean registerWith(RegistrationFormData registrationFormData, String title)
			throws IOException, URISyntaxException {
		URIBuilder postRequest = new URIBuilder(getApp().getProperty("web.baseUrl") + "index.php");
		// query string params
		postRequest.setParameter("controller", "authentication");
		// header params
		String[][] headerParams = setHeaderParamsToAcceptHtml();
		String[][] bodyParams = setBodyParameters(registrationFormData);
		HttpPost post = createPostRequestWithParams(postRequest.toString(), headerParams);
		post.setEntity(new UrlEncodedFormEntity(createHttpBodyParamsWith(bodyParams)));
		CloseableHttpResponse response = this.getHttpClient().execute(post, this.getContext());
		isHttpStatusCode(200, response);
		String body = getTextFrom(response);
		return body.contains(String.format("<title>%s</title>", title));
	}

	public String registerExistedAccountWithApiUsing(String email)
			throws JsonSyntaxException, IOException, IllegalStateException {
		// Use Fluent API
		String pageContent = createFluentPostRequestWith(email, getApp().getProperty("web.baseUrl"));
		JsonElement parsed = new JsonParser().parse(pageContent);
		JsonElement key = parsed.getAsJsonObject().get("errors");
		return key.getAsJsonArray().getAsString();
	}

	public boolean createEmailWith(String email) throws IOException, InterruptedException, URISyntaxException {
		URIBuilder postRequest = new URIBuilder(getApp().getProperty("web.emailGenerator") + "/ajax.php");
		// query string params
		postRequest.setParameter("f", "set_email_user");
		// request header
		String[][] headerParams = setHeaderParameter(getApp().getProperty("web.emailGeneratorApiToken"));
		// Form Data
		String[][] bodyParams = getBodyParamsUsingEmailNameWith(email);
		HttpPost post = createPostRequestWithParams(postRequest.toString(), headerParams);
		post.setEntity(new UrlEncodedFormEntity(createHttpBodyParamsWith(bodyParams)));
		CloseableHttpResponse response = this.getHttpClient().execute(post, this.getContext());
		isHttpStatusCode(200, response);
		String json = getTextFrom(response);
		JsonElement parsed = new JsonParser().parse(json);
		String emailAddrKey = parsed.getAsJsonObject().get("email_addr").getAsString();
		JsonElement authKey = parsed.getAsJsonObject().get("auth");
		boolean statusKey = authKey.getAsJsonObject().get("success").getAsBoolean();
		JsonArray errorCodeKey = authKey.getAsJsonObject().get("error_codes").getAsJsonArray();
		if (statusKey == true & errorCodeKey.size() == 0)
			return emailAddrKey.equals(email);
		else
			return false;
	}

	// TODO IT IS A STUB
	// THIS LOGIC DOESNT CHECK VERIFICATION LINK
	// CHECKS ONLY FOR ABSENCE OF ERRORS
	public boolean verifyActivationLink(String email, String link) throws IOException, URISyntaxException {
		URIBuilder getRequest = new URIBuilder(getApp().getProperty("web.emailGenerator") + "/ajax.php");
		// query string params
		setQueryParameters(email, getRequest, String.valueOf(getTimeStamp().getTime()));
		String[][] headerParams = setHeaderParameter(getApp().getProperty("web.emailGeneratorApiToken"));
		HttpGet get = createGetRequestWithParams(getRequest.toString(), headerParams);
		CloseableHttpResponse response = this.getHttpClient().execute(get, this.getContext());
		isHttpStatusCode(200, response);
		String json = getTextFrom(response);
		JsonElement parsed = new JsonParser().parse(json);
		String emailAddrKey = parsed.getAsJsonObject().get("email").getAsString();
		JsonElement authKey = parsed.getAsJsonObject().get("auth");
		boolean statusKey = authKey.getAsJsonObject().get("success").getAsBoolean();
		JsonArray errorCodeKey = authKey.getAsJsonObject().get("error_codes").getAsJsonArray();
		return isContained(email, link, parsed, emailAddrKey, statusKey, errorCodeKey);
	}

}
