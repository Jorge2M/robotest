package com.mng.robotest.test80.mango.test.stpv.shop.checkout.trustpay;

import org.openqa.selenium.WebDriver;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.annotations.step.Step;
import com.mng.robotest.test80.arq.annotations.validation.ListResultValidation;
import com.mng.robotest.test80.arq.annotations.validation.Validation;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.trustpay.PageTrustpayTestConfirm;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.trustpay.PageTrustpayTestConfirm.typeButtons;

public class PageTrustpayTestConfirmStpV {
    
	@Validation
    public static ListResultValidation validateIsPage(WebDriver driver) {
		ListResultValidation validations = ListResultValidation.getNew();
    	validations.add(
    		"Figura el botón \"OK\"<br>",
    		PageTrustpayTestConfirm.isPresentButton(typeButtons.OK, driver), State.Defect);
    	validations.add(
    		"Figura el botón \"ANNOUNCED\"<br>",
    		PageTrustpayTestConfirm.isPresentButton(typeButtons.ANNOUNCED, driver), State.Warn);
    	validations.add(
    		"Figura el botón \"FAIL\"<br>",
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
