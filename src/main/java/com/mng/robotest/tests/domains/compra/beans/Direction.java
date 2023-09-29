package com.mng.robotest.tests.domains.compra.beans;

import org.openqa.selenium.WebElement;

public class Direction {

	private String name;
	private String address;
	private String location;
	private String phone;
	private boolean isPrincipal;
	private WebElement element;

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public boolean isPrincipal() {
		return isPrincipal;
	}

	public void setPrincipal(boolean isPrincipal) {
		this.isPrincipal = isPrincipal;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public WebElement getElement() {
		return element;
	}

	public void setElement(WebElement element) {
		this.element = element;
	}

}
