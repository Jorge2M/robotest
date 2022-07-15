package com.mng.robotest.domains.loyalty.beans;

public class User {

	private final String email;
	private final String contactId;
	private final String country;
	
	public User(String email, String contactId, String country) {
		this.email = email;
		this.contactId = contactId;
		this.country = country;
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

}
