package com.mng.robotest.tests.domains.galeria.pageobjects.entity;

public enum NumColumnas {
	
	UNA(1), 
	DOS(2), 
	TRES(3), 
	CUATRO(4);
	
	private final int num;
	private NumColumnas(int num) {
		this.num = num;
	}
	public static NumColumnas getValue(int num) {
		for (var value : values()) {
			if (value.num==num) {
				return value;
			}
		}
		return null;
	}
}
