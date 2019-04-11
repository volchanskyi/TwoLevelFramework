package com.automationpractice.appmanager;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.fluent.Form;
import org.apache.http.client.fluent.Request;

public class HttpLoginSessionHelper extends HttpSessionHelper {

	// loginWithErrorHandling Method
		protected String createFluentPostRequestUsingEmailWith(String email, String password, String property)
				throws ClientProtocolException, IOException {
			String content = Request
					.Post(property + "index.php?controller=authentication").bodyForm(Form.form().add("email", email)
							.add("passwd", password).add("back", "").add("SubmitLogin", "").build())
					.execute().returnContent().asString();
			return content;
		}

		

}
