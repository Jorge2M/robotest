package com.mng.robotest.test.pageobject.shop.checkout.paypal;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM.*;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

public class ModalPreloaderSpinner {
	
	private static final String XPathPreloaderSpinner = "//div[@id='preloaderSpinner']";

	public static boolean isVisibleUntil(int maxSeconds, WebDriver driver) {
		return (state(Visible, By.xpath(XPathPreloaderSpinner), driver)
				.wait(maxSeconds).check());
	}
	
	public static boolean isNotVisibleUntil(int maxSeconds, WebDriver driver) {
		return (state(Invisible, By.xpath(XPathPreloaderSpinner), driver)
				.wait(maxSeconds).check());
	}
}
