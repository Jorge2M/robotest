package com.mng.robotest.tests.domains.compra.payments.tmango.pageobjects;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

import com.mng.robotest.tests.domains.base.PageBase;

public class PageAmexInputCip extends PageBase {

	private static final String XP_INPUT_CIP = "//input[@name='pin']";
	private static final String XP_ACCEPT_BUTTON = "//img[@src[contains(.,'daceptar.gif')]]/../../a";
	
	public boolean isPage(int seconds) {
		return state(PRESENT, XP_INPUT_CIP).wait(seconds).check();
	}
	
	public void inputCIP(String cip) {
		getElement(XP_INPUT_CIP).sendKeys(cip);
	}

	public void clickAceptarButton() {
		click(XP_ACCEPT_BUTTON).exec();
	}
}
