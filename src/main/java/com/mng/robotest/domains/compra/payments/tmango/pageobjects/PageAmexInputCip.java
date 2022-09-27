package com.mng.robotest.domains.compra.payments.tmango.pageobjects;

import com.mng.robotest.domains.transversal.PageBase;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

public class PageAmexInputCip extends PageBase {

	private static final String XPATH_INPUT_CIP = "//input[@name='pin']";
	private static final String XPATH_ACCEPT_BUTTON = "//img[@src[contains(.,'daceptar.gif')]]/../../a";
	
	public boolean isPageUntil(int seconds) {
		return state(Present, XPATH_INPUT_CIP).wait(seconds).check();
	}
	
	public void inputCIP(String CIP) {
		getElement(XPATH_INPUT_CIP).sendKeys(CIP);
	}

	public void clickAceptarButton() {
		click(XPATH_ACCEPT_BUTTON).exec();
	}
}