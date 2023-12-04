package com.mng.robotest.tests.domains.compra.payments.trustpay.steps;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.mng.robotest.tests.domains.base.StepBase;
import com.mng.robotest.tests.domains.compra.payments.trustpay.pageobjects.PageTrustpayTestConfirm;
import com.mng.robotest.tests.domains.compra.payments.trustpay.pageobjects.PageTrustpayTestConfirm.typeButtons;

import static com.github.jorge2m.testmaker.conf.State.*;

public class PageTrustpayTestConfirmSteps extends StepBase {
	
	private final PageTrustpayTestConfirm pageTrustpayTestConfirm = new PageTrustpayTestConfirm();
	
	@Validation
	public ChecksTM validateIsPage() {
		var checks = ChecksTM.getNew();
		checks.add(
			"Figura el botón \"OK\"",
			pageTrustpayTestConfirm.isPresentButton(typeButtons.OK));
		
		checks.add(
			"Figura el botón \"ANNOUNCED\"",
			pageTrustpayTestConfirm.isPresentButton(typeButtons.ANNOUNCED), WARN);
		
		checks.add(
			"Figura el botón \"FAIL\"",
			pageTrustpayTestConfirm.isPresentButton(typeButtons.FAIL), WARN);
		
		checks.add(
			"Figura el botón \"PENDING\"",
			pageTrustpayTestConfirm.isPresentButton(typeButtons.PENDING), WARN);
		
		return checks;
	}
	
	@Step (
		description="Seleccionar el botón para continuar con el pago", 
		expected="El pago se completa correctamente")
	public void clickButtonOK() {
		pageTrustpayTestConfirm.clickButton(typeButtons.OK);
	}
}
