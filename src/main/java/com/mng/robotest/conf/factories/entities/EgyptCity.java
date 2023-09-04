package com.mng.robotest.conf.factories.entities;

import java.io.Serializable;

public class EgyptCity implements Serializable {

	private static final long serialVersionUID = 1L;
	private String state;
	private String city;
	
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	
	
	
}
