package com.mng.robotest.domains.apiproduct.entity;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class StockDetailRedis implements Serializable {
	
	private static final long serialVersionUID = 2173607514825729127L;
	
	String warehouse; 
	int stock;
	
	public StockDetailRedis() { }
	
	public StockDetailRedis(String warehouse, int stock) {
		super();
		this.warehouse = warehouse;
		this.stock = stock;
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
	
