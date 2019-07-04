package com.automationpractice.model;

public class LigalCredentials implements LigarCredentialsInterface {

	private String accountName;
	private String email;
	private String password;
	private String token;

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
	public LigalCredentials withEmail(String email) {
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
	public LigalCredentials withPassword(String password) {
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
	public LigalCredentials withAccountName(String accountName) {
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
	public LigalCredentials withToken(String token) {
		this.token = token;
		return this;
	}

	@Override
	public String toString() {
		return "LigalCredentials [accountName=" + accountName + ", email=" + email + ", password=" + password
				+ ", token=" + token + "]";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.automationpractice.model.LigalCreds#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((accountName == null) ? 0 : accountName.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((token == null) ? 0 : token.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.automationpractice.model.LigalCreds#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LigalCredentials other = (LigalCredentials) obj;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (accountName == null) {
			if (other.accountName != null)
				return false;
		} else if (!accountName.equals(other.accountName))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (token == null) {
			if (other.token != null)
				return false;
		} else if (!token.equals(other.token))
			return false;
		return true;
	}

}
