package com.mng.robotest.test.pageobject.shop.checkout.paymaya;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State;

public class PageInitPaymaya extends PageObjTM {

	private static final String XPathWrapper = "//div[@class='paywith-paymaya-screen']";
	private static final String XPathQr = "//div[@class='scan-section']//div[@class='qr']";
	private static final String XPathButtonPayMaya = "//button[@class='button--paylink-btn']";
	
	public PageInitPaymaya(WebDriver driver) {
		super(driver);
	}
	
	public boolean isPage() {
		return state(State.Present, By.xpath(XPathWrapper)).check();
	}
	
	public boolean isQrVisible() {
		return state(State.Visible, By.xpath(XPathQr)).check();
	}
	
	public void clickButtonPayMaya() {
		click(By.xpath(XPathButtonPayMaya)).exec();
	}
}
