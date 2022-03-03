package com.mng.robotest.domains.apiproduct.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class DescriptionsRedis implements Serializable {
	
	private static final long serialVersionUID = -4030425274503358092L;
	
	@JsonProperty("short") String shortDescription;
    @JsonProperty("long") List<String> longDescription;
    
    public DescriptionsRedis() { }
    
	public DescriptionsRedis(String shortDescription, List<String> longDescription) {
		super();
		this.shortDescription = shortDescription;
		this.longDescription = longDescription;
	}
	public String getShortDescription() {
		return shortDescription;
	}
	public void setShortDescription(String shortDescription) {
		this.shortDescription = shortDescription;
	}
	public List<String> getLongDescription() {
		return longDescription;
	}
	public void setLongDescription(List<String> longDescription) {
		this.longDescription = longDescription;
	}
	
}
