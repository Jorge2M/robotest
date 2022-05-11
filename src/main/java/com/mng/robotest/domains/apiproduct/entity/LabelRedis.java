package com.mng.robotest.domains.apiproduct.entity;

public class LabelRedis {

	String id;
	String name;
	String translateId;
	int order;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTranslateId() {
		return translateId;
	}
	public void setTranslateId(String translateId) {
		this.translateId = translateId;
	}
	public int getOrder() {
		return order;
	}
	public void setOrder(int order) {
		this.order = order;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
