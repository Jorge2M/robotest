package com.mng.robotest.tests.domains.cookiesallowed.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Cookie {

	private String cookieName;
	private String host;
	private String expiry;
	private boolean thirdParty;
	
	public static Cookie from(String cookieName) {
		Cookie cookie = new Cookie();
		cookie.setCookieName(cookieName);
		return cookie;
	}
	
	public String getCookieName() {
		return cookieName;
	}
	public void setCookieName(String cookieName) {
		this.cookieName = cookieName;
	}
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
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
