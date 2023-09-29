package com.mng.robotest.tests.repository.garment.entity;

import java.util.List;
import java.util.Optional;


public class GarmentFicha {

	String id;
	String brandId;
	String categoryId;
	String familyLabel;
	Colors colors;
	List<RelatedModel> relatedModels;
	List<LabelGarment> garmentLabels;
	Details details;
	DataLayer dataLayer;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getBrandId() {
		return brandId;
	}
	public void setBrandId(String brandId) {
		this.brandId = brandId;
	}
	public String getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}
	public String getFamilyLabel() {
		return familyLabel;
	}
	public void setFamilyLabel(String familyLabel) {
		this.familyLabel = familyLabel;
	}
	public DataLayer getDataLayer() {
		return dataLayer;
	}
	public void setDataLayer(DataLayer dataLayer) {
		this.dataLayer = dataLayer;
	}
	public Details getDetails() {
		return details;
	}
	public void setDetails(Details details) {
		this.details = details;
	}
	public Colors getColors() {
		return colors;
	}
	public Optional<ColorGarment> getColor(String id) {
		return getColors().getColors().stream()
				.filter(s -> s.getId().compareTo(id)==0)
				.findFirst();
	}
	public void setColors(Colors colors) {
		this.colors = colors;
	}
	public List<RelatedModel> getRelatedModels() {
		return relatedModels;
	}
	public void setRelatedModels(List<RelatedModel> relatedModels) {
		this.relatedModels = relatedModels;
	}
	public List<LabelGarment> getGarmentLabels() {
		return garmentLabels;
	}
	public Optional<LabelGarment> getGarmentLabel(String key) {
		return getGarmentLabels().stream()
				.filter(s -> s.getKey().compareTo(key)==0)
				.findFirst();
	}
	public void setGarmentLabels(List<LabelGarment> garmentLabels) {
		this.garmentLabels = garmentLabels;
	}
	
	public boolean isStock() {
		return colors.isStock();
	}
}
