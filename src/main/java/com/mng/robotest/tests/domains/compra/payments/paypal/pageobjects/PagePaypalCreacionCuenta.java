package com.mng.robotest.tests.domains.compra.payments.paypal.pageobjects;

import com.github.jorge2m.testmaker.service.webdriver.pageobject.TypeClick;
import com.mng.robotest.tests.domains.base.PageBase;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

public class PagePaypalCreacionCuenta extends PageBase {
 
	private static final String XPATH_BUTTON_INICIAR_SESION = "//div[@class[contains(.,'LoginButton')]]/a";
	private static final String XPATH_BUTTON_ACEPTAR_Y_PAGAR = "//input[@track-submit='signup']";
	private static final String XPATH_BUTTON_PAGAR_AHORA = "//input[@track-submit='guest_xo']";
	
	public boolean isPageUntil(int seconds) {
		String xpath = "(" + XPATH_BUTTON_ACEPTAR_Y_PAGAR + ") | (" + XPATH_BUTTON_PAGAR_AHORA + ") | (" + XPATH_BUTTON_INICIAR_SESION + ")";
		return state(Present, xpath).wait(seconds).check();
	}

	public void clickButtonIniciarSesion() {
		click(XPATH_BUTTON_INICIAR_SESION).type(TypeClick.javascript).exec();
	}
}
