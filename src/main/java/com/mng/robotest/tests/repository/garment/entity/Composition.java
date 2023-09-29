package com.mng.robotest.tests.repository.garment.entity;

import java.util.List;

public class Composition {

	String title;
	String composition;
	List<WashingRule> washingRules;
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getComposition() {
		return composition;
	}
	public void setComposition(String composition) {
		this.composition = composition;
	}
	public List<WashingRule> getWashingRules() {
		return washingRules;
	}
	public void setWashingRules(List<WashingRule> washingRules) {
		this.washingRules = washingRules;
	} 
	
}
