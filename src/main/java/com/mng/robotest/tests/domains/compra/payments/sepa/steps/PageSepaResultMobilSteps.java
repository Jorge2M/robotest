package com.mng.robotest.tests.domains.compra.payments.sepa.steps;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.mng.robotest.tests.domains.compra.payments.sepa.pageobjects.PageSepaResultMobil;
import com.mng.robotest.testslegacy.utils.ImporteScreen;

import static com.github.jorge2m.testmaker.conf.State.*;

public class PageSepaResultMobilSteps {
	
	private final PageSepaResultMobil pageSepaResultMobil = new PageSepaResultMobil();
	
	@Validation
	public ChecksTM validateIsPage(String importeTotal, String codPais) {
		var checks = ChecksTM.getNew();
		checks.add(
			"Aparece la página de resultado de SEPA para móvil",
			pageSepaResultMobil.isPage(), Warn);	
		
		checks.add(
			"Aparece el importe de la compra: " + importeTotal,
			ImporteScreen.isPresentImporteInScreen(importeTotal, codPais, pageSepaResultMobil.driver), 
			Warn);
		
		return checks;
	}
	
	@Step (
		description="Seleccionamos el botón para Pagar", 
		expected="Aparece la página de resultado OK del pago en Mango")
	public void clickButtonPagar() {
		pageSepaResultMobil.clickButtonPay();
	}
}
