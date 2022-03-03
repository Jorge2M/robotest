package com.mng.robotest.test.pageobject.shop.checkout.dotpay;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM.*;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;


public class PageDotpayAcceptSimulation {
	
	static String XPathEncabezado = "//h1[text()[contains(.,'Simulation of payment')]]";
	static String XPathRedButtonAceptar = "//input[@id='submit_success' and @type='submit']";

	public static boolean isPage(int maxSeconds, WebDriver driver) {
		return (state(Visible, By.xpath(XPathEncabezado), driver).wait(maxSeconds).check());
	}
	
	public static boolean isPresentRedButtonAceptar(WebDriver driver) {
		return (state(Present, By.xpath(XPathRedButtonAceptar), driver).check());
	}

	public static void clickRedButtonAceptar(WebDriver driver) {
		click(By.xpath(XPathRedButtonAceptar), driver).exec();
	}
}
