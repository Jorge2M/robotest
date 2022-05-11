package com.mng.robotest.domains.apiproduct.entity;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ImageRedis implements Serializable {

	private static final long serialVersionUID = 8137210984184603359L;
	
	String url; 
	String type; 
	String width;
	
	public ImageRedis() { }
	
	public ImageRedis(String url, String type, String width) {
		super();
		this.url = url;
		this.type = type;
		this.width = width;
	}
	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getWidth() {
		return width;
	}
	public void setWidth(String width) {
		this.width = width;
	}
	
}
