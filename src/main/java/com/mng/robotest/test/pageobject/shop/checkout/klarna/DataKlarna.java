package com.mng.robotest.test.pageobject.shop.checkout.klarna;

import static com.mng.robotest.test.pageobject.shop.checkout.klarna.ModalUserDataKlarna.InputKlarna.*;

public class DataKlarna {

	private String email;
	private String codPostal;
	private String userName;
	private String apellidos;
	private String direccion;
	private String phone;
	private String personnumber;
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getCodPostal() {
		return codPostal;
	}
	public void setCodPostal(String codPostal) {
		this.codPostal = codPostal;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getApellidos() {
		return apellidos;
	}
	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}
	public String getDireccion() {
		return direccion;
	}
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getPersonnumber() {
		return personnumber;
	}
	public void setPersonnumber(String personnumber) {
		this.personnumber = personnumber;
	}
	
	public String getHtmlFormattedData() {
		StringBuffer htmlMsg = new StringBuffer();
		if (getEmail()!=null) {
			htmlMsg.append("<b>" + Email + "</b>:" + getEmail() + "<br>");
		}
		if (getCodPostal()!=null) {
			htmlMsg.append("<b>" + Codigo_Postal + "</b>:" + getCodPostal() + "<br>");
		}
		if (getUserName()!=null) {
			htmlMsg.append("<b>" + User_Name + "</b>:" + getUserName() + "<br>");
		}
		if (getApellidos()!=null) {
			htmlMsg.append("<b>" + Apellidos + "</b>:" + getApellidos() + "<br>");
		}
		if (getDireccion()!=null) {
			htmlMsg.append("<b>" + Direccion + "</b>:" + getDireccion() + "<br>");
		}
		if (getPhone()!=null) {
			htmlMsg.append("<b>" + Phone + "</b>:" + getPhone() + "<br>");
		}
		return htmlMsg.toString();
	}

	
}
