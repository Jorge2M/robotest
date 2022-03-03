package com.mng.robotest.test.getdata.products.data;

import java.util.List;

public class Group {

	private boolean showSeparator;
	private List<Garment> garments;
	
	public boolean isShowSeparator() {
		return showSeparator;
	}
	public void setShowSeparator(boolean showSeparator) {
		this.showSeparator = showSeparator;
	}
	public List<Garment> getGarments() {
		return garments;
	}
	public void setGarments(List<Garment> garments) {
		this.garments = garments;
	}
	
}
