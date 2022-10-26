package com.mng.robotest.getdata.garment.entity;

import java.util.List;

public class Descriptions {
	
	String title;
	List<String> bullets;
	List<String> capsules;
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public List<String> getBullets() {
		return bullets;
	}
	public void setBullets(List<String> bullets) {
		this.bullets = bullets;
	}
	public List<String> getCapsules() {
		return capsules;
	}
	public void setCapsules(List<String> capsules) {
		this.capsules = capsules;
	}
}
