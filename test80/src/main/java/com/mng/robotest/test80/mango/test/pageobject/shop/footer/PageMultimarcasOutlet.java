package com.mng.robotest.test80.mango.test.pageobject.shop.footer;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.mng.robotest.test80.mango.test.pageobject.WebdrvWrapp;

public class PageMultimarcasOutlet extends WebdrvWrapp implements PageFromFooter {
	
	final String XPathForIdPage = "//title[text()[contains(.,'OUTLET')] or text()[contains(.,'Outlet')]]";
	
	@Override
	public String getName() {
		return "Multimarcas Outlet";
	}
	
	@Override
	public boolean isPageCorrect(WebDriver driver) {
		return (isElementPresent(driver, By.xpath(XPathForIdPage)));
	}
}
