package com.mng.robotest.tests.repository.canonicalproduct.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;
import java.util.List;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_EMPTY;

@JsonInclude(NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class EntitySize implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private String id; 
	private EntitySizeDescriptions descriptions; 
	private String ean;
	private List<EntityStockDetails> stockDetails;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public EntitySizeDescriptions getDescriptions() {
		return descriptions;
	}

	public void setDescriptions(EntitySizeDescriptions descriptions) {
		this.descriptions = descriptions;
	}

	public String getEan() {
		return ean;
	}

	public void setEan(String ean) {
		this.ean = ean;
	}

	public List<EntityStockDetails> getStockDetails() {
		return stockDetails;
	}

	public void setStockDetails(List<EntityStockDetails> stockDetails) {
		this.stockDetails = stockDetails;
	}
}
