package com.mng.robotest.test.steps.shop.checkout.eps;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.conf.State;
import com.mng.robotest.domains.transversal.StepBase;
import com.mng.robotest.test.pageobject.shop.checkout.eps.PageEpsSimulador;
import com.mng.robotest.test.pageobject.shop.checkout.eps.PageEpsSimulador.TypeDelay;

public class PageEpsSimuladorSteps extends StepBase {
	
	private final PageEpsSimulador pageEpsSimulador = new PageEpsSimulador();
	
	@Validation (
		description="Aparece la página de simulación de EPS",
		level=State.Defect)
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
