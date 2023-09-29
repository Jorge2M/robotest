package com.mng.robotest.tests.repository.garment.entity;

import java.util.List;

public class Product {

	String id;
	String name;
	String brand;
	String subBrand;
	String categoryId;
	String category;
	String collection;
	String gender;
	String exclusiveOnline;
	String priceType;
	List<String> description;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	public String getSubBrand() {
		return subBrand;
	}
	public void setSubBrand(String subBrand) {
		this.subBrand = subBrand;
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
	public String getCollection() {
		return collection;
	}
	public void setCollection(String collection) {
		this.collection = collection;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getExclusiveOnline() {
		return exclusiveOnline;
	}
	public void setExclusiveOnline(String exclusiveOnline) {
		this.exclusiveOnline = exclusiveOnline;
	}
	public String getPriceType() {
		return priceType;
	}
	public void setPriceType(String priceType) {
		this.priceType = priceType;
	}
	public List<String> getDescription() {
		return description;
	}
	public void setDescription(List<String> description) {
		this.description = description;
	}
	
}
