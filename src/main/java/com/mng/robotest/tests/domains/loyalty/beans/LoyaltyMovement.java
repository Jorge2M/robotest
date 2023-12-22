package com.mng.robotest.tests.domains.loyalty.beans;

public class LoyaltyMovement {

	private final String concepto;
	private final int pointsReceived;
	private final int pointsUsed;
	
	public LoyaltyMovement(String concepto, int pointsReceived, int pointsUsed) {
		this.concepto = concepto;
		this.pointsReceived = pointsReceived;
		this.pointsUsed = pointsUsed;
	}
	
	public String getConcepto() {
		return concepto;
	}
	public int getPointsReceived() {
		return pointsReceived;
	}
	public int getPointsUsed() {
		return pointsUsed;
	}	

}
