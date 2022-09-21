package com.mng.robotest.test.steps.shop.checkout.tmango;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.conf.State;
import com.mng.robotest.domains.transversal.StepBase;
import com.mng.robotest.test.pageobject.shop.checkout.tmango.PageRedsysSim;

public class PageRedsysSimSteps extends StepBase {

	private final PageRedsysSim pageRedsysSim = new PageRedsysSim();
	
	@Validation(
		description="Aparece la página de Simulador Pago RedSys",
		level=State.Defect)
	public boolean checkPage() {
		return pageRedsysSim.isPage();
	}
	
	@Step (
		description="Seleccionamos el botón \"Enviar\"", 
		expected="Aparece la página de resultado Ok de la pasarela Redsys")
	public void clickEnviar(String CIP, String importeTotal) {
		pageRedsysSim.clickEnviar();
		new PageAmexResultSteps().validateIsPageOk(importeTotal);
	}
}
