package com.mng.robotest.test.steps.shop.checkout.paymaya;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.conf.State;
import com.mng.robotest.domains.transversal.StepBase;
import com.mng.robotest.test.pageobject.shop.checkout.paymaya.PageResultPaymaya;


public class PageResultPaymayaSteps extends StepBase {

	private final PageResultPaymaya pageResultPaymaya = new PageResultPaymaya();
	
	@Validation (
		description="Aparece la página de introducción resultado de Paymaya",
		level=State.Warn)
	public boolean checkPage() {
		return pageResultPaymaya.isPage();
	}
	
	@Step(
		description="Seleccionamos el botón <b>Confirm Payment</b>",
		expected="Aparece la página de resultado del pago")
	public void confirmPayment() throws Exception {
		pageResultPaymaya.confirmPayment();
	}
	
}
