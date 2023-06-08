package com.mng.robotest.repository.productlist.entity;

public class Size {

	private int id;
	private String label;
	private int stock;
	
	public int getId() {
		return id;
	}
	public String getId2Digits() {
		String valor = String.valueOf(getId());
		if (valor.length()==1) {
			return "0" + valor;
		}
		return valor;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public int getStock() {
		return stock;
	}
	public void setStock(int stock) {
		this.stock = stock;
	}
	
}
