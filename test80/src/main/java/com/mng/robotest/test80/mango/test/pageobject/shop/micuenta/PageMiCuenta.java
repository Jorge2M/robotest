package com.mng.robotest.test80.mango.test.pageobject.shop.micuenta;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.mng.testmaker.service.webdriver.wrapper.TypeOfClick;
import com.mng.testmaker.service.webdriver.wrapper.WebdrvWrapp;


public class PageMiCuenta extends WebdrvWrapp {
	
	private final WebDriver driver;
	
    private static String XPathLinkMisDatos = "//a[@href[contains(.,'account/personalinfo')]]";
    private static String XPathLinkMisPedidos = "//a[@href[contains(.,'account/orders')]]";
    private static String XPathLinkMisCompras = "//a[@href[contains(.,'/mypurchases')]]";
    private static String XPathLinkSuscripciones = "//a[@href[contains(.,'account/suscriptions')]]";
    private static String XPathLinkDevoluciones = "//span[@data-event-category='devoluciones']";
    private static String XPathLinkReembolsos = "//a[@data-event-category='mi-cuenta-reembolsos']";
    
    private PageMiCuenta(WebDriver driver) {
    	this.driver = driver;
    }
    public static PageMiCuenta getNew(WebDriver driver) {
    	return (new PageMiCuenta(driver));
    }
    
    public boolean isPageUntil(int maxSecondsToWait) {
    	return (isElementVisibleUntil(driver, By.xpath(XPathLinkMisDatos), maxSecondsToWait));
    }
    
    public void clickMisPedidos() throws Exception {
        clickAndWaitLoad(driver, By.xpath(XPathLinkMisPedidos));
    }
    
    public void clickMisCompras() throws Exception {
        clickAndWaitLoad(driver, By.xpath(XPathLinkMisCompras));
    }
    
    public void clickSuscripciones() throws Exception {
        clickAndWaitLoad(driver, By.xpath(XPathLinkSuscripciones));
    }
    
    public void clickDevoluciones() throws Exception {
        clickAndWaitLoad(driver, By.xpath(XPathLinkDevoluciones));
    }
    
    public void clickReembolsos() throws Exception {
        clickAndWaitLoad(driver, By.xpath(XPathLinkReembolsos), TypeOfClick.javascript);
    }
    
    public void clickMisDatos() throws Exception {
        clickAndWaitLoad(driver, By.xpath(XPathLinkMisDatos));
    }    
}
