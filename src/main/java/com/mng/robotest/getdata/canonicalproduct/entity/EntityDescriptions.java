package com.mng.robotest.getdata.canonicalproduct.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_EMPTY;

@JsonInclude(NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class EntityDescriptions implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@JsonProperty("short") private String shortDescription;
	@JsonProperty("long") private List<String> longDescription;
	@JsonProperty("shortEn") private String englishShortDescription;
	
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

	public String getEnglishShortDescription() {
		return englishShortDescription;
	}

	public void setEnglishShortDescription(String englishShortDescription) {
		this.englishShortDescription = englishShortDescription;
	}

}

