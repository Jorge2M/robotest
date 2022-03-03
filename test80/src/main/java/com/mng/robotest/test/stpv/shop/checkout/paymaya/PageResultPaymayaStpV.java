package com.mng.robotest.test.stpv.shop.checkout.paymaya;

import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.conf.State;
import com.mng.robotest.test.pageobject.shop.checkout.paymaya.PageResultPaymaya;

public class PageResultPaymayaStpV {

	private final PageResultPaymaya pageResultPaymaya;
	
	public PageResultPaymayaStpV(WebDriver driver) {
		this.pageResultPaymaya = new PageResultPaymaya(driver);
	}
	
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
