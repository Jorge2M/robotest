package com.mng.robotest.test80.mango.test.pageobject.shop.footer;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.mng.testmaker.webdriverwrapper.WebdrvWrapp;

public class PagePregFrecuentes extends WebdrvWrapp implements PageFromFooter {
	
	final String XPathForIdPage = "//*[text()[contains(.,'Preguntas frecuentes')]]";
	
	@Override
	public String getName() {
		return "PREGUNTAS FRECUENTES";
	}
	
	@Override
	public boolean isPageCorrectUntil(int maxSecondsWait, WebDriver driver) {
		return (isElementPresentUntil(driver, By.xpath(XPathForIdPage), maxSecondsWait));
	}
}
