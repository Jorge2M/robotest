package com.mng.robotest.test.getdata.products.data;

import java.util.List;

public class Color {

	private String id;
	private String label;
	private boolean defaultColor;
	private int stock;
	private List<Size> sizes;
	
	public Size getSizeWithMoreStock() {
		int maxStock = -1;
		Size sizeCandidate = null;
		for (Size size : getSizes()) {
			if (size.getStock()>maxStock) {
				sizeCandidate = size;
				maxStock = size.getStock();
			}
		}
		return sizeCandidate;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public boolean isDefaultColor() {
		return defaultColor;
	}
	public void setDefaultColor(boolean defaultColor) {
		this.defaultColor = defaultColor;
	}
	public int getStock() {
		return stock;
	}
	public void setStock(int stock) {
		this.stock = stock;
	}
	public List<Size> getSizes() {
		return sizes;
	}
	public void setSizes(List<Size> sizes) {
		this.sizes = sizes;
	}
	
}
