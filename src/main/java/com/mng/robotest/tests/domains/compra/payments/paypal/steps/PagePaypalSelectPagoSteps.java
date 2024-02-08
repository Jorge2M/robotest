package com.mng.robotest.tests.domains.compra.payments.paypal.steps;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.mng.robotest.tests.domains.base.StepBase;
import com.mng.robotest.tests.domains.compra.payments.paypal.pageobjects.PagePaypalSelectPago;

public class PagePaypalSelectPagoSteps extends StepBase {
	
	private final PagePaypalSelectPago pgPaypalSelectPago = new PagePaypalSelectPago();
	
	@Validation (
		description="Aparece la página de Selección del Pago " + SECONDS_WAIT)
	public boolean checkIsPage(int seconds) {
		return pgPaypalSelectPago.isPage(seconds);
	}
	
	@Step (
		description="Seleccionar el botón \"Continuar\"", 
		expected="Aparece la página de Mango de resultado OK del pago")
	public void clickContinuarButton() {
		pgPaypalSelectPago.clickContinuarButton();
		new ModalPreloaderSppinerSteps().validateAppearsAndDisappears();
	}
}
