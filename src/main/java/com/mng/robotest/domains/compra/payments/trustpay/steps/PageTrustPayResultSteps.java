package com.mng.robotest.domains.compra.payments.trustpay.steps;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.mng.robotest.domains.base.StepBase;
import com.mng.robotest.domains.compra.payments.trustpay.pageobjects.PageTrustPayResult;
import com.mng.robotest.test.utils.ImporteScreen;

import static com.github.jorge2m.testmaker.conf.State.*;

public class PageTrustPayResultSteps extends StepBase {
	
	private final PageTrustPayResult pageTrustPayResult = new PageTrustPayResult();
	
	@Validation
	public ChecksTM checkIsPage(String importeTotal) {
		var checks = ChecksTM.getNew();
		String textHeader = "Payment In Progress";
	 	checks.add(
			"Figura el encabezamiento \"" + textHeader,
			pageTrustPayResult.headerContains(textHeader));
	 	
	 	String codPais = dataTest.getCodigoPais();
	 	checks.add(
			"Figura el importe total de la compra (" + importeTotal + ")",
			ImporteScreen.isPresentImporteInScreen(importeTotal, codPais, driver), Warn);
	 	
	 	checks.add(
			"Figura el botón \"continue\"",
			pageTrustPayResult.isPresentButtonContinue());
	 	
	 	return checks;
	}
	
	@Step (
		description="Seleccionar el botón para continuar con el pago", 
		expected="El pago se completa correctamente")
	public void clickButtonContinue() {
		pageTrustPayResult.clickButtonContinue();
	}
}
