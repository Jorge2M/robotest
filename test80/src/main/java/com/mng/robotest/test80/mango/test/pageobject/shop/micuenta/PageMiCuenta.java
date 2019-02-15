package com.mng.robotest.test80.mango.test.pageobject.shop.micuenta;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.mng.robotest.test80.mango.test.pageobject.WebdrvWrapp;


public class PageMiCuenta extends WebdrvWrapp {
    static String XPathLinkMisDatos = "//a[@href[contains(.,'account/personalinfo')]]";
    static String XPathLinkMisPedidos = "//a[@href[contains(.,'account/orders')]]";
    static String XPathLinkMisCompras = "//a[@href[contains(.,'/mypurchases')]]";
    static String XPathLinkSuscripciones = "//a[@href[contains(.,'account/suscriptions')]]";
    static String XPathLinkDevoluciones = "//span[@data-event-category='devoluciones']";
    static String XPathLinkReembolsos = "//a[@data-event-category='mi-cuenta-reembolsos']";
    
    public static boolean isPageUntil(int maxSecondsToWait, WebDriver driver) {
    	return (isElementVisibleUntil(driver, By.xpath(XPathLinkMisDatos), maxSecondsToWait));
    }
    
    public static void clickMisPedidos(WebDriver driver) throws Exception {
        clickAndWaitLoad(driver, By.xpath(XPathLinkMisPedidos));
    }
    
    public static void clickMisCompras(WebDriver driver) throws Exception {
        clickAndWaitLoad(driver, By.xpath(XPathLinkMisCompras));
    }
    
    public static void clickSuscripciones(WebDriver driver) throws Exception {
        clickAndWaitLoad(driver, By.xpath(XPathLinkSuscripciones));
    }
    
    public static void clickDevoluciones(WebDriver driver) throws Exception {
        clickAndWaitLoad(driver, By.xpath(XPathLinkDevoluciones));
    }
    
    public static void clickReembolsos(WebDriver driver) throws Exception {
        clickAndWaitLoad(driver, By.xpath(XPathLinkReembolsos), TypeOfClick.javascript);
    }
    
    public static void clickMisDatos(WebDriver driver) throws Exception {
        clickAndWaitLoad(driver, By.xpath(XPathLinkMisDatos));
    }    
}
