package com.mng.robotest.test80.mango.test.stpv.shop.checkout.trustpay;

import org.openqa.selenium.WebDriver;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.annotations.step.Step;
import com.mng.robotest.test80.arq.annotations.validation.ChecksResult;
import com.mng.robotest.test80.arq.annotations.validation.Validation;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.trustpay.PageTrustpayTestConfirm;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.trustpay.PageTrustpayTestConfirm.typeButtons;

public class PageTrustpayTestConfirmStpV {
    
	@Validation
    public static ChecksResult validateIsPage(WebDriver driver) {
		ChecksResult validations = ChecksResult.getNew();
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
