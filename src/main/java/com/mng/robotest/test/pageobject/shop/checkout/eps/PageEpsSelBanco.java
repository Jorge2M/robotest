package com.mng.robotest.test.pageobject.shop.checkout.eps;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM.*;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;


/**
 * Page1: la página inicial de iDEAL (la posterior a la selección del botón "Confirmar Pago")
 * Page2: la página de simulación
 * @author jorge.munoz
 *
 */
public class PageEpsSelBanco {

	static String XPathIconoEps = "//div[@class='header-logo']";
	static String XPathIconoBanco = "//div[@class='loginlogo']";

	public static boolean isPresentIconoEps(WebDriver driver) {
		return (state(Present, By.xpath(XPathIconoEps), driver).check());
	}
	
	public static boolean isVisibleIconoBanco(WebDriver driver) {
		return (state(Present, By.xpath(XPathIconoBanco), driver).check());
	}
}
