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
		ChecksTM validations = ChecksTM.getNew();
		int maxSeconds = 2;
	 	validations.add(
			"Aparece una página con un mensaje de OK (lo esperamos hasta " + maxSeconds + " segundos)",
			pageAmexResult.isResultOkUntil(maxSeconds), State.Defect); 
	 	validations.add(
			"Aparece el importe de la operación " + importeTotal,
			ImporteScreen.isPresentImporteInScreen(importeTotal, codigoPais, pageAmexResult.driver), State.Warn); 
	 	validations.add(
			"Aparece un botón \"CONTINUAR\"",
			pageAmexResult.isPresentContinueButton(), State.Defect);
	 	return validations;
	}
	
	@Step (
		description="Seleccionamos el botón \"Continuar\"", 
		expected="Aparece la página de Mango de resultado OK del pago")
	public void clickContinuarButton() {
		pageAmexResult.clickContinuarButton();
	}
}