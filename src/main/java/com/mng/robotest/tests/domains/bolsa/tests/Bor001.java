package com.mng.robotest.tests.domains.bolsa.tests;

import com.mng.robotest.tests.domains.base.TestBase;
import com.mng.robotest.tests.domains.compra.beans.ConfigCheckout;
import com.mng.robotest.tests.domains.galeria.steps.GaleriaSteps;
import com.mng.robotest.tests.domains.transversal.acceso.steps.AccesoSteps;
import com.mng.robotest.testslegacy.steps.navigations.shop.CheckoutFlow;
import com.mng.robotest.testslegacy.steps.navigations.shop.CheckoutFlow.From;

public class Bor001 extends TestBase {

	@Override
	public void execute() throws Exception {
		accessAndSelectMenuVestidos();
		selectTallaArticle();
		navigateToCheckoutAndCheckEmployee();
	}
	
	private void accessAndSelectMenuVestidos() throws Exception {
		new AccesoSteps().manySteps();
		clickMenu("Vestidos");
	}	

	private void selectTallaArticle() throws Exception {
		new GaleriaSteps().selectTallaAvailable();
	}
	
	private void navigateToCheckoutAndCheckEmployee() throws Exception {
		var configCheckout = ConfigCheckout.config()
				.emaiExists()
				.userIsEmployee().build();
		
		dataTest.setDataPago(configCheckout);
		new CheckoutFlow.BuilderCheckout(dataTest.getDataPago()).build()
			.checkout(From.BOLSA);
	}	

}
