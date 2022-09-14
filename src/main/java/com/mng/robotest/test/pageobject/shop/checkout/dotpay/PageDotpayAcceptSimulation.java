package com.mng.robotest.test.pageobject.shop.checkout.dotpay;

import com.mng.robotest.domains.transversal.PageBase;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

public class PageDotpayAcceptSimulation extends PageBase {
	
	private static final String XPATH_ENCABEZADO = "//h1[text()[contains(.,'Simulation of payment')]]";
	private static final String XPATH_RED_BUTTON_ACEPTAR = "//input[@id='submit_success' and @type='submit']";

	public boolean isPage(int seconds) {
		return state(Visible, XPATH_ENCABEZADO).wait(seconds).check();
	}
	
	public boolean isPresentRedButtonAceptar() {
		return state(Present, XPATH_RED_BUTTON_ACEPTAR).check();
	}

	public void clickRedButtonAceptar() {
		click(XPATH_RED_BUTTON_ACEPTAR).exec();
	}
}
