package com.mng.robotest.test80.mango.test.pageobject.shop.checkout;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM.*;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;


public class ModalAvisoCambioPais {

	static String XPathModal = "//div[@aria-labelledby[contains(.,'changeCountryModal')]]";
	static String XPathButtonConfCambio = XPathModal + "//button[@name='continue']";

	public static boolean isVisibleUntil(int maxSeconds, WebDriver driver) {
		return (state(Visible, By.xpath(XPathModal), driver)
				.wait(maxSeconds).check());
	}

	public static boolean isInvisibleUntil(int maxSeconds, WebDriver driver) {
		return (state(Invisible, By.xpath(XPathModal), driver)
				.wait(maxSeconds).check());
	}

	public static void clickConfirmarCambio(WebDriver driver) {
		waitForPageLoaded(driver);
		moveToElement(By.xpath(XPathButtonConfCambio), driver);
		click(By.xpath(XPathButtonConfCambio), driver).exec();
	}
}
