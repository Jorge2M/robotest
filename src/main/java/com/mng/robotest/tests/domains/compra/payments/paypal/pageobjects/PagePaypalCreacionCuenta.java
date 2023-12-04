package com.mng.robotest.tests.domains.compra.payments.paypal.pageobjects;

import com.mng.robotest.tests.domains.base.PageBase;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.TypeClick.*;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

public class PagePaypalCreacionCuenta extends PageBase {
 
	private static final String XP_BUTTON_INICIAR_SESION = "//div[@class[contains(.,'LoginButton')]]/a";
	private static final String XP_BUTTON_ACEPTAR_Y_PAGAR = "//input[@track-submit='signup']";
	private static final String XP_BUTTON_PAGAR_AHORA = "//input[@track-submit='guest_xo']";
	
	public boolean isPage(int seconds) {
		String xpath = "(" + XP_BUTTON_ACEPTAR_Y_PAGAR + ") | (" + XP_BUTTON_PAGAR_AHORA + ") | (" + XP_BUTTON_INICIAR_SESION + ")";
		return state(PRESENT, xpath).wait(seconds).check();
	}

	public void clickButtonIniciarSesion() {
		click(XP_BUTTON_INICIAR_SESION).type(JAVASCRIPT).exec();
	}
}
