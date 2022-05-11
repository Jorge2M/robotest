package com.mng.robotest.domains.apiproduct.domain.entity;

import java.util.List;

public class Descriptions {
	
	String shortDescription;
    List<String> longDescription;
    
	public String getShortDescription() {
		return shortDescription;
	}
	public void setShortDescription(String shortDescription) {
		this.shortDescription = shortDescription;
	}
	public List<String> getLongDescription() {
		return longDescription;
	}
	public void setLongDescription(List<String> longDescription) {
		this.longDescription = longDescription;
	}
	
}
