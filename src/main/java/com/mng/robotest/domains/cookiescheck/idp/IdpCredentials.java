package com.mng.robotest.domains.cookiescheck.idp;

public class IdpCredentials {
	
	private final String key;
	private final String value;
	
	public IdpCredentials(String key, String value) {
		this.key = key;
		this.value = value;
	}

	public String getKey() {
		return key;
	}

	public String getValue() {
		return value;
	}
}