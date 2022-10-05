package com.mng.robotest.domains.bolsa.tests;

import com.mng.robotest.domains.bolsa.steps.SecBolsaSteps;
import com.mng.robotest.domains.compra.beans.ConfigCheckout;
import com.mng.robotest.domains.transversal.TestBase;
import com.mng.robotest.test.datastored.DataPago;
import com.mng.robotest.test.steps.navigations.shop.CheckoutFlow;
import com.mng.robotest.test.steps.navigations.shop.CheckoutFlow.From;
import com.mng.robotest.test.steps.shop.AccesoSteps;

public class Bor002 extends TestBase {

	public Bor002() {
		dataTest.setUserRegistered(true);
	}
	
	@Override
	public void execute() throws Exception {
		accessAndSelectMenuVestidos();
		addBagArticleWithColors();
		navigateToCheckout();		
	}

	private void accessAndSelectMenuVestidos() throws Exception {
		new AccesoSteps().manySteps();
		clickMenu("Vestidos");
	}
	
	private void addBagArticleWithColors() throws Exception {
		new SecBolsaSteps().altaArticlosConColores(1);
	}
	
	private void navigateToCheckout() throws Exception {
		ConfigCheckout configCheckout = ConfigCheckout.config()
				.emaiExists().build();
		
		DataPago dataPago = getDataPago(configCheckout);
		new CheckoutFlow.BuilderCheckout(dataPago).build().checkout(From.BOLSA);
	}

}
