package com.mng.robotest.domains.apiproduct.entity;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class SizeRedis implements Serializable {
	
	private static final long serialVersionUID = -7665957130791492438L;
	
	String id; 
	SizeDescriptionsRedis descriptions;
    String ean;
    List<StockDetailRedis> stockDetails;
    
    
    public SizeRedis() { }
    
	public SizeRedis(String id, SizeDescriptionsRedis descriptions, String ean, List<StockDetailRedis> stockDetails) {
		super();
		this.id = id;
		this.descriptions = descriptions;
		this.ean = ean;
		this.stockDetails = stockDetails;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public SizeDescriptionsRedis getDescriptions() {
		return descriptions;
	}
	public void setDescriptions(SizeDescriptionsRedis descriptions) {
		this.descriptions = descriptions;
	}
	public String getEan() {
		return ean;
	}
	public void setEan(String ean) {
		this.ean = ean;
	}

	public List<StockDetailRedis> getStockDetails() {
		return stockDetails;
	}

	public void setStockDetails(List<StockDetailRedis> stockDetails) {
		this.stockDetails = stockDetails;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
