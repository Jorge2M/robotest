package com.mng.robotest.test.getdata.garment.data;

import java.util.List;
import java.util.stream.Collectors;

public class RelatedModel {

	private String id;
	private String type;
	List<String> productIds;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public List<String> getProductIds() {
		return productIds;
	}
	public List<String> getProductIdsWithout(String productId) {
		return getProductIds().stream()
			.filter(s -> s.toString().compareTo(productId)!=0)
			.toList();
	}
	public void setProductIds(List<String> productIds) {
		this.productIds = productIds;
	}
	
}
