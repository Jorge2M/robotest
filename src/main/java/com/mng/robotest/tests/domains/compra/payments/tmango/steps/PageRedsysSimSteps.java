package com.mng.robotest.tests.domains.compra.payments.tmango.steps;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.mng.robotest.tests.domains.base.StepBase;
import com.mng.robotest.tests.domains.compra.payments.tmango.pageobjects.PageRedsysSim;

public class PageRedsysSimSteps extends StepBase {

	private final PageRedsysSim pageRedsysSim = new PageRedsysSim();
	
	@Validation(
		description="Aparece la página de Simulador Pago RedSys")
	public boolean checkPage(int seconds) {
		return pageRedsysSim.isPage(seconds);
	}
	
	@Step (
		description="Seleccionamos el botón \"Enviar\"", 
		expected="Aparece la página de resultado Ok de la pasarela Redsys")
	public void clickEnviar(String cip, String importeTotal) {
		pageRedsysSim.clickEnviar();
		new PageAmexResultSteps().validateIsPageOk(importeTotal);
	}
}
