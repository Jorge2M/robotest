package com.mng.robotest.getdata.productlist.entity;

import java.util.List;

public class Group {

	private boolean showSeparator;
	private List<GarmentCatalog> garments;
	
	public boolean isShowSeparator() {
		return showSeparator;
	}
	public void setShowSeparator(boolean showSeparator) {
		this.showSeparator = showSeparator;
	}
	public List<GarmentCatalog> getGarments() {
		return garments;
	}
	public void setGarments(List<GarmentCatalog> garments) {
		this.garments = garments;
	}
	
}
