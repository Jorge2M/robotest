package com.mng.robotest.domains.loyalty.getdata.data;

public class Email {

	String contactId;
	String email;
	boolean contactable;
	boolean isLoyalty;
	String engine;
	
	public String getContactId() {
		return contactId;
	}
	public void setContactId(String contactId) {
		this.contactId = contactId;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public boolean isContactable() {
		return contactable;
	}
	public void setContactable(boolean contactable) {
		this.contactable = contactable;
	}
	public boolean isLoyalty() {
		return isLoyalty;
	}
	public void setLoyalty(boolean isLoyalty) {
		this.isLoyalty = isLoyalty;
	}
	public String getEngine() {
		return engine;
	}
	public void setEngine(String engine) {
		this.engine = engine;
	}
	
}
