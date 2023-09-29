package com.mng.robotest.tests.repository.garment.entity;

import java.util.List;

public class Detail {

	boolean availability;
	List<Product> products;
	
	public boolean isAvailability() {
		return availability;
	}
	public void setAvailability(boolean availability) {
		this.availability = availability;
	}
	public List<Product> getProducts() {
		return products;
	}
	public void setProducts(List<Product> products) {
		this.products = products;
	}
	
}
