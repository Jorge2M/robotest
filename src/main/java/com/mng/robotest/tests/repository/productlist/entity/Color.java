package com.mng.robotest.tests.repository.productlist.entity;

import java.util.List;

public class Color {

	private String id;
	private String label;
	private String linkAnchor;
	private boolean defaultColor;
	private int stock;
	private List<Size> sizes;
	private List<Image> images;
	private Price price;
	
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
	public String getLinkAnchor() {
		return linkAnchor;
	}
	public void setLinkAnchor(String linkAnchor) {
		this.linkAnchor = linkAnchor;
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

	public List<Image> getImages() {
		return images;
	}

	public void setImages(List<Image> images) {
		this.images = images;
	}

	public Price getPrice() {
		return price;
	}

	public void setPrice(Price price) {
		this.price = price;
	}

}
