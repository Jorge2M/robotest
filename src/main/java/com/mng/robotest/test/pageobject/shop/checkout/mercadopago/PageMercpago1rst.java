package com.mng.robotest.test.pageobject.shop.checkout.mercadopago;

import com.mng.robotest.domains.transversal.PageBase;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

public class PageMercpago1rst extends PageBase {

	private static final String XPATH_INPUT_NUM_TARJETA = "//input[@id='cardNumber']";
	private static final String XPATH_LINK_REGISTRO = "//a[@href[contains(.,'changeGuestMail')]]";

	public boolean isPageUntil(int maxSeconds) {
		return state(Visible, XPATH_INPUT_NUM_TARJETA).wait(maxSeconds).check();
	}

	public void clickLinkRegistro() {
		click(XPATH_LINK_REGISTRO).exec();
	}
}
