package com.mng.robotest.getdata.canonicalproduct.entity;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_EMPTY;

@JsonInclude(NON_EMPTY)
public class EntityGenderType implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private String id; 
	private String name;
	
	public EntityGenderType() {
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
