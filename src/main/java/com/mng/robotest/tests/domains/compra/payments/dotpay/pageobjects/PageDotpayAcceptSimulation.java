package com.mng.robotest.tests.domains.compra.payments.dotpay.pageobjects;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

import com.mng.robotest.tests.domains.base.PageBase;

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
