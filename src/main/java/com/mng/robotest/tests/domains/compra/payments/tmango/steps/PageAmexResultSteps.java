package com.mng.robotest.tests.domains.compra.payments.tmango.steps;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.mng.robotest.tests.domains.base.StepBase;
import com.mng.robotest.tests.domains.compra.payments.tmango.pageobjects.PageAmexResult;
import com.mng.robotest.testslegacy.utils.ImporteScreen;

import static com.github.jorge2m.testmaker.conf.State.*;

public class PageAmexResultSteps extends StepBase {
	
	private final PageAmexResult pageAmexResult = new PageAmexResult(driver);
	
	@Validation
	public ChecksTM validateIsPageOk(String importeTotal) {
		var checks = ChecksTM.getNew();
		String codigoPais = dataTest.getCodigoPais();
		int seconds = 2;
	 	checks.add(
			"Aparece una página con un mensaje de OK " + getLitSecondsWait(seconds),
			pageAmexResult.isResultOkUntil(seconds));
	 	
	 	checks.add(
			"Aparece el importe de la operación " + importeTotal,
			ImporteScreen.isPresentImporteInScreen(importeTotal, codigoPais, driver), Warn);
	 	
	 	checks.add(
			"Aparece un botón \"CONTINUAR\"",
			pageAmexResult.isPresentContinueButton());
	 	
	 	return checks;
	}
	
	@Step (
		description="Seleccionamos el botón \"Continuar\"", 
		expected="Aparece la página de Mango de resultado OK del pago")
	public void clickContinuarButton() {
		pageAmexResult.clickContinuarButton();
	}
}