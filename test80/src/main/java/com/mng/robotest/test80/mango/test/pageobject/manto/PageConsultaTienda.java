package com.mng.robotest.test80.mango.test.pageobject.manto;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.mng.robotest.test80.mango.test.pageobject.WebdrvWrapp;

@SuppressWarnings("javadoc")
public class PageConsultaTienda extends WebdrvWrapp {
    
    static String XPathInputTienda = "//input[@id[contains(.,'ID_TIENDA')]]";
    static String XPathBotonBuscar = "//input[@value[contains(.,'Buscar tienda')]]";
    static String XPathTiendaNoExiste = "//li[text()[contains(.,'La tienda no existe')]]";
    static String XPathTiendaExiste = "//span[text()[contains(.,'ID Tienda')]]";
    
    public static boolean isVisibleInputTienda(WebDriver driver) {
        return (isElementVisible(driver, By.xpath(XPathInputTienda)));
    }

	public static void introducirTienda(String tiendaNoExistente, WebDriver driver) {
		driver.findElement(By.xpath(XPathInputTienda)).click();
	    driver.findElement(By.xpath(XPathInputTienda)).clear();
	    driver.findElement(By.xpath(XPathInputTienda)).sendKeys(tiendaNoExistente);
	    driver.findElement(By.xpath(XPathBotonBuscar)).click();
		
	}

	public static boolean apareceMensajeTiendaNoExiste(WebDriver driver) {
		return isElementPresent(driver, By.xpath(XPathTiendaNoExiste));
	}

	public static boolean apareceInformacionTienda(WebDriver driver) {
		return isElementPresent(driver, By.xpath(XPathTiendaExiste));
	}

}
