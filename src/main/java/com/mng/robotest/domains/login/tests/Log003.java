package com.mng.robotest.domains.login.tests;

import com.mng.robotest.domains.base.TestBase;
import com.mng.robotest.domains.bolsa.steps.PageIniciarSesionBolsaMobileSteps;
import com.mng.robotest.domains.bolsa.steps.SecBolsaSteps;
import com.mng.robotest.domains.compra.steps.PageResultPagoSteps;

import static com.mng.robotest.domains.bolsa.steps.SecBolsaSteps.FluxBolsaCheckout.*;

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
		new SecBolsaSteps().altaListaArticulosEnBolsa(getArticles(1));
	}
	
	private void clickComprarAndSelectIniciarSesion() {
		new SecBolsaSteps().selectButtonComprar(INICIAR_SESION);
	}
	
	private void iniciarSesion() {
		new PageIniciarSesionBolsaMobileSteps().login();
	}
	
	private void clickVerMisCompras() {
		new PageResultPagoSteps().selectMisCompras();
	}
	
	private void checkNoElementsInBag() {
		new SecBolsaSteps().checkBolsaIsVoid();
	}

}
