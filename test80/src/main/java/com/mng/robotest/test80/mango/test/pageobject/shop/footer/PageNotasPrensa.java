package com.mng.robotest.test80.mango.test.pageobject.shop.footer;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.mng.robotest.test80.arq.webdriverwrapper.WebdrvWrapp;

public class PageNotasPrensa extends WebdrvWrapp implements PageFromFooter {
	
	final String XPathForIdPage = "//*[text()[contains(.,'NOTAS DE PRENSA')] or text()[contains(.,'Notas de prensa')] or text()[contains(.,'PRESS RELEASES')]]";
	
	@Override
	public String getName() {
		return "Noticias en prensa";
	}
	
	@Override
	public boolean isPageCorrectUntil(int maxSecondsWait, WebDriver driver) {
		return (isElementPresentUntil(driver, By.xpath(XPathForIdPage), maxSecondsWait));
	}
}
