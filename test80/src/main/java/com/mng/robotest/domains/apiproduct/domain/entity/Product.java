package com.mng.robotest.domains.apiproduct.domain.entity;

import java.util.List;

public class Product {
	
    ProductId id;
    String seller;
    String tariffHeading;
    String style;
    String generic;
    String season;
    String collection;
    String productType;
    String dangerousMatterCode;
    String brand;
    Gender gender;
    Descriptions descriptions;
    Families families;
    List<Composition> composition;
    List<WashingRule> washingRules;
    List<Color> colors;
    List<RelatedModel> relatedModels;
    String countryId;
    String languageId;
    
	public ProductId getId() {
		return id;
	}
	public void setId(ProductId id) {
		this.id = id;
	}
	public String getSeller() {
		return seller;
	}
	public void setSeller(String seller) {
		this.seller = seller;
	}
	public String getTariffHeading() {
		return tariffHeading;
	}
	public void setTariffHeading(String tariffHeading) {
		this.tariffHeading = tariffHeading;
	}
	public String getStyle() {
		return style;
	}
	public void setStyle(String style) {
		this.style = style;
	}
	public String getGeneric() {
		return generic;
	}
	public void setGeneric(String generic) {
		this.generic = generic;
	}
	public String getSeason() {
		return season;
	}
	public void setSeason(String season) {
		this.season = season;
	}
	public String getCollection() {
		return collection;
	}
	public void setCollection(String collection) {
		this.collection = collection;
	}
	public String getProductType() {
		return productType;
	}
	public void setProductType(String productType) {
		this.productType = productType;
	}
	public String getDangerousMatterCode() {
		return dangerousMatterCode;
	}
	public void setDangerousMatterCode(String dangerousMatterCode) {
		this.dangerousMatterCode = dangerousMatterCode;
	}
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	public Gender getGender() {
		return gender;
	}
	public void setGender(Gender gender) {
		this.gender = gender;
	}
	public Descriptions getDescriptions() {
		return descriptions;
	}
	public void setDescriptions(Descriptions descriptions) {
		this.descriptions = descriptions;
	}
	public Families getFamilies() {
		return families;
	}
	public void setFamilies(Families families) {
		this.families = families;
	}
	public List<Composition> getComposition() {
		return composition;
	}
	public void setComposition(List<Composition> composition) {
		this.composition = composition;
	}
	public List<WashingRule> getWashingRules() {
		return washingRules;
	}
	public void setWashingRules(List<WashingRule> washingRules) {
		this.washingRules = washingRules;
	}
	public List<Color> getColors() {
		return colors;
	}
	public void setColors(List<Color> colors) {
		this.colors = colors;
	}
	public List<RelatedModel> getRelatedModels() {
		return relatedModels;
	}
	public void setRelatedModels(List<RelatedModel> relatedModels) {
		this.relatedModels = relatedModels;
	}
	public String getCountryId() {
		return countryId;
	}
	public void setCountryId(String countryId) {
		this.countryId = countryId;
	}
	public String getLanguageId() {
		return languageId;
	}
	public void setLanguageId(String languageId) {
		this.languageId = languageId;
	}
    

	
}

