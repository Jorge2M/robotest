package com.mng.robotest.test.pageobject.shop.checkout.paypal;

import com.github.jorge2m.testmaker.service.webdriver.pageobject.TypeClick;
import com.mng.robotest.domains.transversal.PageBase;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

public class PagePaypalCreacionCuenta extends PageBase {
 
	private static final String XPATH_BUTTON_INICIAR_SESION = "//div[@class[contains(.,'LoginButton')]]/a";
	private static final String XPATH_BUTTON_ACEPTAR_Y_PAGAR = "//input[@track-submit='signup']";
	private static final String XPATH_BUTTON_PAGAR_AHORA = "//input[@track-submit='guest_xo']";
	
	public boolean isPageUntil(int maxSeconds) {
		String xpath = "(" + XPATH_BUTTON_ACEPTAR_Y_PAGAR + ") | (" + XPATH_BUTTON_PAGAR_AHORA + ") | (" + XPATH_BUTTON_INICIAR_SESION + ")";
		return state(Present, xpath).wait(maxSeconds).check();
	}

	public void clickButtonIniciarSesion() {
		click(XPATH_BUTTON_INICIAR_SESION).type(TypeClick.javascript).exec();
	}
}
