package com.automationpractice.model;

public interface RegFormData {

	int hashCode();

	boolean equals(Object obj);

	String getEmail();

	RegFormData withEmail(String email);

	String getFirstName();

	RegFormData withFirstName(String fName);

	String getLastName();

	RegFormData withLastName(String lName);

	String getPassword();

	RegFormData withPassword(String password);

	String getAddress();

	RegFormData withAddress(String address);

	String getCityName();

	RegFormData withCityName(String city);

	String getPostCode();

	RegFormData withPostCode(String postcode);

	String getState();

	RegFormData withState(String state);

	String getPhoneNumber();

	RegFormData withPhoneNumber(String phone);

}