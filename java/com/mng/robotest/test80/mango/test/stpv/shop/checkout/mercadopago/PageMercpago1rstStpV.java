package com.mng.robotest.test80.mango.test.stpv.shop.checkout.mercadopago;

import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.conf.State;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.mercadopago.PageMercpago1rst;

public class PageMercpago1rstStpV {
	
	@Validation (
		description="Aparece la página inicial de Mercado para la introducción de datos (la esperamos hasta #{maxSeconds} segundos)",
		level=State.Warn)
    public static boolean validateIsPageUntil(int maxSeconds, WebDriver driver) {
       return (PageMercpago1rst.isPageUntil(maxSeconds, driver));
    }
	
	@Step (
		description="Accedemos a la página de identificación", 
        expected="Aparece la página de identificación")
    public static void clickLinkRegistration(WebDriver driver) {
        PageMercpago1rst.clickLinkRegistro(driver);
        PageMercpagoLoginStpV.validateIsPage(driver);
    }
}
