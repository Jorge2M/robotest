package com.mng.robotest.tests.domains.compra.payments.mercadopago.pageobjects;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

import com.mng.robotest.tests.domains.base.PageBase;

public class PageMercpago1rst extends PageBase {

	private static final String XP_INPUT_NUM_TARJETA = "//input[@id='cardNumber']";
	private static final String XP_LINK_REGISTRO = "//a[@href[contains(.,'changeGuestMail')]]";

	public boolean isPageUntil(int seconds) {
		return state(Visible, XP_INPUT_NUM_TARJETA).wait(seconds).check();
	}

	public void clickLinkRegistro() {
		click(XP_LINK_REGISTRO).exec();
	}
}
