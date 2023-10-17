package com.mng.robotest.tests.domains.galeria.pageobjects.entities;

public enum TypeSlider { 
	PREVIOUS(".prev", "previous") , 
	NEXT(".next", "next"); 
	
	final String normal;
	final String kondo;
	private TypeSlider(String normal, String kondo) {
		this.normal = normal;
		this.kondo = kondo;
	}
	public String getNormal() {
		return this.normal;
	}
	public String getKondo() {
		return this.kondo;
	}
}
