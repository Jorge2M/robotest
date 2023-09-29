package com.mng.robotest.tests.domains.cookiescheck.idp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class IdentityToken {
	
	private String access_token;
	private String refresh_token;
	
	public IdentityToken() {
	}
	
	public IdentityToken(String access_token, String refresh_token) {
		this.access_token = access_token;
		this.refresh_token = refresh_token;
	}

	public String getAccess_token() {
		return access_token;
	}

	public String getRefresh_token() {
		return refresh_token;
	}
	
	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}
	
	public void setRefresn_token(String refresh_token) {
		this.refresh_token = refresh_token;
	}
	
}