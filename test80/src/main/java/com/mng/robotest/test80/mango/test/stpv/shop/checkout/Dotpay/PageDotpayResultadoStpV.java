package com.mng.robotest.test80.mango.test.stpv.shop.checkout.Dotpay;

import org.openqa.selenium.WebDriver;

import com.mng.testmaker.boundary.aspects.step.Step;
import com.mng.testmaker.boundary.aspects.validation.Validation;
import com.mng.testmaker.conf.State;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.dotpay.PageDotpayResultado;

public class PageDotpayResultadoStpV {
    
	private final PageDotpayResultado pageDotpayResultado;
	
	public PageDotpayResultadoStpV(WebDriver driver) {
		this.pageDotpayResultado = new PageDotpayResultado(driver);
	}
	
	@Validation (
		description="Aparece la página de resultado del pago OK de Dotpay",
		level=State.Warn)
    public boolean validateIsPage() {
        return (pageDotpayResultado.isPageResultadoOk());
    }
    
	@Step (
		description="Seleccionar el botón <b>Next</b>", 
        expected="Aparece la página de pago OK de Mango")
    public void clickNext() throws Exception {
		pageDotpayResultado.clickButtonNext();
    }
}
