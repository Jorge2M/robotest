package com.mng.robotest.test80.mango.test.pageobject.shop.modales;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static com.mng.testmaker.service.webdriver.pageobject.PageObjTM.*;
import static com.mng.testmaker.service.webdriver.pageobject.StateElement.State.*;

public class ModalSuscripcion {

    private static String XPathTextRGPD = "//p[@class='gdpr-text gdpr-profiling']";
    private static String XPathLegalRGPD = "//p[@class='gdpr-text gdpr-data-protection']";
	
	public static boolean isTextoRGPDPresent(WebDriver driver) {
		return (state(Present, By.xpath(XPathTextRGPD), driver).check());
	}

	public static boolean isTextoLegalRGPDPresent(WebDriver driver) {
		return (state(Present, By.xpath(XPathLegalRGPD), driver).check());
	}
	
}