package com.mng.robotest.test.steps.shop.checkout.tmango;

import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.mng.robotest.test.pageobject.shop.checkout.tmango.PageAmexResult;
import com.mng.robotest.test.utils.ImporteScreen;

public class PageAmexResultSteps {
	
	private final PageAmexResult pageAmexResult;
	
	public PageAmexResultSteps(WebDriver driver) {
		pageAmexResult = new PageAmexResult(driver);
	}

	@Validation
	public ChecksTM validateIsPageOk(String importeTotal, String codigoPais) {
		ChecksTM checks = ChecksTM.getNew();
		int seconds = 2;
	 	checks.add(
			"Aparece una página con un mensaje de OK (lo esperamos hasta " + seconds + " segundos)",
			pageAmexResult.isResultOkUntil(seconds), State.Defect); 
	 	checks.add(
			"Aparece el importe de la operación " + importeTotal,
			ImporteScreen.isPresentImporteInScreen(importeTotal, codigoPais, pageAmexResult.driver), State.Warn); 
	 	checks.add(
			"Aparece un botón \"CONTINUAR\"",
			pageAmexResult.isPresentContinueButton(), State.Defect);
	 	return checks;
	}
	
	@Step (
		description="Seleccionamos el botón \"Continuar\"", 
		expected="Aparece la página de Mango de resultado OK del pago")
	public void clickContinuarButton() {
		pageAmexResult.clickContinuarButton();
	}
}