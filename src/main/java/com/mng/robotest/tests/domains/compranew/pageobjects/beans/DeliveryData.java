package com.mng.robotest.tests.domains.compranew.pageobjects.beans;

import java.util.HashMap;
import java.util.Map;

public class DeliveryData {

	private String name;
	private String surname;
	private String country;
	private String address;
	private String postcode;
	private String city;
	private String province;
	private String state;
	private String email;
	private String mobile;
	private String dni;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPostcode() {
		return postcode;
	}
	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getDni() {
		return dni;
	}
	public void setDni(String dni) {
		this.dni = dni;
	}
	
	public String getHtmlData() {
		var duplas = getDuplas();
        var resultado = new StringBuilder();
        for (var dupla : duplas.entrySet()) {
            if (dupla.getValue() != null && !dupla.getValue().isEmpty()) {
                resultado.append("<br><b>")
                         .append(dupla.getKey())
                         .append("</b>: ")
                         .append(dupla.getValue());
            }
        }
        return resultado.toString();
	}
	
	private Map<String, String> getDuplas() {
        Map<String, String> duplas = new HashMap<>();
        duplas.put("name", name);
        duplas.put("surname", surname);
        duplas.put("country", country);
        duplas.put("address", address);
        duplas.put("postcode", postcode);
        duplas.put("city", city);
        duplas.put("state", state);
        duplas.put("email", email);
        duplas.put("mobile", mobile);
        duplas.put("dni", dni);
        return duplas;
	}

}
