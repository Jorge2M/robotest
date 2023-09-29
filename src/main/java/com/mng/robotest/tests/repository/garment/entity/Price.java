package com.mng.robotest.tests.repository.garment.entity;

import java.util.List;

public class Price {

	Float price;
	String currency;
	String salePrice;
	String discount;
	List<String> crossedOutPrices;
	
	public Float getPrice() {
		return price;
	}
	public void setPrice(Float price) {
		this.price = price;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public String getSalePrice() {
		return salePrice;
	}
	public void setSalePrice(String salePrice) {
		this.salePrice = salePrice;
	}
	public String getDiscount() {
		return discount;
	}
	public void setDiscount(String discount) {
		this.discount = discount;
	}
	public List<String> getCrossedOutPrices() {
		return crossedOutPrices;
	}
	public void setCrossedOutPrices(List<String> crossedOutPrices) {
		this.crossedOutPrices = crossedOutPrices;
	}
	
}
