package com.mng.robotest.tests.domains.cookiesallowed.entities;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CookiesData {

	private List<Cookie> content;

	public List<Cookie> getContent() {
		return content;
	}

	public void setContent(List<Cookie> content) {
		this.content = content;
	}
	
}
