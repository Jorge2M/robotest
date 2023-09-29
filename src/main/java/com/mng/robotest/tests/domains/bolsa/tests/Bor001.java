package com.mng.robotest.tests.domains.bolsa.tests;

import com.mng.robotest.tests.domains.base.TestBase;
import com.mng.robotest.tests.domains.compra.beans.ConfigCheckout;
import com.mng.robotest.tests.domains.galeria.steps.PageGaleriaSteps;
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
		new PageGaleriaSteps().selectTallaAvailable();
	}
	
	private void navigateToCheckoutAndCheckEmployee() throws Exception {
		ConfigCheckout configCheckout = ConfigCheckout.config()
				.emaiExists()
				.userIsEmployee().build();
		
		var dataPago = getDataPago(configCheckout);
		new CheckoutFlow.BuilderCheckout(dataPago).build().checkout(From.BOLSA);
	}	

}
