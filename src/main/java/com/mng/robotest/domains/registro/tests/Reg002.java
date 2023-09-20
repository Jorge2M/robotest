package com.mng.robotest.domains.registro.tests;

import java.util.HashMap;
import java.util.Map;

import com.mng.robotest.domains.base.TestBase;
import com.mng.robotest.domains.registro.beans.ListDataRegistro;
import com.mng.robotest.domains.registro.beans.ListDataRegistro.DataRegType;
import com.mng.robotest.domains.registro.beans.ListDataRegistro.PageData;
import com.mng.robotest.domains.registro.steps.PageRegistroIniStepsOutlet;
import com.mng.robotest.domains.registro.steps.PageRegistroIniStepsOutlet.ErrorRegister;
import com.mng.robotest.domains.transversal.menus.steps.SecMenusUserSteps;
import com.mng.robotest.test.beans.IdiomaPais;
import com.mng.robotest.test.beans.Pais;
import com.mng.robotest.test.utils.awssecrets.GetterSecrets;
import com.mng.robotest.test.utils.awssecrets.Secret;
import com.mng.robotest.test.utils.awssecrets.GetterSecrets.SecretType;


public class Reg002 extends TestBase {

	private final SecMenusUserSteps userMenusSteps = new SecMenusUserSteps();
	private final PageRegistroIniStepsOutlet pageRegistroIniSteps = new PageRegistroIniStepsOutlet();
	
	private final Map<String, String> dataRegister = new HashMap<>();
	
	public Reg002(Pais pais, IdiomaPais idioma) {
		super();
		dataTest.setPais(pais);
		dataTest.setIdioma(idioma);
		dataTest.setUserRegistered(false);
	}
	
	@Override
	public void execute() throws Exception {
		accesoAndClickRegistrate();
		registerWithoutInputData();
		registerWithIncorrectInputData();
		registerWithExistentEmail();
	}

	private void accesoAndClickRegistrate() throws Exception {
		access();
		userMenusSteps.selectRegistrate();
	}
	
	private void registerWithoutInputData() {
		pageRegistroIniSteps.clickRegistrateButton(dataRegister, ErrorRegister.INPUT_WARINGS);
	}
	
	private void registerWithIncorrectInputData() {
		var dataKOToSend = new ListDataRegistro();
		dataKOToSend.add(DataRegType.NAME, "Jorge111", false);
		dataKOToSend.add(DataRegType.APELLIDOS, "Muñoz Martínez333", false);
		dataKOToSend.add(DataRegType.EMAIL, "jorge.munoz", false);
		dataKOToSend.add(DataRegType.PASSWORD, "passsinnumeros", false);
		dataKOToSend.add(DataRegType.TELEFONO, "66501512A", false);
		dataKOToSend.add(DataRegType.CODPOSTAL, "0872A", false);
		
		String dataToSendInHtmlFormat = dataKOToSend.getFormattedHTMLData(PageData.PAGEINICIAL);
		pageRegistroIniSteps.sendFixedDataToInputs(dataKOToSend, dataToSendInHtmlFormat);
	}	
	
	private void registerWithExistentEmail() {
		driver.navigate().refresh();
		waitMillis(1000);
		var dataToSend = new ListDataRegistro(); 
		dataToSend.add(DataRegType.NAME, "Jorge", true);
		dataToSend.add(DataRegType.APELLIDOS, "Muñoz Martínez", true);
		
		Secret secret = GetterSecrets.factory().getCredentials(SecretType.SHOP_ROBOT_USER);
		dataToSend.add(DataRegType.EMAIL, secret.getUser(), true);
		dataToSend.add(DataRegType.PASSWORD, secret.getPassword(), true);
		
		dataToSend.add(DataRegType.TELEFONO, "665015122", true);
		dataToSend.add(DataRegType.CODPOSTAL, "08013", true);
		String dataToSendInHtmlFormat = dataToSend.getFormattedHTMLData(PageData.PAGEINICIAL);
		
		pageRegistroIniSteps.sendFixedDataToInputs(dataToSend, dataToSendInHtmlFormat);
		pageRegistroIniSteps.clickRegistrateButton(dataRegister, ErrorRegister.USR_EXISTS_IN_MANGO);
	}
}
