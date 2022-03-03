package com.mng.robotest.test.pageobject.shop.checkout.paypal;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.service.webdriver.pageobject.TypeClick;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM.*;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

public class PagePaypalCreacionCuenta {
 
	static String XPathButtonIniciarSesion = "//div[@class[contains(.,'LoginButton')]]/a";
	static String XPathButtonAceptarYPagar = "//input[@track-submit='signup']";
	static String XPathButtonPagarAhora = "//input[@track-submit='guest_xo']";
	
	public static boolean isPageUntil(int maxSeconds, WebDriver driver) {
		String xpath = "(" + XPathButtonAceptarYPagar + ") | (" + XPathButtonPagarAhora + ") | (" + XPathButtonIniciarSesion + ")";
		return (state(Present, By.xpath(xpath), driver).wait(maxSeconds).check());
	}

	public static void clickButtonIniciarSesion(WebDriver driver) {
		click(By.xpath(XPathButtonIniciarSesion), driver).type(TypeClick.javascript).exec();
	}
}
