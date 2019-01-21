package com.mng.robotest.test80.mango.test.pageobject.shop.checkout.postfinance;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.mng.robotest.test80.mango.test.pageobject.WebdrvWrapp;

public class PagePostfSelectPago extends WebdrvWrapp {

	public static String getXPathIconoMetodoPago(String nombrePago) {
		return ("//input[@title='" + nombrePago + "']");
	}
	
	public static boolean isPageUntil(String nombrePago, int maxSecondsToWait, WebDriver driver) {
		String xpathIconoPago = getXPathIconoMetodoPago(nombrePago);
		return (isElementVisibleUntil(driver, By.xpath(xpathIconoPago), maxSecondsToWait));
	}
	
	public static void clickIconoPago(String nombrePago, WebDriver driver) throws Exception {
		String xpathIconoPago = getXPathIconoMetodoPago(nombrePago);
		clickAndWaitLoad(driver, By.xpath(xpathIconoPago));
	}
}
