package com.mng.robotest.tests.domains.compra.payments.paytrail.steps;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.mng.robotest.tests.domains.base.StepBase;
import com.mng.robotest.tests.domains.compra.payments.paytrail.pageobjects.PagePaytrailResultadoOk;
import com.mng.robotest.testslegacy.utils.ImporteScreen;

import static com.github.jorge2m.testmaker.conf.State.*;

public class PagePaytrailResultadoOkSteps extends StepBase {
	
	private final PagePaytrailResultadoOk pagePaytrailResultadoOk = new PagePaytrailResultadoOk();
	
	@Validation
	public ChecksTM validateIsPage(String importeTotal, String codPais) {
		var checks = ChecksTM.getNew();
	   	checks.add(
			"Aparece la página de resultado Ok de Paytrail",
			pagePaytrailResultadoOk.isPage());
	   	
	   	checks.add(
			"Aparece el importe de la compra: " + importeTotal,
			ImporteScreen.isPresentImporteInScreen(importeTotal, codPais, driver), WARN);
	   	
	   	return checks;
	}
	
	@Step (
		description="Click el botón para volver a Mango", 
		expected="Aparece la página de resultado Ok de Mango")
	public void clickVolverAMangoButton() {
		pagePaytrailResultadoOk.clickVolverAMangoButton();
	}
}
