package com.mng.robotest.test80.mango.test.pageobject.shop;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static com.mng.testmaker.service.webdriver.pageobject.PageObjTM.*;
import static com.mng.testmaker.service.webdriver.pageobject.StateElement.State.*;


public class PageRecADomic {

	static String XPathIsPageRecogida ="//h1[text()[contains(.,'RECOGIDA A DOMICILIO')]]";
	static String XPathTableDevoluciones = "//table[@class[contains(.,'devoluciones_table')]]";
	static String XPathNoHayPedidos = "//p[text()[contains(.,'no tienes ningún pedido')]]";

	public static boolean isPage(WebDriver driver) {
		return (state(Present, By.xpath(XPathIsPageRecogida), driver).check());
	}

	public static boolean isTableDevoluciones(WebDriver driver) {
		return (state(Present, By.xpath(XPathTableDevoluciones), driver).check());
	}

	public static boolean hayPedidos(WebDriver driver) {
		return (state(Present, By.xpath(XPathNoHayPedidos), driver).check());
	}
}
