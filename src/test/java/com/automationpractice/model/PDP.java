package com.automationpractice.model;

public class PDP implements LigalCreds, Prods {

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((accountName == null) ? 0 : accountName.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((token == null) ? 0 : token.hashCode());
		result = prime * result + id;
		result = prime * result + ((productName == null) ? 0 : productName.hashCode());
		result = prime * result + quantity;
		return result;
	}

	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LigalCredentials otherCredentials = (LigalCredentials) obj;
		if (email != otherCredentials.getEmail())
			return false;
		if (accountName == null) {
			if (otherCredentials.getAccountName() != null)
				return false;
		} else if (!accountName.equals(otherCredentials.getAccountName()))
			return false;
		if (password != otherCredentials.getPassword())
			return false;
		if (token != otherCredentials.getToken())
			return false;
		Products otherProducts = (Products) obj;
		if (id != otherProducts.getId())
			return false;
		if (productName == null) {
			if (otherProducts.getProductName() != null)
				return false;
		} else if (!productName.equals(otherProducts.getProductName()))
			return false;
		if (quantity != otherProducts.getQuantity())
			return false;
		return true;
	}

	private String accountName;
	private String email;
	private String password;
	private String token;
	private String productName;
	private int id;
	private int quantity;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.automationpractice.model.Prods#getId()
	 */
	@Override
	public int getId() {
		return id;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.automationpractice.model.Prods#withId(int)
	 */
	@Override
	public PDP withId(int id) {
		this.id = id;
		return this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.automationpractice.model.Prods#getQuantity()
	 */
	@Override
	public int getQuantity() {
		return quantity;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.automationpractice.model.Prods#withQuantity(int)
	 */
	@Override
	public PDP withQuantity(int quantity) {
		this.quantity = quantity;
		return this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.automationpractice.model.Prods#getName()
	 */
	@Override
	public String getProductName() {
		return productName;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.automationpractice.model.Prods#withName(java.lang.String)
	 */
	@Override
	public PDP withProductName(String productName) {
		this.productName = productName;
		return this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.automationpractice.model.LigalCreds#getEmail()
	 */
	@Override
	public String getEmail() {
		return email;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.automationpractice.model.LigalCreds#withEmail(java.lang.String)
	 */
	@Override
	public PDP withEmail(String email) {
		this.email = email;
		return this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.automationpractice.model.LigalCreds#getPassword()
	 */
	@Override
	public String getPassword() {
		return password;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.automationpractice.model.LigalCreds#withPassword(java.lang.String)
	 */
	@Override
	public PDP withPassword(String password) {
		this.password = password;
		return this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.automationpractice.model.LigalCreds#getName()
	 */
	@Override
	public String getAccountName() {
		return accountName;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.automationpractice.model.LigalCreds#withName(java.lang.String)
	 */
	@Override
	public PDP withAccountName(String accountName) {
		this.accountName = accountName;
		return this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.automationpractice.model.LigalCreds#getToken()
	 */
	@Override
	public String getToken() {
		return token;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.automationpractice.model.LigalCreds#withToken(java.lang.String)
	 */
	@Override
	public PDP withToken(String token) {
		this.token = token;
		return this;
	}

}
