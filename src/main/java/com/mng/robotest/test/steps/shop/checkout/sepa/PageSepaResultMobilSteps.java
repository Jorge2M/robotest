package com.mng.robotest.test.steps.shop.checkout.sepa;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.mng.robotest.test.pageobject.shop.checkout.sepa.PageSepaResultMobil;
import com.mng.robotest.test.utils.ImporteScreen;


public class PageSepaResultMobilSteps {
	
	private final PageSepaResultMobil pageSepaResultMobil = new PageSepaResultMobil();
	
	@Validation
	public ChecksTM validateIsPage(String importeTotal, String codPais) {
		ChecksTM checks = ChecksTM.getNew();
		checks.add(
			"Aparece la página de resultado de SEPA para móvil",
			pageSepaResultMobil.isPage(), State.Warn);	
		checks.add(
			"Aparece el importe de la compra: " + importeTotal,
			ImporteScreen.isPresentImporteInScreen(importeTotal, codPais, pageSepaResultMobil.driver), 
			State.Warn);	
		return checks;
	}
	
	@Step (
		description="Seleccionamos el botón para Pagar", 
		expected="Aparece la página de resultado OK del pago en Mango")
	public void clickButtonPagar() throws Exception {
		pageSepaResultMobil.clickButtonPay();
	}
}
