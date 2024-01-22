package com.mng.robotest.tests.domains.registro.tests;

import static com.mng.robotest.tests.domains.bolsa.steps.SecBolsaSteps.FluxBolsaCheckout.*;

import com.mng.robotest.tests.domains.base.TestBase;
import com.mng.robotest.tests.domains.bolsa.steps.SecBolsaSteps;
import com.mng.robotest.tests.domains.registro.beans.DataNewRegister;
import com.mng.robotest.tests.domains.registro.steps.PageRegistroInitialShopSteps;

public class Reg008 extends TestBase {
	
	//TODO gestionar el caso de PRO
	
	@Override
	public void execute() throws Exception {
		access();
		altaArticulosBolsa();
		clickComprarAndSelectRegistro();
		register();
		System.out.println("Fin");
	}

	private void altaArticulosBolsa() throws Exception {
		new SecBolsaSteps().altaListaArticulosEnBolsa(getArticles(1));
	}
	
	private void clickComprarAndSelectRegistro() {
		new SecBolsaSteps().selectButtonComprar(REGISTRO);
	}
	
	private void register() {
		var pgRegistroInitialSteps = new PageRegistroInitialShopSteps();
		var dataNewRegister = DataNewRegister.makeDefault(dataTest.getPais());
		pgRegistroInitialSteps.inputData(dataNewRegister);
		pgRegistroInitialSteps.clickCreateAccountButtonFromBolsa();
		
		//new Page2IdentCheckoutSteps().checkIsPage(false, 2);
	}
	
}
