package com.mng.robotest.domains.bolsa.tests;

import com.mng.robotest.domains.bolsa.steps.SecBolsaSteps;
import com.mng.robotest.domains.compra.beans.ConfigCheckout;
import com.mng.robotest.domains.transversal.TestBase;
import com.mng.robotest.test.datastored.DataBag;
import com.mng.robotest.test.datastored.DataPago;
import com.mng.robotest.test.getdata.usuarios.GestorUsersShop;
import com.mng.robotest.test.getdata.usuarios.UserShop;
import com.mng.robotest.test.steps.navigations.shop.CheckoutFlow;
import com.mng.robotest.test.steps.navigations.shop.CheckoutFlow.From;
import com.mng.robotest.test.steps.shop.AccesoSteps;

public class Bor002 extends TestBase {

	public Bor002() {
		UserShop userShop = GestorUsersShop.checkoutBestUserForNewTestCase();
		dataTest.userConnected = userShop.user;
		dataTest.passwordUser = userShop.password;
		dataTest.userRegistered = true;
	}
	
	@Override
	public void execute() throws Exception {
		accessAndSelectMenuVestidos();
		DataBag dataBag = addBagArticleWithColors();
		navigateToCheckout(dataBag);		
	}

	private void accessAndSelectMenuVestidos() throws Exception {
		new AccesoSteps().manySteps(dataTest);
		clickMenu("vestidos", TypeSelectMenu.XREF);
	}
	
	private DataBag addBagArticleWithColors() throws Exception {
		return new SecBolsaSteps().altaArticlosConColores(1);
	}
	
	private void navigateToCheckout(DataBag dataBag) throws Exception {
		ConfigCheckout configCheckout = ConfigCheckout.config()
				.emaiExists().build();
		
		DataPago dataPago = getDataPago(configCheckout, dataBag);
		new CheckoutFlow.BuilderCheckout(dataPago).build().checkout(From.BOLSA);
	}

}
