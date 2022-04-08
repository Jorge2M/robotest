package com.mng.robotest.domains.apiproduct.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class SizeDescriptionsRedis implements Serializable {
	
	private static final long serialVersionUID = 8769047195036133682L;
	
	@JsonProperty("base") String baseDescription;
    @JsonProperty("further") String furtherDescription;
    String baseEn;
    String furtherEn;
    
    public SizeDescriptionsRedis() { }
    
	public SizeDescriptionsRedis(String baseDescription, String furtherDescription) {
		super();
		this.baseDescription = baseDescription;
		this.furtherDescription = furtherDescription;
	}
	
	public String getBaseDescription() {
		return baseDescription;
	}
	public void setBaseDescription(String baseDescription) {
		this.baseDescription = baseDescription;
	}
	public String getFurtherDescription() {
		return furtherDescription;
	}
	public void setFurtherDescription(String furtherDescription) {
		this.furtherDescription = furtherDescription;
	}

	public String getBaseEn() {
		return baseEn;
	}

	public void setBaseEn(String baseEn) {
		this.baseEn = baseEn;
	}

	public String getFurtherEn() {
		return furtherEn;
	}

	public void setFurtherEn(String furtherEn) {
		this.furtherEn = furtherEn;
	}
	
}
