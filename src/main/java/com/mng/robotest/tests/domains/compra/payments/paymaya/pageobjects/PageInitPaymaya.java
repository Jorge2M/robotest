package com.mng.robotest.tests.domains.compra.payments.paymaya.pageobjects;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

import com.mng.robotest.tests.domains.base.PageBase;

public class PageInitPaymaya extends PageBase {

	private static final String XP_WRAPPER = "//div[@class='paywith-paymaya-screen']";
	private static final String XP_QR = "//div[@class='scan-section']//div[@class='qr']";
	private static final String XP_BUTTON_PAY_MAYA = "//button[@class='button--paylink-btn']";
	
	public boolean isPage() {
		return state(Present, XP_WRAPPER).check();
	}
	
	public boolean isQrVisible() {
		return state(Visible, XP_QR).check();
	}
	
	public void clickButtonPayMaya() {
		click(XP_BUTTON_PAY_MAYA).exec();
	}
}
