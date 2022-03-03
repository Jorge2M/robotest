package com.mng.robotest.domains.apiproduct.domain.entity;

import java.util.List;

public class RelatedModel {
	
	String type;
	List<ProductId> productIds;
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public List<ProductId> getProductIds() {
		return productIds;
	}
	public void setProductIds(List<ProductId> productIds) {
		this.productIds = productIds;
	}
	
}
