package com.mng.robotest.tests.domains.registro.tests;

import static com.mng.robotest.tests.domains.bolsa.steps.BolsaSteps.FluxBolsaCheckout.*;

import com.mng.robotest.tests.domains.base.TestBase;
import com.mng.robotest.tests.domains.bolsa.steps.BolsaSteps;
import com.mng.robotest.tests.domains.compra.steps.Page2IdentCheckoutSteps;
import com.mng.robotest.tests.domains.compra.steps.PageResultPagoSteps;
import com.mng.robotest.tests.domains.registro.beans.DataNewRegister;
import com.mng.robotest.tests.domains.registro.steps.PageRegistroInitialShopSteps;

public class Reg008 extends TestBase {
	
	@Override
	public void execute() throws Exception {
		access();
		altaArticulosBolsa();
		clickComprarAndSelectRegistro();
		register();
		if (!isPRO()) {
			executeVisaPayment();
			clickVerMisCompras();
			checkNoElementsInBag();
		}
	}

	private void altaArticulosBolsa() throws Exception {
		new BolsaSteps().altaListaArticulosEnBolsa(getArticles(1));
	}
	
	private void clickComprarAndSelectRegistro() {
		new BolsaSteps().selectButtonComprar(REGISTRO);
	}
	
	private void register() {
		var pgRegistroInitialSteps = new PageRegistroInitialShopSteps();
		var dataNewRegister = DataNewRegister.makeDefault(dataTest.getPais());
		pgRegistroInitialSteps.inputData(dataNewRegister);
		pgRegistroInitialSteps.clickCreateAccountButtonFromBolsa();
		
		var page2IdentCheckoutSteps = new Page2IdentCheckoutSteps();
		page2IdentCheckoutSteps.inputDataPorDefecto(dataTest.getUserConnected(), false);
		page2IdentCheckoutSteps.clickContinuar(false);
	}
	
	private void clickVerMisCompras() {
		new PageResultPagoSteps().selectMisCompras();
	}
	
	private void checkNoElementsInBag() {
		new BolsaSteps().checkBolsaIsVoid();
	}	
	
}
