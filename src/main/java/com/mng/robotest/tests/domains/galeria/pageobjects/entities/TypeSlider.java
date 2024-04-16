package com.mng.robotest.tests.domains.galeria.pageobjects.entities;

public enum TypeSlider { 
	PREVIOUS(".prev", "previous", "preview") , 
	NEXT(".next", "next", "next"); 
	
	final String old;
	final String normal;
	final String genesis;
	private TypeSlider(String old, String normal, String genesis) {
		this.old = old;
		this.normal = normal;
		this.genesis = genesis;
	}
	public String getOld() {
		return this.normal;
	}
	public String getNormal() {
		return this.normal;
	}
	public String getGenesis() {
		return this.getGenesis();
	}
}
