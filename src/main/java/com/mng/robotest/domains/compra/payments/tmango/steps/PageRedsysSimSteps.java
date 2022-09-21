package com.mng.robotest.domains.compra.payments.tmango.steps;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.conf.State;
import com.mng.robotest.domains.compra.payments.tmango.pageobjects.PageRedsysSim;
import com.mng.robotest.domains.transversal.StepBase;

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
