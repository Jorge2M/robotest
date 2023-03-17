package com.mng.robotest.domains.compra.payments.tmango.pageobjects;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

import com.mng.robotest.domains.base.PageBase;

public class PageAmexInputCip extends PageBase {

	private static final String XPATH_INPUT_CIP = "//input[@name='pin']";
	private static final String XPATH_ACCEPT_BUTTON = "//img[@src[contains(.,'daceptar.gif')]]/../../a";
	
	public boolean isPageUntil(int seconds) {
		return state(Present, XPATH_INPUT_CIP).wait(seconds).check();
	}
	
	public void inputCIP(String cip) {
		getElement(XPATH_INPUT_CIP).sendKeys(cip);
	}

	public void clickAceptarButton() {
		click(XPATH_ACCEPT_BUTTON).exec();
	}
}
