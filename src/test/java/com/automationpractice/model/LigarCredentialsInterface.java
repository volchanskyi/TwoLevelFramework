package com.automationpractice.model;

interface LigarCredentialsInterface {

	int hashCode();

	boolean equals(Object obj);

	String getEmail();

	LigarCredentialsInterface withEmail(String email);

	String getPassword();

	LigarCredentialsInterface withPassword(String password);

	String getAccountName();

	LigarCredentialsInterface withAccountName(String accountName);

	String getToken();

	LigarCredentialsInterface withToken(String token);

}