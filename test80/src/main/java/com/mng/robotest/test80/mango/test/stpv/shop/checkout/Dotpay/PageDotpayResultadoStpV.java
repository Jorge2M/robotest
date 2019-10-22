package com.mng.robotest.test80.mango.test.stpv.shop.checkout.Dotpay;

import org.openqa.selenium.WebDriver;

import com.mng.testmaker.boundary.aspects.step.Step;
import com.mng.testmaker.boundary.aspects.validation.Validation;
import com.mng.testmaker.conf.State;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.dotpay.PageDotpayResultado;

public class PageDotpayResultadoStpV {
    
	@Validation (
		description="Aparece la página de resultado del pago OK de Dotpay",
		level=State.Warn)
    public static boolean validateIsPage(WebDriver driver) {
        return (PageDotpayResultado.isPageResultadoOk(driver));
    }
    
	@Step (
		description="Seleccionar el botón <b>Next</b>", 
        expected="Aparece la página de pago OK de Mango")
    public static void clickNext(WebDriver driver) throws Exception {
		PageDotpayResultado.clickButtonNext(driver);
    }
}
