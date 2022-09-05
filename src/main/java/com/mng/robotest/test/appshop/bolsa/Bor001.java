package com.mng.robotest.test.appshop.bolsa;

import com.mng.robotest.domains.compra.beans.ConfigCheckout;
import com.mng.robotest.domains.transversal.TestBase;
import com.mng.robotest.test.beans.Linea.LineaType;
import com.mng.robotest.test.datastored.DataBag;
import com.mng.robotest.test.datastored.DataPago;
import com.mng.robotest.test.pageobject.shop.menus.KeyMenu1rstLevel;
import com.mng.robotest.test.pageobject.shop.menus.Menu1rstLevel;
import com.mng.robotest.test.pageobject.shop.menus.MenuTreeApp;
import com.mng.robotest.test.steps.navigations.shop.CheckoutFlow;
import com.mng.robotest.test.steps.navigations.shop.GaleriaNavigationsSteps;
import com.mng.robotest.test.steps.navigations.shop.CheckoutFlow.From;
import com.mng.robotest.test.steps.shop.AccesoSteps;
import com.mng.robotest.test.steps.shop.menus.SecMenusWrapperSteps;

public class Bor001 extends TestBase {

	@Override
	public void execute() throws Exception {
		dataTest.userRegistered = false;
		new AccesoSteps().manySteps(dataTest);
		SecMenusWrapperSteps secMenusSteps = new SecMenusWrapperSteps();
		Menu1rstLevel menuVestidos = MenuTreeApp.getMenuLevel1From(app, KeyMenu1rstLevel.from(LineaType.she, null, "vestidos"));
		secMenusSteps.accesoMenuXRef(menuVestidos);
		DataBag dataBag = new GaleriaNavigationsSteps().selectArticleAvailableFromGaleria(dataTest.pais);

		ConfigCheckout configCheckout = ConfigCheckout.config()
				.emaiExists()
				.userIsEmployee().build();
		
		DataPago dataPago = new DataPago(configCheckout);
		dataPago.getDataPedido().setDataBag(dataBag);
		
		new CheckoutFlow.BuilderCheckout(dataPago).build().checkout(From.BOLSA);		
	}	

}
