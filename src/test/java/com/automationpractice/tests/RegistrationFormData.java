package com.automationpractice.tests;

public class RegistrationFormData {
	private String email;
	private String fName;
	private String lName;
	private String password;
	private String address;
	private String city;
	private String postalCode;
	private String state;
	private String phone;

	public RegistrationFormData(String email, String fName, String lName, String password,
			String address, String city, String postalCode, String state, String phone) {
		this.email = email;
		this.fName = fName;
		this.lName = lName;
		this.password = password;
		this.address = address;
		this.city = city;
		this.postalCode = postalCode;
		this.state = state;
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public String getfName() {
		return fName;
	}

	public String getlName() {
		return lName;
	}

	public String getPassword() {
		return password;
	}

	public String getAddress() {
		return address;
	}

	public String getCity() {
		return city;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public String getState() {
		return state;
	}

	public String getPhone() {
		return phone;
	}
}