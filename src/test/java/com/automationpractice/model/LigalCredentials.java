package com.automationpractice.model;

public class LigalCredentials {
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LigalCredentials other = (LigalCredentials) obj;
		if (email != other.email)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (password != other.password)
			return false;
		return true;
	}

	private String name;
	private String email;
	private String password;

	public String getEmail() {
		return email;
	}

	public LigalCredentials withEmail(String email) {
		this.email = email;
		return this;
	}

	public String getPassword() {
		return password;
	}

	public LigalCredentials withPassword(String password) {
		this.password = password;
		return this;
	}

	public String getName() {
		return name;
	}

	public LigalCredentials withName(String name) {
		this.name = name;
		return this;
	}

}
