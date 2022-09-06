package com.mng.robotest.test.steps.shop.checkout.ideal;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.conf.State;
import com.mng.robotest.domains.transversal.StepBase;
import com.mng.robotest.test.pageobject.shop.checkout.ideal.PageIdealSimulador;

public class PageIdealSimuladorSteps extends StepBase {

	private final PageIdealSimulador pageIdealSimulador = new PageIdealSimulador();
	
	@Validation (
		description="Aparece la página de simulación de Ideal",
		level=State.Defect)
	public boolean validateIsPage() { 
		return pageIdealSimulador.isPage();
	}

	@Step (
		description="Seleccionar el botón \"Continuar\"", 
		expected="El pago se realiza correctamente")
	public void clickContinueButton() {
		pageIdealSimulador.clickButtonContinue();
	}
}
