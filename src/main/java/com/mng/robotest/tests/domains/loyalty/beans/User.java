package com.mng.robotest.tests.domains.loyalty.beans;

import com.mng.robotest.tests.repository.usuarios.UserShop;

public class User {

	private final String email;
	private final String contactId;
	private final String country;
	private final String password;
	
	public User(String email, String contactId, String country) {
		this.email = email;
		this.contactId = contactId;
		this.country = country;
		this.password = "";
	}
	
	public User(String email, String contactId, String country, String password) {
		this.email = email;
		this.contactId = contactId;
		this.country = country;
		this.password = password;
	}
	
	public static User from(UserShop userShop, String contactId, String country) {
		return new User(userShop.getUser(), contactId, country, userShop.getPassword());
	}

	public String getEmail() {
		return email;
	}

	public String getContactId() {
		return contactId;
	}

	public String getCountry() {
		return country;
	}

	public String getPassword() {
		return password;
	}

}
