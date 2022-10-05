package com.mng.robotest.domains.registro.beans;

import java.util.List;

import com.mng.robotest.domains.registro.pageobjects.PageRegistroPersonalizacionShop.GenderOption;
import com.mng.robotest.domains.transversal.menus.pageobjects.LineaWeb.LineaType;

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
