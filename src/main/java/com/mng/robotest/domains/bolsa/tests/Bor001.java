package com.mng.robotest.domains.bolsa.tests;

import com.mng.robotest.domains.base.TestBase;
import com.mng.robotest.domains.compra.beans.ConfigCheckout;
import com.mng.robotest.domains.transversal.acceso.steps.AccesoSteps;
import com.mng.robotest.test.datastored.DataPago;
import com.mng.robotest.test.steps.navigations.shop.CheckoutFlow;
import com.mng.robotest.test.steps.navigations.shop.GaleriaSteps;
import com.mng.robotest.test.steps.navigations.shop.CheckoutFlow.From;

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
		new GaleriaSteps().selectTalla();
	}
	
	private void navigateToCheckoutAndCheckEmployee() throws Exception {
		ConfigCheckout configCheckout = ConfigCheckout.config()
				.emaiExists()
				.userIsEmployee().build();
		
		DataPago dataPago = getDataPago(configCheckout);
		new CheckoutFlow.BuilderCheckout(dataPago).build().checkout(From.BOLSA);
	}	

}
