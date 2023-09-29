package com.mng.robotest.tests.domains.registro.tests;

import java.util.HashMap;
import java.util.Map;

import com.mng.robotest.tests.domains.base.TestBase;
import com.mng.robotest.tests.domains.registro.beans.ListDataRegistro;
import com.mng.robotest.tests.domains.registro.steps.PageRegistroIniStepsOutlet;
import com.mng.robotest.tests.domains.transversal.menus.steps.SecMenusUserSteps;
import com.mng.robotest.tests.repository.secrets.GetterSecrets;
import com.mng.robotest.testslegacy.beans.IdiomaPais;
import com.mng.robotest.testslegacy.beans.Pais;

import static com.mng.robotest.tests.domains.registro.beans.ListDataRegistro.DataRegType.*;
import static com.mng.robotest.tests.domains.registro.beans.ListDataRegistro.PageData.*;
import static com.mng.robotest.tests.domains.registro.steps.PageRegistroIniStepsOutlet.ErrorRegister.*;
import static com.mng.robotest.tests.repository.secrets.GetterSecrets.SecretType.*;

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
		pageRegistroIniSteps.clickRegistrateButton(dataRegister, INPUT_WARINGS);
	}
	
	private void registerWithIncorrectInputData() {
		var dataKOToSend = new ListDataRegistro();
		dataKOToSend.add(NAME, "Jorge111", false);
		dataKOToSend.add(APELLIDOS, "Muñoz Martínez333", false);
		dataKOToSend.add(EMAIL, "jorge.munoz", false);
		dataKOToSend.add(PASSWORD, "passsinnumeros", false);
		dataKOToSend.add(TELEFONO, "66501512A", false);
		dataKOToSend.add(CODPOSTAL, "0872A", false);
		
		String dataToSendInHtmlFormat = dataKOToSend.getFormattedHTMLData(PAGEINICIAL);
		pageRegistroIniSteps.sendFixedDataToInputs(dataKOToSend, dataToSendInHtmlFormat);
	}	
	
	private void registerWithExistentEmail() {
		driver.navigate().refresh();
		waitMillis(1000);
		var dataToSend = new ListDataRegistro(); 
		dataToSend.add(NAME, "Jorge", true);
		dataToSend.add(APELLIDOS, "Muñoz Martínez", true);
		var secret = GetterSecrets.factory().getCredentials(SHOP_ROBOT_USER);
		dataToSend.add(EMAIL, secret.getUser(), true);
		dataToSend.add(PASSWORD, secret.getPassword(), true);
		
		dataToSend.add(TELEFONO, "665015122", true);
		dataToSend.add(CODPOSTAL, "08720", true);
		String dataToSendInHtmlFormat = dataToSend.getFormattedHTMLData(PAGEINICIAL);
		
		pageRegistroIniSteps.sendFixedDataToInputs(dataToSend, dataToSendInHtmlFormat);
		pageRegistroIniSteps.clickRegistrateButton(dataRegister, USR_EXISTS_IN_MANGO);
	}
}
