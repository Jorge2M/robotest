package com.mng.robotest.tests.domains.bolsa.tests;

import com.mng.robotest.tests.domains.base.TestBase;
import com.mng.robotest.tests.domains.bolsa.steps.SecBolsaSteps;
import com.mng.robotest.tests.domains.compra.beans.ConfigCheckout;
import com.mng.robotest.tests.domains.transversal.acceso.steps.AccesoSteps;
import com.mng.robotest.testslegacy.datastored.DataPago;
import com.mng.robotest.testslegacy.steps.navigations.shop.CheckoutFlow;
import com.mng.robotest.testslegacy.steps.navigations.shop.CheckoutFlow.From;

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
