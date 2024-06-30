package com.mng.robotest.tests.domains.legal.tests;

import static com.mng.robotest.testslegacy.data.PaisShop.*;

import com.mng.robotest.tests.domains.base.TestBase;
import com.mng.robotest.tests.domains.menus.steps.SecMenusUserSteps;
import com.mng.robotest.tests.domains.registro.pageobjects.PageRegistroInitialShop;
import com.mng.robotest.tests.domains.registro.steps.PageRegistroInitialShopSteps;

/**
 * Control textos legales "Nuevo Registro":
 * https://confluence.mango.com/pages/viewpage.action?spaceKey=PIUR&title=Mapeo+de+textos+legales#expand-Nuevoregistro
 *
 */
public class Leg001 extends TestBase {

	@Override
	public void execute() throws Exception {
		checkRGPD();
		renewTest();
		checkLoyalty();
		renewTest();
		checkCorea();
	}
	
	private void checkRGPD() throws Exception {
		dataTest.setPais(GREECE.getPais());
		dataTest.setIdioma(GREECE.getPais().getListIdiomas().get(1)); //Inglés
		goToRegister();
		clickPoliticaPrivacidad();
		checkTextoLegal();		
	}
	private void checkLoyalty() throws Exception {
		dataTest.setPais(ESPANA.getPais());
		dataTest.setIdioma(ESPANA.getPais().getListIdiomas().get(0)); //Español
		goToRegister();
		clickPoliticaPrivacidad();
		checkTextoLegal();		
	}	
	private void checkCorea() throws Exception {
		dataTest.setPais(COREA_DEL_SUR.getPais());
		dataTest.setIdioma(COREA_DEL_SUR.getPais().getListIdiomas().get(1)); //Inglés
		goToRegister();
		clickCollectionAndUseLinks();
		checkTextoLegal();		
	}	

	private void goToRegister() throws Exception {
		access();
		new SecMenusUserSteps().selectRegistrate();
		refresh(); //Sin esto el click al link "Política de privacidad" falla la 1a vez
		new PageRegistroInitialShopSteps().checkIsPage(5);
	}
	
	private void clickPoliticaPrivacidad() {
		new PageRegistroInitialShopSteps().clickPoliticaPrivacidad();
	}
	
	private void clickCollectionAndUseLinks() {
		var pageRegistroSteps = new PageRegistroInitialShopSteps();
		pageRegistroSteps.clickLinkGivePromotions();
		keyDown(5);
		pageRegistroSteps.clickConsentPersonalInformationLink();
		keyDown(5);
	}
	
	private void checkTextoLegal() {
		checkLegalTextsVisible(PageRegistroInitialShop.make());
	}	
	
}
