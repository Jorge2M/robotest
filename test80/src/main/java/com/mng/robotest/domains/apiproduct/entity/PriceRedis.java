package com.mng.robotest.domains.apiproduct.entity;

import java.io.Serializable;

public class PriceRedis implements Serializable {

	private static final long serialVersionUID = -4975217830177225590L;
	
	String currency;
	Float price;
	Float fullPrice;
	Float basePrice;
	boolean starPrice;
	boolean opi;
	String type;
	String promotionName;
	
	public PriceRedis() { }
	
	public PriceRedis(String currency, Float price, Float fullPrice, Float basePrice, boolean starPrice, boolean opi,
			String type, String promotionName) {
		super();
		this.currency = currency;
		this.price = price;
		this.fullPrice = fullPrice;
		this.basePrice = basePrice;
		this.starPrice = starPrice;
		this.opi = opi;
		this.type = type;
		this.promotionName = promotionName;
	}

	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public Float getPrice() {
		return price;
	}
	public void setPrice(Float price) {
		this.price = price;
	}
	public Float getFullPrice() {
		return fullPrice;
	}
	public void setFullPrice(Float fullPrice) {
		this.fullPrice = fullPrice;
	}
	public Float getBasePrice() {
		return basePrice;
	}
	public void setBasePrice(Float basePrice) {
		this.basePrice = basePrice;
	}
	public boolean getStarPrice() {
		return starPrice;
	}
	public void setStarPrice(boolean starPrice) {
		this.starPrice = starPrice;
	}
	public boolean isOpi() {
		return opi;
	}
	public void setOpi(boolean opi) {
		this.opi = opi;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getPromotionName() {
		return promotionName;
	}
	public void setPromotionName(String promotionName) {
		this.promotionName = promotionName;
	}

}
