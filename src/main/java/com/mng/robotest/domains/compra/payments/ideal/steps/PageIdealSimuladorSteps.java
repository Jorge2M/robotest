package com.mng.robotest.domains.compra.payments.ideal.steps;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.mng.robotest.domains.base.StepBase;
import com.mng.robotest.domains.compra.payments.ideal.pageobjects.PageIdealSimulador;

import static com.github.jorge2m.testmaker.conf.State.*;

public class PageIdealSimuladorSteps extends StepBase {

	private final PageIdealSimulador pageIdealSimulador = new PageIdealSimulador();
	
	@Validation (
		description="Aparece la página de simulación de Ideal",
		level=Defect)
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
