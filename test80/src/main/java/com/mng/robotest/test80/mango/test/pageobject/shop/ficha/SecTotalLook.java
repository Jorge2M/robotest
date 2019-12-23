package com.mng.robotest.test80.mango.test.pageobject.shop.ficha;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.mng.testmaker.service.webdriver.wrapper.WebdrvWrapp;

public class SecTotalLook extends WebdrvWrapp {

	private final static String XPathTotalLook = "//div[@id='lookTotal']";
	
	public static boolean isVisible(WebDriver driver) {
		return WebdrvWrapp.isElementVisible(driver, By.xpath(XPathTotalLook));
	}
}
