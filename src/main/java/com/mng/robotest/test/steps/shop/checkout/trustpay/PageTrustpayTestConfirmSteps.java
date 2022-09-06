package com.mng.robotest.test.steps.shop.checkout.trustpay;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.mng.robotest.domains.transversal.StepBase;
import com.mng.robotest.test.pageobject.shop.checkout.trustpay.PageTrustpayTestConfirm;
import com.mng.robotest.test.pageobject.shop.checkout.trustpay.PageTrustpayTestConfirm.typeButtons;

public class PageTrustpayTestConfirmSteps extends StepBase {
	
	private final PageTrustpayTestConfirm pageTrustpayTestConfirm = new PageTrustpayTestConfirm();
	
	@Validation
	public ChecksTM validateIsPage() {
		ChecksTM checks = ChecksTM.getNew();
		checks.add(
			"Figura el botón \"OK\"",
			pageTrustpayTestConfirm.isPresentButton(typeButtons.OK), State.Defect);
		
		checks.add(
			"Figura el botón \"ANNOUNCED\"",
			pageTrustpayTestConfirm.isPresentButton(typeButtons.ANNOUNCED), State.Warn);
		
		checks.add(
			"Figura el botón \"FAIL\"",
			pageTrustpayTestConfirm.isPresentButton(typeButtons.FAIL), State.Warn);
		
		checks.add(
			"Figura el botón \"PENDING\"",
			pageTrustpayTestConfirm.isPresentButton(typeButtons.PENDING), State.Warn);
		
		return checks;
	}
	
	@Step (
		description="Seleccionar el botón para continuar con el pago", 
		expected="El pago se completa correctamente")
	public void clickButtonOK() throws Exception {
		pageTrustpayTestConfirm.clickButton(typeButtons.OK);
	}
}
