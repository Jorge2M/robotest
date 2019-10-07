package com.mng.robotest.test80.mango.test.pageobject.shop;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.mng.testmaker.service.webdriver.wrapper.WebdrvWrapp;


public class PageRecADomic extends WebdrvWrapp {

    static String XPathIsPageRecogida ="//h1[text()[contains(.,'RECOGIDA A DOMICILIO')]]";
    static String XPathTableDevoluciones = "//table[@class[contains(.,'devoluciones_table')]]";
    static String XPathNoHayPedidos = "//p[text()[contains(.,'no tienes ningún pedido')]]";
    
    public static boolean isPage(WebDriver driver) {
        return (isElementPresent(driver, By.xpath(XPathIsPageRecogida)));
    }
    
    public static boolean isTableDevoluciones(WebDriver driver) {
        return (isElementPresent(driver, By.xpath(XPathTableDevoluciones)));
    }
    
    public static boolean hayPedidos(WebDriver driver) {
        return (!isElementPresent(driver, By.xpath(XPathNoHayPedidos)));
    }
}
