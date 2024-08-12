package com.mng.robotest.tests.domains.menus.entity;

import com.mng.robotest.tests.conf.AppEcom;

import static com.mng.robotest.tests.domains.menus.entity.LineaType.*;

public enum SublineaType { 
	TEEN_NINA("teenA", "teenA", "nina", TEEN),
	TEEN_NINO("teenO", "teenO", "nino", TEEN),
	NINA_NINA("nina", "outletA", "nina", NINA), 
	NINA_BEBE("babyNina", "outletBA", "bebe", NINA),
	NINO_NINO("nino", "outletO", "nino", NINO), 
	NINO_BEBE("babyNino", "outletBO", "bebe", NINO); 
	
	String idShop = "";
	String idOutlet = "";
	String text;
	LineaType parentLine;
	private SublineaType(String idShop, String idOutlet, String text, LineaType parentLine) {
		this.idShop = idShop;
		this.idOutlet = idOutlet;
		this.text = text;
		this.parentLine = parentLine;
	}
	
	public String getId(AppEcom app) {
		if (app==AppEcom.outlet) {
			return idOutlet;
		}
		return idShop;
	}
	
	public String getIdTeen2(AppEcom app) {
		if (getId(app).compareTo("teenA")==0) {
			return "teenQ";
		}
		return "teenP";
	}
	
	public String getText() {
		return text;
	}
	
	public String getNameUpper() {
		return name().toUpperCase();
	}
	
	public LineaType getParentLine() {
		return this.parentLine;
	}
	
}	


