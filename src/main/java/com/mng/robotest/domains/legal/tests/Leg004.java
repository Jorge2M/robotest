package com.mng.robotest.domains.legal.tests;

import com.mng.robotest.domains.base.TestBase;
import com.mng.robotest.domains.micuenta.pageobjects.PageMisDatos;
import com.mng.robotest.domains.micuenta.steps.PageMiCuentaSteps;
import com.mng.robotest.domains.transversal.menus.steps.SecMenusUserSteps;

import static com.mng.robotest.test.data.PaisShop.*;

/**
 * Control textos legales "Mis Datos":
 * https://confluence.mango.com/pages/viewpage.action?spaceKey=PIUR&title=Mapeo+de+textos+legales#expand-Misdatos
 *
 */
public class Leg004 extends TestBase {

	@Override
	public void execute() throws Exception {
		checkPaisesLoyalty();
		renewTest();
		//TODO en Serbia no aparecen los textos legales -> parece un error -> reportar
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
		new PageMiCuentaSteps().clickLinkMisDatos();
	}	
	
	private void checkTextoLegal() {
		checkLegalTextsVisible(new PageMisDatos());
	}

}
