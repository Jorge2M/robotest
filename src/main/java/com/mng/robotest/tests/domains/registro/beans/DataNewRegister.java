package com.mng.robotest.tests.domains.registro.beans;

import java.util.Arrays;
import java.util.List;

import com.mng.robotest.tests.domains.registro.pageobjects.PageRegistroPersonalizacionShop.GenderOption;
import com.mng.robotest.tests.domains.transversal.menus.pageobjects.LineaWeb.LineaType;

public class DataNewRegister {

	private final String email;
	private final String password;
	private final String movil;
	private final boolean checkPromotions;
	
	private final String name;
	private final String postalCode;
	private final String dateOfBirth;
	private final GenderOption gender;
	private final List<LineaType> lineas;
	
	public DataNewRegister(
			String email, String password, String movil, boolean checkPromotions,
			String name, String postalCode, String dateOfBirth, GenderOption gender, List<LineaType> lineas) {
		this.email = email;
		this.password = password;
		this.movil = movil;
		this.checkPromotions = checkPromotions;
		
		this.name = name;
		this.postalCode = postalCode;
		this.dateOfBirth = dateOfBirth;
		this.gender = gender;
		this.lineas = lineas;
	}
	
	public DataNewRegister(String email, String password, String movil) {
		this.email = email;
		this.password = password;
		this.movil = movil;
		this.checkPromotions = false;
		
		this.name = "";
		this.postalCode = "";
		this.dateOfBirth = "";
		this.gender = GenderOption.FEMENINO;
		this.lineas = Arrays.asList(LineaType.SHE);
	}	

	public String getEmail() {
		return email;
	}
	public String getPassword() {
		return password;
	}
	public String getMovil() {
		return movil;
	}
	public boolean isCheckPromotions() {
		return checkPromotions;
	}

	public String getName() {
		return name;
	}

	public String getPostalCode() {
		return postalCode;
	}
	public boolean isPostalCode() {
		return (postalCode!=null && "".compareTo(postalCode)!=0);
	}

	public String getDateOfBirth() {
		return dateOfBirth;
	}

	public GenderOption getGender() {
		return gender;
	}

	public List<LineaType> getLineas() {
		return lineas;
	}
}