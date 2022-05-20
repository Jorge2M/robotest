package com.mng.robotest.domains.cookiescheck.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Cookie {

	private String cookieName;
	private String expiry;
	private boolean thirdParty;
	
	public String getCookieName() {
		return cookieName;
	}
	public void setCookieName(String cookieName) {
		this.cookieName = cookieName;
	}
	public String getExpiry() {
		return expiry;
	}
	public void setExpiry(String expiry) {
		this.expiry = expiry;
	}
	public boolean isThirdParty() {
		return thirdParty;
	}
	public void setThirdParty(boolean thirdParty) {
		this.thirdParty = thirdParty;
	}
	
}
