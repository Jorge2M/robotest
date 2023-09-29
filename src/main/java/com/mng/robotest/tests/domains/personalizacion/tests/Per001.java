package com.mng.robotest.tests.domains.personalizacion.tests;

import com.mng.robotest.tests.domains.base.TestBase;
import com.mng.robotest.tests.domains.galeria.steps.PageGaleriaSteps;
import com.mng.robotest.tests.domains.personalizacion.steps.GetProductsSteps;
import com.mng.robotest.tests.domains.transversal.menus.pageobjects.LineaWeb.LineaType;

public class Per001 extends TestBase {

	@Override
	public void execute() throws Exception {	
		access();
		selectCamisasAndCheckProducts();
		scrollAndCheckDuplicatedProducts();		
	}

	private void selectCamisasAndCheckProducts() throws Exception {
		clickMenu("Camisas");
		new GetProductsSteps().callProductListService(LineaType.SHE, "prendas", "camisas", "14");
	}

	private void scrollAndCheckDuplicatedProducts() throws Exception {
		new PageGaleriaSteps().scrollFromFirstPage();
	}
	
}
