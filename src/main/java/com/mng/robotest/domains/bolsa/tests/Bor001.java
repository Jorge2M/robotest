package com.mng.robotest.domains.bolsa.tests;

import com.mng.robotest.domains.compra.beans.ConfigCheckout;
import com.mng.robotest.domains.transversal.TestBase;
import com.mng.robotest.test.datastored.DataBag;
import com.mng.robotest.test.datastored.DataPago;
import com.mng.robotest.test.steps.navigations.shop.CheckoutFlow;
import com.mng.robotest.test.steps.navigations.shop.GaleriaNavigationsSteps;
import com.mng.robotest.test.steps.navigations.shop.CheckoutFlow.From;
import com.mng.robotest.test.steps.shop.AccesoSteps;

public class Bor001 extends TestBase {

	@Override
	public void execute() throws Exception {
		accessAndSelectMenuVestidos();
		DataBag dataBag = selectTallaArticle();
		navigateToCheckoutAndCheckEmployee(dataBag);		
	}

	private void accessAndSelectMenuVestidos() throws Exception {
		new AccesoSteps().manySteps(dataTest);
		clickMenu("vestidos", TypeSelectMenu.XREF);
	}	

	private DataBag selectTallaArticle() throws Exception {
		return new GaleriaNavigationsSteps().selectTalla(dataTest.pais);
	}
	
	private void navigateToCheckoutAndCheckEmployee(DataBag dataBag) throws Exception {
		ConfigCheckout configCheckout = ConfigCheckout.config()
				.emaiExists()
				.userIsEmployee().build();
		
		DataPago dataPago = getDataPago(configCheckout, dataBag);
		new CheckoutFlow.BuilderCheckout(dataPago).build().checkout(From.BOLSA);
	}	

}
