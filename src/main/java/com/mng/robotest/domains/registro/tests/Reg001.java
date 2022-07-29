package com.mng.robotest.domains.registro.tests;

import java.util.HashMap;
import java.util.Map;

import com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM;
import com.mng.robotest.conftestmaker.AppEcom;
import com.mng.robotest.domains.registro.pageobjects.beans.ListDataRegistro;
import com.mng.robotest.domains.registro.pageobjects.beans.ListDataRegistro.DataRegType;
import com.mng.robotest.domains.registro.pageobjects.beans.ListDataRegistro.PageData;
import com.mng.robotest.domains.registro.steps.PageRegistroIniSteps;
import com.mng.robotest.domains.registro.steps.PageRegistroIniSteps.ErrorRegister;
import com.mng.robotest.domains.transversal.TestBase;
import com.mng.robotest.test.beans.IdiomaPais;
import com.mng.robotest.test.beans.Pais;
import com.mng.robotest.test.stpv.shop.AccesoStpV;
import com.mng.robotest.test.stpv.shop.menus.SecMenusUserSteps;
import com.mng.robotest.test.utils.awssecrets.GetterSecrets;
import com.mng.robotest.test.utils.awssecrets.Secret;
import com.mng.robotest.test.utils.awssecrets.GetterSecrets.SecretType;


public class Reg001 extends TestBase {

	private final SecMenusUserSteps userMenusSteps = SecMenusUserSteps.getNew(channel, app, driver);
	private final PageRegistroIniSteps pageRegistroIniSteps = new PageRegistroIniSteps(driver);
	
	private final Map<String, String> dataRegister = new HashMap<>();
	
	public Reg001(Pais pais, IdiomaPais idioma) throws Exception {
		super();
		dataTest.pais = pais;
		dataTest.idioma = idioma;
		dataTest.userRegistered = false;
	}
	
	@Override
	public void execute() throws Exception {
		if (dataTest.appE==AppEcom.votf) {
			return;
		}
			
		AccesoStpV.oneStep(dataTest, false, driver);
		userMenusSteps.selectRegistrate(dataTest);
		
		registerWithoutInputData();
		registerWithIncorrectInputData();
		registerWithNonExistentEmail();
		registerWithExistentEmail();
	}
	
	private void registerWithoutInputData() {
		pageRegistroIniSteps.clickRegistrateButton(dataTest.pais, app, dataRegister, ErrorRegister.InputWarnings);
	}
	
	private void registerWithIncorrectInputData() {
		ListDataRegistro dataKOToSend = new ListDataRegistro();
		dataKOToSend.add(DataRegType.name, "Jorge111", false);
		dataKOToSend.add(DataRegType.apellidos, "Muñoz Martínez333", false);
		dataKOToSend.add(DataRegType.email, "jorge.munoz", false);
		dataKOToSend.add(DataRegType.password, "passsinnumeros", false);
		dataKOToSend.add(DataRegType.telefono, "66501512A", false);
		dataKOToSend.add(DataRegType.codpostal, "0872A", false);
		
		String dataToSendInHtmlFormat = dataKOToSend.getFormattedHTMLData(PageData.pageInicial);
		pageRegistroIniSteps.sendFixedDataToInputs(dataKOToSend, dataToSendInHtmlFormat);
	}	
	
	private void registerWithNonExistentEmail() {
		driver.navigate().refresh();
		PageObjTM.waitMillis(1000);
		ListDataRegistro dataToSend = new ListDataRegistro(); 
		dataToSend.add(DataRegType.name, "Jorge", true);
		dataToSend.add(DataRegType.apellidos, "Muñoz Martínez", true);
		dataToSend.add(DataRegType.email, "jorge.munoz.noexiste@gmail.com", true);
		dataToSend.add(DataRegType.password, "sirjjjjj74", true);
		dataToSend.add(DataRegType.telefono, "665015122", true);
		dataToSend.add(DataRegType.codpostal, "08720", true);
		
		String dataToSendInHtmlFormat = dataToSend.getFormattedHTMLData(PageData.pageInicial);
		pageRegistroIniSteps.sendFixedDataToInputs(dataToSend, dataToSendInHtmlFormat);
		pageRegistroIniSteps.clickRegistrateButton(dataTest.pais, app, dataRegister, ErrorRegister.UsrNoExistsInGmail);
	}

	private void registerWithExistentEmail() {
		driver.navigate().refresh();
		PageObjTM.waitMillis(1000);
		ListDataRegistro dataToSend = new ListDataRegistro(); 
		dataToSend.add(DataRegType.name, "Jorge", true);
		dataToSend.add(DataRegType.apellidos, "Muñoz Martínez", true);
		
		Secret secret = GetterSecrets.factory().getCredentials(SecretType.SHOP_JORGE_USER);
		dataToSend.add(DataRegType.email, secret.getUser(), true);
		dataToSend.add(DataRegType.password, secret.getPassword(), true);
		
		dataToSend.add(DataRegType.telefono, "665015122", true);
		dataToSend.add(DataRegType.codpostal, "08720", true);
		String dataToSendInHtmlFormat = dataToSend.getFormattedHTMLData(PageData.pageInicial);
		
		pageRegistroIniSteps.sendFixedDataToInputs(dataToSend, dataToSendInHtmlFormat);
		pageRegistroIniSteps.clickRegistrateButton(dataTest.pais, app, dataRegister, ErrorRegister.UsrExistsInMango);
	}
}
