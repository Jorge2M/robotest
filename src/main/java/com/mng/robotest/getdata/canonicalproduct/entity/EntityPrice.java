package com.mng.robotest.getdata.canonicalproduct.entity;

import java.io.Serializable;

public class EntityPrice implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String currency;
    private Float price;
    private Float fullPrice;
    private Float basePrice;
    private boolean starPrice;
    private boolean opi;
    private String type;
    private String promotionName;	
	
	public EntityPrice() {
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

	public boolean isStarPrice() {
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
