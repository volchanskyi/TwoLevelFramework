package com.automationpractice.model;

public class RegistrationFormData implements RegFormData {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.automationpractice.model.Prods#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((fName == null) ? 0 : fName.hashCode());
		result = prime * result + ((lName == null) ? 0 : lName.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((address == null) ? 0 : address.hashCode());
		result = prime * result + ((city == null) ? 0 : city.hashCode());
		result = prime * result + ((postcode == null) ? 0 : postcode.hashCode());
		result = prime * result + ((state == null) ? 0 : state.hashCode());
		result = prime * result + ((phone == null) ? 0 : phone.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.automationpractice.model.Prods#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RegistrationFormData other = (RegistrationFormData) obj;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (fName == null) {
			if (other.fName != null)
				return false;
		} else if (!fName.equals(other.fName))
			return false;
		if (lName == null) {
			if (other.lName != null)
				return false;
		} else if (!lName.equals(other.lName))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (address == null) {
			if (other.address != null)
				return false;
		} else if (!address.equals(other.address))
			return false;
		if (city == null) {
			if (other.city != null)
				return false;
		} else if (!city.equals(other.city))
			return false;
		if (postcode == null) {
			if (other.postcode != null)
				return false;
		} else if (!postcode.equals(other.postcode))
			return false;
		if (state == null) {
			if (other.state != null)
				return false;
		} else if (!state.equals(other.state))
			return false;
		if (phone == null) {
			if (other.phone != null)
				return false;
		} else if (!phone.equals(other.phone))
			return false;
		return true;
	}

	private String email;
	private String fName;
	private String lName;
	private String password;
	private String address;
	private String city;
	private String postcode;
	private String state;
	private String phone;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.automationpractice.model.Prods#getId()
	 */
	@Override
	public String getEmail() {
		return email;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.automationpractice.model.Prods#withId(int)
	 */
	@Override
	public RegistrationFormData withEmail(String email) {
		this.email = email;
		return this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.automationpractice.model.Prods#getId()
	 */
	@Override
	public String getFirstName() {
		return fName;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.automationpractice.model.Prods#withId(int)
	 */
	@Override
	public RegistrationFormData withFirstName(String fName) {
		this.fName = fName;
		return this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.automationpractice.model.Prods#getQuantity()
	 */
	@Override
	public String getLastName() {
		return lName;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.automationpractice.model.Prods#withId(int)
	 */
	@Override
	public RegistrationFormData withLastName(String lName) {
		this.lName = lName;
		return this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.automationpractice.model.Prods#getQuantity()
	 */
	@Override
	public String getPassword() {
		return password;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.automationpractice.model.Prods#withId(int)
	 */
	@Override
	public RegistrationFormData withPassword(String password) {
		this.password = password;
		return this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.automationpractice.model.Prods#getQuantity()
	 */
	@Override
	public String getAddress() {
		return address;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.automationpractice.model.Prods#withId(int)
	 */
	@Override
	public RegistrationFormData withAddress(String address) {
		this.address = address;
		return this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.automationpractice.model.Prods#getQuantity()
	 */
	@Override
	public String getCityName() {
		return city;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.automationpractice.model.Prods#withId(int)
	 */
	@Override
	public RegistrationFormData withCityName(String city) {
		this.city = city;
		return this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.automationpractice.model.Prods#getQuantity()
	 */
	@Override
	public String getPostCode() {
		return postcode;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.automationpractice.model.Prods#withId(int)
	 */
	@Override
	public RegistrationFormData withPostCode(String postcode) {
		this.postcode = postcode;
		return this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.automationpractice.model.Prods#getQuantity()
	 */
	@Override
	public String getState() {
		return state;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.automationpractice.model.Prods#withId(int)
	 */
	@Override
	public RegistrationFormData withState(String state) {
		this.state = state;
		return this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.automationpractice.model.Prods#getQuantity()
	 */
	@Override
	public String getPhoneNumber() {
		return phone;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.automationpractice.model.Prods#withId(int)
	 */
	@Override
	public RegistrationFormData withPhoneNumber(String phone) {
		this.phone = phone;
		return this;
	}

}