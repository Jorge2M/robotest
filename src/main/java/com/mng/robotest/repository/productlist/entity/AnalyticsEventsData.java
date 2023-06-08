package com.mng.robotest.repository.productlist.entity;

public class AnalyticsEventsData {

	private String id;
	private String name;
	private String categoryId;
	private String category;
	private String garmentType;
	private String collection;
	private boolean isPersonalized;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public boolean isPersonalized() {
		return isPersonalized;
	}

	public void setIsPersonalized(boolean isPersonalized) {
		this.isPersonalized = isPersonalized;
	}

	public String getGarmentType() {
		return garmentType;
	}

	public void setGarmentType(String garmentType) {
		this.garmentType = garmentType;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCollection() {
		return collection;
	}

	public void setCollection(String collection) {
		this.collection = collection;
	}

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

}
