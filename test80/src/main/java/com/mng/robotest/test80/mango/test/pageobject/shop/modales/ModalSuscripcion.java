package com.mng.robotest.test80.mango.test.pageobject.shop.modales;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.mng.testmaker.service.webdriver.wrapper.WebdrvWrapp;

public class ModalSuscripcion extends WebdrvWrapp {

    private static String XPathTextRGPD = "//p[@class='gdpr-text gdpr-profiling']";
    private static String XPathLegalRGPD = "//p[@class='gdpr-text gdpr-data-protection']";
	
	public static boolean isTextoRGPDPresent(WebDriver driver) {
		return isElementPresent(driver, By.xpath(XPathTextRGPD));
	}

	public static boolean isTextoLegalRGPDPresent(WebDriver driver) {
		return isElementPresent(driver, By.xpath(XPathLegalRGPD));
	}
	
}