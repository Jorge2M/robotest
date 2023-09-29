package com.mng.robotest.tests.domains.loyalty.getdata.data;

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