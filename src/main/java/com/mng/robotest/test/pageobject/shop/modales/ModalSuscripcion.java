package com.mng.robotest.test.pageobject.shop.modales;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM.*;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

public class ModalSuscripcion {

	private static String XPathLegalRGPD = "//p[@class='gdpr-text gdpr-data-protection']";

	public static boolean isTextoLegalRGPDPresent(WebDriver driver) {
		return (state(Present, By.xpath(XPathLegalRGPD), driver).check());
	}
	
}