package com.mng.robotest.test.steps.shop.checkout.paypal;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.conf.State;
import com.mng.robotest.domains.transversal.StepBase;
import com.mng.robotest.test.pageobject.shop.checkout.paypal.PagePaypalSelectPago;


public class PagePaypalSelectPagoSteps extends StepBase {
	
	PagePaypalSelectPago pagePaypalSelectPago = new PagePaypalSelectPago();
	
	@Validation (
		description="Aparece la página de Selección del Pago (la esperamos hasta #{maxSeconds} segundos)",
		level=State.Defect)
	public boolean validateIsPageUntil(int maxSeconds) {
		return pagePaypalSelectPago.isPageUntil(maxSeconds);
	}
	
	@Step (
		description="Seleccionar el botón \"Continuar\"", 
		expected="Aparece la página de Mango de resultado OK del pago")
	public void clickContinuarButton() {
		pagePaypalSelectPago.clickContinuarButton();
		new ModalPreloaderSppinerSteps().validateAppearsAndDisappears();
	}
}
