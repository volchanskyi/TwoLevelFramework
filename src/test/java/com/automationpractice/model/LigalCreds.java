package com.automationpractice.model;

public interface LigalCreds {

	int hashCode();

	boolean equals(Object obj);

	String getEmail();

	LigalCreds withEmail(String email);

	String getPassword();

	LigalCreds withPassword(String password);

	String getAccountName();

	LigalCreds withAccountName(String accountName);

	String getToken();

	LigalCreds withToken(String token);

}