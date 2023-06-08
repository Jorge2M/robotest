package com.mng.robotest.repository.garment.entity;

import java.util.List;

public class Colors {

	String title;
	List<ColorGarment> colors;
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public List<ColorGarment> getColors() {
		return colors;
	}
	public void setColors(List<ColorGarment> colors) {
		this.colors = colors;
	}
	public boolean isStock() {
		for (ColorGarment color : colors) {
			if ("con-stock".compareTo(color.getDataLayer().getStock())==0) {
				return true;
			}
		}
		return false;
	}
	
}
