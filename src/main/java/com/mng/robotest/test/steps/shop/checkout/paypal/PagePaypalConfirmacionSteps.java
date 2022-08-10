package com.mng.robotest.test.steps.shop.checkout.paypal;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.conf.State;
import com.mng.robotest.domains.transversal.StepBase;
import com.mng.robotest.test.pageobject.shop.checkout.paypal.PagePaypalConfirmacion;


public class PagePaypalConfirmacionSteps extends StepBase {

	private final PagePaypalConfirmacion pagePaypalConfirmacion = new PagePaypalConfirmacion();
	
	@Validation (
		description="Aparece la página de Confirmación (la esperamos hasta #{maxSeconds} segundos)",
		level=State.Defect)
	public boolean validateIsPageUntil(int maxSeconds) {
		return (pagePaypalConfirmacion.isPageUntil(maxSeconds));
	}

	@Step (
		description="Seleccionar el botón \"Continuar\"", 
		expected="Aparece la página de Mango de resultado OK del pago")
	public void clickContinuarButton() { 
		pagePaypalConfirmacion.clickContinuarButton();
	}
}
