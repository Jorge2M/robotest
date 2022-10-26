package com.mng.robotest.getdata.canonicalproduct.entity;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_EMPTY;

@JsonInclude(NON_EMPTY)
public class EntitySizeDescriptions implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private String base; 
	private String baseEn;
	private String further; 
	private String furtherEn;
	
	public String getBase() {
		return base;
	}

	public void setBase(String base) {
		this.base = base;
	}

	public String getBaseEn() {
		return baseEn;
	}

	public void setBaseEn(String baseEn) {
		this.baseEn = baseEn;
	}

	public String getFurther() {
		return further;
	}

	public void setFurther(String further) {
		this.further = further;
	}

	public String getFurtherEn() {
		return furtherEn;
	}

	public void setFurtherEn(String furtherEn) {
		this.furtherEn = furtherEn;
	}
}
