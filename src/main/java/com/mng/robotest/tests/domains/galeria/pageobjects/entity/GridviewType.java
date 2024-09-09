package com.mng.robotest.tests.domains.galeria.pageobjects.entity;

public enum GridviewType {

	ONE_COLUMN("inspirational"),
	TWO_COLUMN("standard"),
	TREE_COLUM("overview");
	
	private String type;
	private GridviewType(String type) {
		this.type = type;
	}
	
	public String getType() {
		return type;
	}
	
}
