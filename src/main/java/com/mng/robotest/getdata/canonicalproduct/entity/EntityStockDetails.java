package com.mng.robotest.getdata.canonicalproduct.entity;

import java.io.Serializable;

public class EntityStockDetails implements Serializable {

	private static final long serialVersionUID = 1L;

	private String warehouse;
	private int stock;
	
	public EntityStockDetails() {
	}

	public String getWarehouse() {
		return warehouse;
	}

	public void setWarehouse(String warehouse) {
		this.warehouse = warehouse;
	}

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}

}
