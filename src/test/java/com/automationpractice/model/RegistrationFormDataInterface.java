package com.automationpractice.model;

interface RegistrationFormDataInterface {

	int hashCode();

	boolean equals(Object obj);

	String getEmail();

	RegistrationFormDataInterface withEmail(String email);

	String getFirstName();

	RegistrationFormDataInterface withFirstName(String fName);

	String getLastName();

	RegistrationFormDataInterface withLastName(String lName);

	String getPassword();

	RegistrationFormDataInterface withPassword(String password);

	String getAddress();

	RegistrationFormDataInterface withAddress(String address);

	String getCityName();

	RegistrationFormDataInterface withCityName(String city);

	String getPostCode();

	RegistrationFormDataInterface withPostCode(String postcode);

	String getState();

	RegistrationFormDataInterface withState(String state);

	String getPhoneNumber();

	RegistrationFormDataInterface withPhoneNumber(String phone);

}