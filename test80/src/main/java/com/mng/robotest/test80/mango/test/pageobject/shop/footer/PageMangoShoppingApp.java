package com.mng.robotest.test80.mango.test.pageobject.shop.footer;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.mng.robotest.test80.mango.test.pageobject.WebdrvWrapp;

public class PageMangoShoppingApp extends WebdrvWrapp implements PageFromFooter {
	
	final String XPathForIdPage = "//*[text()[contains(.,'MANGO SHOPPING APP')]]";
	
	@Override
	public String getName() {
		return "MANGO SHOPPING APP";
	}
	
	@Override
	public boolean isPageCorrect(WebDriver driver) {
		return (isElementPresent(driver, By.xpath(XPathForIdPage)));
	}
}
