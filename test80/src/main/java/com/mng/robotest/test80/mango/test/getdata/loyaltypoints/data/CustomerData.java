package com.mng.robotest.test80.mango.test.getdata.loyaltypoints.data;

import java.util.List;

public class CustomerData {
	
	private Personal personal;
	private List<Email> emails;

	public List<Email> getEmails() {
		return emails;
	}
	public void setEmails(List<Email> emails) {
		this.emails = emails;
	}
	public Personal getPersonal() {
		return personal;
	}
	public void setPersonal(Personal personal) {
		this.personal = personal;
	}
}