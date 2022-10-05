package com.mng.robotest.domains.personalizacion.tests;

import com.mng.robotest.domains.personalizacion.steps.GetProductsSteps;
import com.mng.robotest.domains.transversal.TestBase;
import com.mng.robotest.domains.transversal.menus.pageobjects.LineaWeb.LineaType;
import com.mng.robotest.test.steps.shop.galeria.PageGaleriaSteps;

public class Per001 extends TestBase {

	@Override
	public void execute() throws Exception {	
		access();
		selectCamisasAndCheckProducts();
		scrollAndCheckDuplicatedProducts();		
	}

	private void selectCamisasAndCheckProducts() throws Exception {
		clickMenu("Camisas");
		new GetProductsSteps().callProductListService(LineaType.she, "prendas", "camisas", "14");
	}

	private void scrollAndCheckDuplicatedProducts() throws Exception {
		new PageGaleriaSteps().scrollFromFirstPage();
	}
	
}
