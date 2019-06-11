package com.automationpractice.appmanager;

import com.automationpractice.model.RegistrationFormData;

interface HttpBodyParametersInterface {

	String[][] setBodyParameters(String email, String token);

	String[][] setBodyParameters(RegistrationFormData formData);

}
