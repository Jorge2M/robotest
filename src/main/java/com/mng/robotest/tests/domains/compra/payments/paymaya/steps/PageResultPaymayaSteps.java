package com.mng.robotest.tests.domains.compra.payments.paymaya.steps;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.mng.robotest.tests.domains.base.StepBase;
import com.mng.robotest.tests.domains.compra.payments.paymaya.pageobjects.PageResultPaymaya;

import static com.github.jorge2m.testmaker.conf.State.*;

public class PageResultPaymayaSteps extends StepBase {

	private final PageResultPaymaya pageResultPaymaya = new PageResultPaymaya();
	
	@Validation (
		description="Aparece la página de introducción resultado de Paymaya",
		level=WARN)
	public boolean checkPage() {
		return pageResultPaymaya.isPage();
	}
	
	@Step(
		description="Seleccionamos el botón <b>Confirm Payment</b>",
		expected="Aparece la página de resultado del pago")
	public void confirmPayment() {
		pageResultPaymaya.confirmPayment();
	}
	
}
