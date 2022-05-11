package com.mng.robotest.domains.apiproduct.entity;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class VideoRedis implements Serializable {
	
	private static final long serialVersionUID = -8875860790832160413L;
	
	String url; 
	String width;
	
	public VideoRedis() { }
	
	public VideoRedis(String url, String width) {
		super();
		this.url = url;
		this.width = width;
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
