package com.mng.robotest.domains.compra.payments.mercadopago.pageobjects;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

import com.mng.robotest.domains.base.PageBase;

public class PageMercpago1rst extends PageBase {

	private static final String XPATH_INPUT_NUM_TARJETA = "//input[@id='cardNumber']";
	private static final String XPATH_LINK_REGISTRO = "//a[@href[contains(.,'changeGuestMail')]]";

	public boolean isPageUntil(int seconds) {
		return state(Visible, XPATH_INPUT_NUM_TARJETA).wait(seconds).check();
	}

	public void clickLinkRegistro() {
		click(XPATH_LINK_REGISTRO).exec();
	}
}
