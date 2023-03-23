package com.mng.robotest.domains.compra.payments.paypal.steps;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.mng.robotest.domains.base.StepBase;
import com.mng.robotest.domains.compra.payments.paypal.pageobjects.PagePaypalConfirmacion;

import static com.github.jorge2m.testmaker.conf.State.*;

public class PagePaypalConfirmacionSteps extends StepBase {

	private final PagePaypalConfirmacion pagePaypalConfirmacion = new PagePaypalConfirmacion();
	
	@Validation (
		description="Aparece la página de Confirmación (la esperamos hasta #{seconds} segundos)",
		level=Defect)
	public boolean validateIsPageUntil(int seconds) {
		return (pagePaypalConfirmacion.isPageUntil(seconds));
	}

	@Step (
		description="Seleccionar el botón \"Continuar\"", 
		expected="Aparece la página de Mango de resultado OK del pago")
	public void clickContinuarButton() { 
		pagePaypalConfirmacion.clickContinuarButton();
	}
}
