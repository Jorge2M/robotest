package com.mng.robotest.tests.domains.loyalty.beans;

public class LoyaltyMovement {

	private final String concepto;
	private final int points;
	
	public LoyaltyMovement(String concepto, int points) {
		this.concepto = concepto;
		this.points = points;
	}
	
	public String getConcepto() {
		return concepto;
	}
	public int getPoints() {
		return points;
	}
	

}
