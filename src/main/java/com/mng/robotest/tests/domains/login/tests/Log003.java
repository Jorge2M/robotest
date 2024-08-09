package com.mng.robotest.tests.domains.login.tests;

import static com.mng.robotest.tests.domains.bolsa.steps.BolsaSteps.FluxBolsaCheckout.*;

import com.mng.robotest.tests.domains.base.TestBase;
import com.mng.robotest.tests.domains.bolsa.steps.BolsaSteps;
import com.mng.robotest.tests.domains.compra.steps.Page1IdentCheckoutSteps;
import com.mng.robotest.tests.domains.compra.steps.PageResultPagoSteps;

public class Log003 extends TestBase {
	
	@Override
	public void execute() throws Exception {
		access();
		altaArticulosBolsa();
		clickComprarAndSelectIniciarSesion();
		iniciarSesion();
		if (!isPRO()) {
			executeVisaPayment();
			clickVerMisCompras();
			checkNoElementsInBag();
		}
	}

	private void altaArticulosBolsa() throws Exception {
		new BolsaSteps().altaListaArticulosEnBolsa(getArticles(1));
	}
	
	private void clickComprarAndSelectIniciarSesion() {
		new BolsaSteps().selectButtonComprar(INICIAR_SESION);
	}
	
	private void iniciarSesion() {
		//TODO [flux-bolsa] reactivar cuando se reactive el nuevo flujo
		//new PageIniciarSesionBolsaMobileSteps().login();
		new Page1IdentCheckoutSteps().login();
	}
	
	private void clickVerMisCompras() {
		new PageResultPagoSteps().selectMisCompras();
	}
	
	private void checkNoElementsInBag() {
		new BolsaSteps().checkBolsaIsVoid();
	}

}
