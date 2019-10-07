package com.mng.robotest.test80.mango.test.pageobject.shop.footer;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.mng.testmaker.service.webdriver.wrapper.WebdrvWrapp;

public class PageEmpresa extends WebdrvWrapp implements PageFromFooter {
	
	final String XPathForIdPageNew = "//img[@src[contains(.,'edits_site_empresa')]]";
	
	@Override
	public String getName() {
		return "Datos de empresa";
	}
	
	@Override
	public boolean isPageCorrectUntil(int maxSecondsWait, WebDriver driver) {
		return (isElementPresentUntil(driver, By.xpath(XPathForIdPageNew), maxSecondsWait));
	}
}
