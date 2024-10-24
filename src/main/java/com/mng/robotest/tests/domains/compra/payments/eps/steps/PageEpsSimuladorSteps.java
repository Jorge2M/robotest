package com.mng.robotest.tests.domains.compra.payments.eps.steps;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.mng.robotest.tests.domains.base.StepBase;
import com.mng.robotest.tests.domains.compra.payments.eps.pageobjects.PageEpsSimulador;
import com.mng.robotest.tests.domains.compra.payments.eps.pageobjects.PageEpsSimulador.TypeDelay;

public class PageEpsSimuladorSteps extends StepBase {
	
	private final PageEpsSimulador pageEpsSimulador = new PageEpsSimulador();
	
	@Validation (description="Aparece la página de simulación de EPS")
	public boolean validateIsPage() { 
		return pageEpsSimulador.isPage();
	}
	
	@Step (
		description="Seleccionar la opción <b>#{typeDelay}</b> del apartado \"pending-authorised\"", 
		expected="La opción se selecciona correctamente")
	public void selectDelay(TypeDelay typeDelay) {
		pageEpsSimulador.selectDelayAuthorised(typeDelay);
	}

	@Step (
		description="Seleccionar el botón \"pending > autrhorised\"", 
		expected="El pago se realiza correctamente")
	public void clickContinueButton() {
		pageEpsSimulador.clickButtonContinue();
	}
}
