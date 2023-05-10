package com.mng.robotest.domains.legal.tests;

import com.mng.robotest.domains.base.TestBase;
import com.mng.robotest.domains.bolsa.steps.SecBolsaSteps;
import com.mng.robotest.domains.compra.pageobjects.Page2IdentCheckout;
import com.mng.robotest.domains.compra.steps.Page2IdentCheckoutSteps;
import com.mng.robotest.domains.compra.steps.SecSoyNuevoSteps;

import static com.mng.robotest.domains.bolsa.steps.SecBolsaSteps.FluxBolsaCheckout.CONTINUAR_SIN_CUENTA;
import static com.mng.robotest.test.data.PaisShop.*;

/**
 * Control textos legales "Guest Checkout - Paso 2":
 * https://confluence.mango.com/pages/viewpage.action?spaceKey=PIUR&title=Mapeo+de+textos+legales#expand-GuestcheckoutPaso2
 *
 */
public class Leg007 extends TestBase {

	@Override
	public void execute() throws Exception {
		checkRGPD();
		renewTest();
		checkUSA();
		//renewTest();
		//checkTurquia(); //En Turquía no es posible checkearlo porque se va por el antiguo flujo bolsa->checkout
	}
	
	private void checkRGPD() throws Exception {
		dataTest.setPais(ESPANA.getPais());
		goToGuestCheckoutPage2();
		clickLinkPoliticaPrivacidad();
		checkTextoLegal();		
	}
	private void checkUSA() throws Exception {
		dataTest.setPais(USA.getPais());
		goToGuestCheckoutPage2();
		checkTextoLegal();		
	}	
//	private void checkTurquia() throws Exception {
//		dataTest.setPais(TURQUIA.getPais());
//		goToGuestCheckoutPage2();
//		checkTextoLegal();		
//	}	

	private void goToGuestCheckoutPage2() throws Exception {
		access();
		altaArticulosBolsa();
		clickComprarAndSelectContinuarSinCuenta();
		inputEmailAndContinue();
	}
	
	private void checkTextoLegal() {
		checkLegalTextsVisible(new Page2IdentCheckout());
	}	
	
	private void altaArticulosBolsa() throws Exception {
		new SecBolsaSteps().altaListaArticulosEnBolsa(getArticles(1));
	}
	
	private void clickComprarAndSelectContinuarSinCuenta() {
		new SecBolsaSteps().selectButtonComprar(CONTINUAR_SIN_CUENTA);
	}	
	
	private void inputEmailAndContinue() {
		new SecSoyNuevoSteps().inputEmailAndContinue(getUserEmail());
	}
	
	private void clickLinkPoliticaPrivacidad() {
		new Page2IdentCheckoutSteps().clickPoliticaPrivacidad();
	}
	
}
