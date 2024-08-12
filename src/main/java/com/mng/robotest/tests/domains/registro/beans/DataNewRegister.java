package com.mng.robotest.tests.domains.registro.beans;

import static com.mng.robotest.tests.domains.menus.entity.LineaType.*;

import java.util.Arrays;
import java.util.List;

import com.mng.robotest.tests.domains.menus.entity.LineaType;
import com.mng.robotest.tests.domains.registro.pageobjects.PageRegistroPersonalizacionShop.GenderOption;
import com.mng.robotest.tests.repository.secrets.GetterSecrets;
import com.mng.robotest.tests.repository.secrets.GetterSecrets.SecretType;
import com.mng.robotest.testslegacy.data.DataMango;
import com.mng.robotest.testslegacy.beans.Pais;

public class DataNewRegister {

	private String email;
	private String password;
	private String movil;
	private boolean checkPromotions;
	
	private String name;
	private String postalCode;
	private String dateOfBirth;
	private GenderOption gender;
	private List<LineaType> lineas;
	
	public static final DataNewRegister makeDefault(Pais pais) {
		String emailNotExistent = DataMango.getEmailNonExistentTimestamp();
		String passStandard = GetterSecrets.factory().getCredentials(SecretType.SHOP_ROBOT_USER).getPassword();

		return new DataNewRegister(
				emailNotExistent, 
				passStandard, 
				pais.getTelefono(), 
				true,
				"Jorge",
				pais.getCodpos(),
				"04/02/1974",
				GenderOption.MASCULINO,
				Arrays.asList(SHE, HE, KIDS));
	}
	
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
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getMovil() {
		return movil;
	}
	public void setMovil(String movil) {
		this.movil = movil;
	}
	public boolean isCheckPromotions() {
		return checkPromotions;
	}
	public void setCheckPromotions(boolean checkPromotions) {
		this.checkPromotions = checkPromotions;
	}

	public String getName() {
		return name;
	}

	public String getPostalCode() {
		return postalCode;
	}
	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
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
	public void setLineas(List<LineaType> lineas) {
		this.lineas = lineas;
	}
}
