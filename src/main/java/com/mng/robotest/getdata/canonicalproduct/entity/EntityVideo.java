package com.mng.robotest.getdata.canonicalproduct.entity;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_EMPTY;

@JsonInclude(NON_EMPTY)
public class EntityVideo implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private String url; 
	private String width;
	
	public EntityVideo() {
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getWidth() {
		return width;
	}

	public void setWidth(String width) {
		this.width = width;
	}
}
