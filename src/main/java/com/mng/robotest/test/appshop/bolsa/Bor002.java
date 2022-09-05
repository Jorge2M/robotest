package com.mng.robotest.test.appshop.bolsa;

import com.mng.robotest.domains.compra.beans.ConfigCheckout;
import com.mng.robotest.domains.transversal.TestBase;
import com.mng.robotest.test.beans.Linea.LineaType;
import com.mng.robotest.test.datastored.DataBag;
import com.mng.robotest.test.datastored.DataPago;
import com.mng.robotest.test.getdata.usuarios.GestorUsersShop;
import com.mng.robotest.test.getdata.usuarios.UserShop;
import com.mng.robotest.test.pageobject.shop.menus.KeyMenu1rstLevel;
import com.mng.robotest.test.pageobject.shop.menus.Menu1rstLevel;
import com.mng.robotest.test.pageobject.shop.menus.MenuTreeApp;
import com.mng.robotest.test.steps.navigations.shop.CheckoutFlow;
import com.mng.robotest.test.steps.navigations.shop.CheckoutFlow.From;
import com.mng.robotest.test.steps.shop.AccesoSteps;
import com.mng.robotest.test.steps.shop.SecBolsaSteps;
import com.mng.robotest.test.steps.shop.menus.SecMenusWrapperSteps;

public class Bor002 extends TestBase {

	@Override
	public void execute() throws Exception {
		UserShop userShop = GestorUsersShop.checkoutBestUserForNewTestCase();
		dataTest.userConnected = userShop.user;
		dataTest.passwordUser = userShop.password;
		dataTest.userRegistered = true;
		DataBag dataBag = new DataBag();
		
		new AccesoSteps().manySteps(dataTest);
		SecMenusWrapperSteps secMenusSteps = new SecMenusWrapperSteps();
		Menu1rstLevel menuVestidos = MenuTreeApp.getMenuLevel1From(app, KeyMenu1rstLevel.from(LineaType.she, null, "vestidos"));
		secMenusSteps.accesoMenuXRef(menuVestidos);
		
		SecBolsaSteps secBolsaSteps = new SecBolsaSteps();
		secBolsaSteps.altaArticlosConColores(1, dataBag);
		
		//Hasta página de Checkout
		ConfigCheckout configCheckout = ConfigCheckout.config()
				.emaiExists().build();
		
		DataPago dataPago = getDataPago(configCheckout, dataBag);
		new CheckoutFlow.BuilderCheckout(dataPago).build().checkout(From.BOLSA);		
	}

}
