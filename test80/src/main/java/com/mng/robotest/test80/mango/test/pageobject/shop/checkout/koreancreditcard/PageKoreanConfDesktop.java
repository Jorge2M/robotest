package com.mng.robotest.test80.mango.test.pageobject.shop.checkout.koreancreditcard;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.mng.testmaker.service.webdriver.wrapper.WebdrvWrapp;

public class PageKoreanConfDesktop extends WebdrvWrapp {
    private static String XPathSubmitButton = "//input[@id='pay_btn']";

	public static boolean isPage(WebDriver driver) {
		return isElementVisible(driver, By.xpath(XPathSubmitButton));
	}
	
	public static void clickButtonSubmit(WebDriver driver) throws Exception {
		WebdrvWrapp.clickAndWaitLoad(driver, By.xpath(XPathSubmitButton));
	}
}
