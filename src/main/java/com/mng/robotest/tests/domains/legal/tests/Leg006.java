package com.mng.robotest.tests.domains.legal.tests;

import static com.mng.robotest.testslegacy.data.PaisShop.*;

import com.mng.robotest.tests.domains.base.TestBase;
import com.mng.robotest.tests.domains.menus.steps.SecMenusUserSteps;
import com.mng.robotest.tests.domains.micuenta.pageobjects.PageMisDatos;
import com.mng.robotest.tests.domains.micuenta.steps.MiCuentaSteps;

/**
 * Control textos legales "Mis Datos":
 * https://confluence.mango.com/pages/viewpage.action?spaceKey=PIUR&title=Mapeo+de+textos+legales#expand-Misdatos
 *
 */
public class Leg006 extends TestBase {

	@Override
	public void execute() throws Exception {
		checkPaisesLoyalty();
		renewTest();
		checkTextoComunRGPD();
	}
	
	private void checkPaisesLoyalty() throws Exception {
		dataTest.setPais(ESPANA.getPais()); //Loyalty Country
		checkTextosLegales();
	}
	private void checkTextoComunRGPD() throws Exception {
		dataTest.setPais(SERBIA.getPais()); //No Loyalty Country
		checkTextosLegales();
	}
	
	private void checkTextosLegales() throws Exception {
		accessAndLogin();
		goToMisDatos();
		checkTextoLegal();
	}
	
	private void goToMisDatos() {
		new SecMenusUserSteps().clickMenuMiCuenta();
		new MiCuentaSteps().clickLinkMisDatos();
	}	
	
	private void checkTextoLegal() {
		checkLegalTextsVisible(new PageMisDatos());
	}

}
