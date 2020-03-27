package com.mng.robotest.test80.mango.test.pageobject.shop.checkout.postfinance;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static com.mng.testmaker.service.webdriver.pageobject.PageObjTM.*;
import static com.mng.testmaker.service.webdriver.pageobject.StateElement.State.*;

public class PagePostfSelectPago {

	public static String getXPathIconoMetodoPago(String nombrePago) {
		return ("//input[@title='" + nombrePago + "']");
	}
	
	public static boolean isPageUntil(String nombrePago, int maxSeconds, WebDriver driver) {
		String xpathIconoPago = getXPathIconoMetodoPago(nombrePago);
		return (state(Visible, By.xpath(xpathIconoPago), driver).wait(maxSeconds).check());
	}
	
	public static void clickIconoPago(String nombrePago, WebDriver driver) throws Exception {
		String xpathIconoPago = getXPathIconoMetodoPago(nombrePago);
		clickAndWaitLoad(driver, By.xpath(xpathIconoPago));
	}
}
