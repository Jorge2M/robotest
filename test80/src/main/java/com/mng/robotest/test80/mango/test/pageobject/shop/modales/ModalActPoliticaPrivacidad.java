package com.mng.robotest.test80.mango.test.pageobject.shop.modales;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.mng.testmaker.service.webdriver.wrapper.WebdrvWrapp;

public class ModalActPoliticaPrivacidad extends WebdrvWrapp {

	static String XPathModal = "//div[@class='modal-info-gdpr']";
	static String XPathButtonOk = XPathModal + "//input[@type='submit']";
	
	public static boolean isVisible(WebDriver driver) {
		return (isElementVisible(driver, By.xpath(XPathModal)));
	}
	
	public static void clickOk(WebDriver driver) throws Exception {
		clickAndWaitLoad(driver, By.xpath(XPathButtonOk));
	}
	
	public static void clickOkIfVisible(WebDriver driver) throws Exception {
		if (isVisible(driver)) {
			clickOk(driver);
		}
	}
}
