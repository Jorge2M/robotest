package com.mng.robotest.domains.personalizacion.tests;

import com.mng.robotest.domains.personalizacion.steps.GetProductsSteps;
import com.mng.robotest.domains.transversal.TestBase;
import com.mng.robotest.test.beans.Linea.LineaType;
import com.mng.robotest.test.pageobject.shop.menus.KeyMenu1rstLevel;
import com.mng.robotest.test.pageobject.shop.menus.MenuTreeApp;
import com.mng.robotest.test.steps.shop.AccesoSteps;
import com.mng.robotest.test.steps.shop.galeria.PageGaleriaSteps;
import com.mng.robotest.test.steps.shop.menus.SecMenusWrapperSteps;

public class Per001 extends TestBase {

	@Override
	public void execute() throws Exception {	
		accessShop();
		selectCamisasAndCheckProducts();
		scrollAndCheckDuplicatedProducts();		
	}

	private void accessShop() throws Exception {
		new AccesoSteps().oneStep(dataTest, false);
	}
	
	private void selectCamisasAndCheckProducts() throws Exception {
		SecMenusWrapperSteps.getNew(dataTest).selectMenu1rstLevelTypeCatalog(
				MenuTreeApp.getMenuLevel1From(app, KeyMenu1rstLevel.from(LineaType.she, null, "camisas")), dataTest);
		
		new GetProductsSteps().callProductListService(LineaType.she, dataTest.pais, "prendas", "camisas", "14");
	}

	private void scrollAndCheckDuplicatedProducts() throws Exception {
		PageGaleriaSteps pageGaleriaSteps = PageGaleriaSteps.getInstance(channel, app, driver);
		pageGaleriaSteps.scrollFromFirstPage(dataTest);
	}
	
}
