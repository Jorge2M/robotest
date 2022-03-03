package com.mng.robotest.domains.apiproduct.entity;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class RelatedModelRedis implements Serializable {
	
	private static final long serialVersionUID = -6011063467947550291L;
	
	String type;
    List<String> productIds;
    
    public RelatedModelRedis() { }
    
	public RelatedModelRedis(String type, List<String> productIds) {
		super();
		this.type = type;
		this.productIds = productIds;
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
	public void setProductIds(List<String> productIds) {
		this.productIds = productIds;
	}
}
