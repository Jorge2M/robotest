package com.mng.robotest.domains.compra.payments.paymaya.pageobjects;

import com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State;
import com.mng.robotest.domains.base.PageBase;

public class PageInitPaymaya extends PageBase {

	private static final String XPATH_WRAPPER = "//div[@class='paywith-paymaya-screen']";
	private static final String XPATH_QR = "//div[@class='scan-section']//div[@class='qr']";
	private static final String XPATH_BUTTON_PAY_MAYA = "//button[@class='button--paylink-btn']";
	
	public boolean isPage() {
		return state(State.Present, XPATH_WRAPPER).check();
	}
	
	public boolean isQrVisible() {
		return state(State.Visible, XPATH_QR).check();
	}
	
	public void clickButtonPayMaya() {
		click(XPATH_BUTTON_PAY_MAYA).exec();
	}
}
