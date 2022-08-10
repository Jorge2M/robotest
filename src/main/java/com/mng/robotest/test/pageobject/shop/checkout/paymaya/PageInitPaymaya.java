package com.mng.robotest.test.pageobject.shop.checkout.paymaya;

import org.openqa.selenium.By;

import com.mng.robotest.domains.transversal.PageBase;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State;

public class PageInitPaymaya extends PageBase {

	private static final String XPATH_WRAPPER = "//div[@class='paywith-paymaya-screen']";
	private static final String XPATH_QR = "//div[@class='scan-section']//div[@class='qr']";
	private static final String XPATH_BUTTON_PAY_MAYA = "//button[@class='button--paylink-btn']";
	
	public boolean isPage() {
		return state(State.Present, By.xpath(XPATH_WRAPPER)).check();
	}
	
	public boolean isQrVisible() {
		return state(State.Visible, By.xpath(XPATH_QR)).check();
	}
	
	public void clickButtonPayMaya() {
		click(By.xpath(XPATH_BUTTON_PAY_MAYA)).exec();
	}
}
