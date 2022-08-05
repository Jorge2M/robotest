package com.mng.robotest.test.steps.shop.checkout.paytrail;

import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.mng.robotest.test.pageobject.shop.checkout.paytrail.PagePaytrailResultadoOk;
import com.mng.robotest.test.utils.ImporteScreen;

public class PagePaytrailResultadoOkSteps {
	
	@Validation
	public static ChecksTM validateIsPage(String importeTotal, String codPais, WebDriver driver) {
		ChecksTM checks = ChecksTM.getNew();
	   	checks.add(
			"Aparece la página de resultado Ok de Paytrail",
			PagePaytrailResultadoOk.isPage(driver), State.Defect);
	   	checks.add(
			"Aparece el importe de la compra: " + importeTotal,
			ImporteScreen.isPresentImporteInScreen(importeTotal, codPais, driver), State.Warn);
	   	return checks;
	}
	
	@Step (
		description="Click el botón para volver a Mango", 
		expected="Aparece la página de resultado Ok de Mango")
	public static void clickVolverAMangoButton(WebDriver driver) {
		PagePaytrailResultadoOk.clickVolverAMangoButton(driver);
	}
}
