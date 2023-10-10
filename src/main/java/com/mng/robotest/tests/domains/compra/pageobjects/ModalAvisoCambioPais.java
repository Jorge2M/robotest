package com.mng.robotest.tests.domains.compra.pageobjects;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

import com.mng.robotest.tests.domains.base.PageBase;

public class ModalAvisoCambioPais extends PageBase {

	private static final String XPATH_MODAL = "//div[@aria-labelledby[contains(.,'changeCountryModal')]]";
	private static final String XPATH_BUTTON_CONF_CAMBIO = XPATH_MODAL + "//button[@name='continue']";

	public boolean isVisibleUntil(int seconds) {
		return state(Visible, XPATH_MODAL).wait(seconds).check();
	}

	public boolean isInvisibleUntil(int seconds) {
		return state(Invisible, XPATH_MODAL).wait(seconds).check();
	}

	public void clickConfirmarCambio() {
		waitLoadPage();
		moveToElement(XPATH_BUTTON_CONF_CAMBIO);
		click(XPATH_BUTTON_CONF_CAMBIO).exec();
	}
}