package com.mng.robotest.domains.apiproduct.entity;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class GenderRedis implements Serializable {

	private static final long serialVersionUID = 2147793072186458359L;
	
	String id; 
	String name;
	
	public GenderRedis() { }
	
	public GenderRedis(String id, String name) {
		super();
		this.id = id;
		this.name = name;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
}
