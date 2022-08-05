package com.mng.robotest.test.steps.shop.checkout.trustpay;

import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.mng.robotest.test.pageobject.shop.checkout.trustpay.PageTrustpayTestConfirm;
import com.mng.robotest.test.pageobject.shop.checkout.trustpay.PageTrustpayTestConfirm.typeButtons;

public class PageTrustpayTestConfirmSteps {
	
	@Validation
	public static ChecksTM validateIsPage(WebDriver driver) {
		ChecksTM validations = ChecksTM.getNew();
		validations.add(
			"Figura el botón \"OK\"",
			PageTrustpayTestConfirm.isPresentButton(typeButtons.OK, driver), State.Defect);
		validations.add(
			"Figura el botón \"ANNOUNCED\"",
			PageTrustpayTestConfirm.isPresentButton(typeButtons.ANNOUNCED, driver), State.Warn);
		validations.add(
			"Figura el botón \"FAIL\"",
			PageTrustpayTestConfirm.isPresentButton(typeButtons.FAIL, driver), State.Warn);
		validations.add(
			"Figura el botón \"PENDING\"",
			PageTrustpayTestConfirm.isPresentButton(typeButtons.PENDING, driver), State.Warn);
		return validations;
	}
	
	@Step (
		description="Seleccionar el botón para continuar con el pago", 
		expected="El pago se completa correctamente")
	public static void clickButtonOK(WebDriver driver) throws Exception {
		PageTrustpayTestConfirm.clickButton(typeButtons.OK, driver);
	}
}
