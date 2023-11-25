package com.mng.robotest.tests.domains.compra.pageobjects;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

import com.mng.robotest.tests.domains.base.PageBase;

public class ModalAvisoCambioPais extends PageBase {

	private static final String XP_MODAL = "//div[@aria-labelledby[contains(.,'changeCountryModal')]]";
	private static final String XP_BUTTON_CONF_CAMBIO = XP_MODAL + "//button[@name='continue']";

	public boolean isVisibleUntil(int seconds) {
		return state(Visible, XP_MODAL).wait(seconds).check();
	}

	public boolean isInvisibleUntil(int seconds) {
		return state(Invisible, XP_MODAL).wait(seconds).check();
	}

	public void clickConfirmarCambio() {
		waitLoadPage();
		moveToElement(XP_BUTTON_CONF_CAMBIO);
		click(XP_BUTTON_CONF_CAMBIO).exec();
	}
}
