package com.automationpractice.appmanager;

import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.Timestamp;

import org.apache.http.client.CookieStore;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.client.LaxRedirectStrategy;

import com.automationpractice.model.RegistrationFormData;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

public class HttpRegistrationSession extends HttpSessionHelper {
	private CloseableHttpClient httpClient;
	private HttpClientContext context = HttpClientContext.create();
	private CookieStore cookieStore = new BasicCookieStore();
	private ApplicationManager app;
	private Timestamp timeStamp = new Timestamp(System.currentTimeMillis());

	public HttpRegistrationSession(ApplicationManager app) {
		this.app = app;
		this.context.setCookieStore(cookieStore);
		// Enable following REDIRECTIONS (302) on POST
		this.httpClient = HttpClients.custom().setRedirectStrategy(new LaxRedirectStrategy()).build();
	}

	// TODO refactor token
	public boolean signUpWith(String email) throws IOException {
		HttpPost post = new HttpPost(app.getProperty("web.baseUrl") + "index.php");
		// Form Data
		String[][] bodyParams = getBodyParamsWith(email);
		post.setEntity(new UrlEncodedFormEntity(createHttpBodyParamsWith(bodyParams)));
		CloseableHttpResponse response = httpClient.execute(post);
		isHttpStatusCodeOK(response);
		String body = getTextFrom(response);
		return body.contains(String.format("<h1 class=\\\"page-heading\\\">%s<\\/h1>", "Create an account"));
	}

	// TODO refactor generated data
	public boolean registerWith(RegistrationFormData registrationFormData, String title)
			throws IOException, URISyntaxException {
		URIBuilder postRequest = new URIBuilder(app.getProperty("web.baseUrl") + "index.php");
		// query string params
		postRequest.setParameter("controller", "authentication").setParameter("back", "my-account#account-creation");
		// header params
		String[][] headerParams = getHeaderParamsWithNoProperties();
		String[][] bodyParams = getBodyParamsWith(registrationFormData);
		HttpPost post = createPostRequestWithParams(postRequest.toString(), headerParams);
		post.setEntity(new UrlEncodedFormEntity(createHttpBodyParamsWith(bodyParams)));
		CloseableHttpResponse response = httpClient.execute(post);
		isHttpStatusCodeOK(response);
		String body = getTextFrom(response);
		return body.contains(String.format("<title>%s</title>", title));
	}

	public String registerExistedAccountWithApiUsing(String email)
			throws JsonSyntaxException, IOException, IllegalStateException {
		// Use Fluent API
		String pageContent = createFluentPostRequestWith(email, app.getProperty("web.baseUrl"));
		JsonElement parsed = new JsonParser().parse(pageContent);
		JsonElement key = parsed.getAsJsonObject().get("errors");
		return key.getAsJsonArray().getAsString();
	}

	public boolean createEmailWith(String email) throws IOException, InterruptedException, URISyntaxException {
		URIBuilder postRequest = new URIBuilder(app.getProperty("web.emailGenerator") + "/ajax.php");
		// query string params
		postRequest.setParameter("f", "set_email_user");
		// request header
		String[][] headerParams = createHeaderParamsWith(app.getProperty("web.emailGeneratorApiToken"));
		// Form Data
		String[][] bodyParams = getBodyParamsUsingEmailNameWith(email);
		HttpPost post = createPostRequestWithParams(postRequest.toString(), headerParams);
		post.setEntity(new UrlEncodedFormEntity(createHttpBodyParamsWith(bodyParams)));
		CloseableHttpResponse response = httpClient.execute(post, this.context);
		isHttpStatusCodeOK(response);
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
		URIBuilder getRequest = new URIBuilder(app.getProperty("web.emailGenerator") + "/ajax.php");
		// query string params
		addStringParamsUsingEmailNameWith(email, getRequest, String.valueOf(timeStamp.getTime()));
		String[][] headerParams = createHeaderParamsWith(app.getProperty("web.emailGeneratorApiToken"));
		HttpGet get = createGetRequestWithParams(getRequest.toString(), headerParams);
		CloseableHttpResponse response = httpClient.execute(get, this.context);
		isHttpStatusCodeOK(response);
		String json = getTextFrom(response);
		JsonElement parsed = new JsonParser().parse(json);
		String emailAddrKey = parsed.getAsJsonObject().get("email").getAsString();
		JsonElement authKey = parsed.getAsJsonObject().get("auth");
		boolean statusKey = authKey.getAsJsonObject().get("success").getAsBoolean();
		JsonArray errorCodeKey = authKey.getAsJsonObject().get("error_codes").getAsJsonArray();
		return isContained(email, link, parsed, emailAddrKey, statusKey, errorCodeKey);
	}
}
