package com.mng.robotest.domains.apiproduct.domain.entity;

public class Size {
	
	int id;
    SizeDescriptions descriptions;
    String ean;
    
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public SizeDescriptions getDescriptions() {
		return descriptions;
	}
	public void setDescriptions(SizeDescriptions descriptions) {
		this.descriptions = descriptions;
	}
	public String getEan() {
		return ean;
	}
	public void setEan(String ean) {
		this.ean = ean;
	}
    
    
}
