package com.mng.robotest.test.steps.shop.checkout.trustpay;

import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.mng.robotest.test.pageobject.shop.checkout.trustpay.PageTrustPayResult;
import com.mng.robotest.test.utils.ImporteScreen;

public class PageTrustPayResultSteps {
	
	@Validation
	public static ChecksTM validateIsPage(String importeTotal, String codPais, WebDriver driver) {
		ChecksTM checks = ChecksTM.getNew();
		String textHeader = "Payment In Progress";
	 	checks.add(
			"Figura el encabezamiento \"" + textHeader,
			PageTrustPayResult.headerContains(textHeader, driver), State.Defect); 
	 	checks.add(
			"Figura el importe total de la compra (" + importeTotal + ")",
			ImporteScreen.isPresentImporteInScreen(importeTotal, codPais, driver), State.Warn); 
	 	checks.add(
			"Figura el botón \"continue\"",
			PageTrustPayResult.isPresentButtonContinue(driver), State.Defect); 
	 	return checks;
	}
	
	@Step (
		description="Seleccionar el botón para continuar con el pago", 
		expected="El pago se completa correctamente")
	public static void clickButtonContinue(WebDriver driver) {
		PageTrustPayResult.clickButtonContinue(driver);
	}
}
