package com.mng.robotest.test80.mango.test.pageobject.manto;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM.*;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;


public class PageConsultaTienda {
    
    static String XPathInputTienda = "//input[@id[contains(.,'ID_TIENDA')]]";
    static String XPathBotonBuscar = "//input[@value[contains(.,'Buscar tienda')]]";
    static String XPathTiendaNoExiste = "//li[text()[contains(.,'La tienda no existe')]]";
    static String XPathTiendaExiste = "//span[text()[contains(.,'ID Tienda')]]";

	public static boolean isVisibleInputTienda(WebDriver driver) {
		return (state(Visible, By.xpath(XPathInputTienda), driver).check());
	}

	public static void introducirTienda(String tiendaNoExistente, WebDriver driver) {
		driver.findElement(By.xpath(XPathInputTienda)).click();
		driver.findElement(By.xpath(XPathInputTienda)).clear();
		driver.findElement(By.xpath(XPathInputTienda)).sendKeys(tiendaNoExistente);
		driver.findElement(By.xpath(XPathBotonBuscar)).click();
	}

	public static boolean apareceMensajeTiendaNoExiste(WebDriver driver) {
		return (state(Present, By.xpath(XPathTiendaNoExiste), driver).check());
	}

	public static boolean apareceInformacionTienda(WebDriver driver) {
		return (state(Present, By.xpath(XPathTiendaExiste), driver).check());
	}

}
