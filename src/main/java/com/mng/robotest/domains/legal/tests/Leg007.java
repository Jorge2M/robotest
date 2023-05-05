package com.mng.robotest.domains.legal.tests;

import com.mng.robotest.domains.base.TestBase;
import com.mng.robotest.domains.bolsa.steps.SecBolsaSteps;
import com.mng.robotest.domains.compra.pageobjects.secsoynuevo.SecSoyNuevoMobileNew;

import static com.mng.robotest.domains.bolsa.steps.SecBolsaSteps.FluxBolsaCheckout.CONTINUAR_SIN_CUENTA;

/**
 * Control textos legales "Guest Checkout - Paso 1":
 * https://confluence.mango.com/pages/viewpage.action?spaceKey=PIUR&title=Mapeo+de+textos+legales#expand-GuestcheckoutPaso2
 *
 */
public class Leg007 extends TestBase {

	@Override
	public void execute() throws Exception {
		goToGuestCheckoutPage1();
		checkTextoLegal();
	}

	private void goToGuestCheckoutPage1() throws Exception {
		access();
		altaArticulosBolsa();
		clickComprarAndSelectContinuarSinCuenta();
	}
	
	private void checkTextoLegal() {
		checkLegalTextsVisible(new SecSoyNuevoMobileNew());
	}	
	
	private void altaArticulosBolsa() throws Exception {
		new SecBolsaSteps().altaListaArticulosEnBolsa(getArticles(1));
	}
	
	private void clickComprarAndSelectContinuarSinCuenta() {
		new SecBolsaSteps().selectButtonComprar(CONTINUAR_SIN_CUENTA);
	}	
	

	
}
