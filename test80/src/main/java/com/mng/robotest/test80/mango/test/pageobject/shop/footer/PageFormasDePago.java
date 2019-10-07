package com.mng.robotest.test80.mango.test.pageobject.shop.footer;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.mng.testmaker.service.webdriver.wrapper.WebdrvWrapp;

public class PageFormasDePago extends WebdrvWrapp implements PageFromFooter {
	
	final String XPathForIdPage = "//*[text()[contains(.,'Métodos de pago')]]";
	
	@Override
	public String getName() {
		return "Formas de pago";
	}
	
	@Override
	public boolean isPageCorrectUntil(int maxSecondsWait, WebDriver driver) {
		return (isElementPresentUntil(driver, By.xpath(XPathForIdPage), maxSecondsWait));
	}
}
