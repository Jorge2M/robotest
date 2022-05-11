package com.mng.robotest.domains.apiproduct.entity;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class GenderTypeRedis implements Serializable {
	
	private static final long serialVersionUID = 8617778486972071414L;
	
	String id; 
	String name;
	
	public GenderTypeRedis() { }
	
	public GenderTypeRedis(String id, String name) {
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
