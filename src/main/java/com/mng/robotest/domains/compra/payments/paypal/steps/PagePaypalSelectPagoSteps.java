package com.mng.robotest.domains.compra.payments.paypal.steps;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.mng.robotest.domains.base.StepBase;
import com.mng.robotest.domains.compra.payments.paypal.pageobjects.PagePaypalSelectPago;

public class PagePaypalSelectPagoSteps extends StepBase {
	
	private final PagePaypalSelectPago pagePaypalSelectPago = new PagePaypalSelectPago();
	
	@Validation (
		description="Aparece la página de Selección del Pago " + SECONDS_WAIT)
	public boolean validateIsPageUntil(int seconds) {
		return pagePaypalSelectPago.isPageUntil(seconds);
	}
	
	@Step (
		description="Seleccionar el botón \"Continuar\"", 
		expected="Aparece la página de Mango de resultado OK del pago")
	public void clickContinuarButton() {
		pagePaypalSelectPago.clickContinuarButton();
		new ModalPreloaderSppinerSteps().validateAppearsAndDisappears();
	}
}
