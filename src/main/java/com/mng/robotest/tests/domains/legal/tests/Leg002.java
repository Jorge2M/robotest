package com.mng.robotest.tests.domains.legal.tests;

import static com.mng.robotest.testslegacy.data.PaisShop.*;

import com.mng.robotest.tests.domains.base.TestBase;
import com.mng.robotest.tests.domains.menus.steps.MenusUserSteps;
import com.mng.robotest.tests.domains.registro.pageobjects.PageRegistroInitialShop;
import com.mng.robotest.testslegacy.data.PaisShop;

/**
 * Control textos legales "Antiguo Registro":
 * https://confluence.mango.com/display/PIUR/Mapeo+de+textos+legales#expand-Antiguoregistro
 *
 */
public class Leg002 extends TestBase {

	@Override
	public void execute() throws Exception {
		checkRGPD();
		renewTest();
		checkLoyalty();
		renewTest();
		checkArabia();
		renewTest();
		checkRusia();
		renewTest();
		checkTurquia();
		renewTest();		
		checkNoRGPD();
	}
	
	private void checkRGPD() throws Exception {
		dataTest.setPais(ESTONIA.getPais());
		dataTest.setIdioma(ESTONIA.getPais().getListIdiomas().get(0)); //Inglés
		goToRegister();
		checkTextoLegal();		
	}
	private void checkLoyalty() throws Exception {
		dataTest.setPais(ANDORRA.getPais());
		dataTest.setIdioma(ANDORRA.getPais().getListIdiomas().get(1)); //Español
		goToRegister();
		checkTextoLegal();		
	}	
	private void checkArabia() throws Exception {
		dataTest.setPais(PaisShop.SAUDI_ARABIA.getPais());
		dataTest.setIdioma(PaisShop.SAUDI_ARABIA.getPais().getListIdiomas().get(1)); //Inglés
		goToRegister();
		checkTextoLegal();		
	}	
	private void checkRusia() throws Exception {
		dataTest.setPais(RUSSIA.getPais());
		dataTest.setIdioma(RUSSIA.getPais().getListIdiomas().get(0)); //Ruso
		goToRegister();
		checkTextoLegal();		
	}	
	private void checkTurquia() throws Exception {
		dataTest.setPais(TURQUIA.getPais());
		dataTest.setIdioma(TURQUIA.getPais().getListIdiomas().get(0)); //Turco
		goToRegister();
		checkTextoLegal();		
	}
	
	private void checkNoRGPD() throws Exception {
		dataTest.setPais(SERBIA.getPais());
		dataTest.setIdioma(SERBIA.getPais().getListIdiomas().get(0)); //Inglés
		goToRegister();
		checkTextoLegal();		
	}

	private void goToRegister() throws Exception {
		access();
		new MenusUserSteps().selectRegistrate();
	}
	
	private void checkTextoLegal() {
		checkLegalTextsVisible(PageRegistroInitialShop.make());
	}	
	
}
