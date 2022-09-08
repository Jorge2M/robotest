package com.mng.robotest.test.steps.shop.checkout.trustpay;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.mng.robotest.domains.transversal.StepBase;
import com.mng.robotest.test.pageobject.shop.checkout.trustpay.PageTrustPayResult;
import com.mng.robotest.test.utils.ImporteScreen;

public class PageTrustPayResultSteps extends StepBase {
	
	private final PageTrustPayResult pageTrustPayResult = new PageTrustPayResult();
	
	@Validation
	public ChecksTM validateIsPage(String importeTotal, String codPais) {
		ChecksTM checks = ChecksTM.getNew();
		String textHeader = "Payment In Progress";
	 	checks.add(
			"Figura el encabezamiento \"" + textHeader,
			pageTrustPayResult.headerContains(textHeader), State.Defect);
	 	
	 	checks.add(
			"Figura el importe total de la compra (" + importeTotal + ")",
			ImporteScreen.isPresentImporteInScreen(importeTotal, codPais, driver), State.Warn);
	 	
	 	checks.add(
			"Figura el botón \"continue\"",
			pageTrustPayResult.isPresentButtonContinue(), State.Defect);
	 	
	 	return checks;
	}
	
	@Step (
		description="Seleccionar el botón para continuar con el pago", 
		expected="El pago se completa correctamente")
	public void clickButtonContinue() {
		pageTrustPayResult.clickButtonContinue();
	}
}