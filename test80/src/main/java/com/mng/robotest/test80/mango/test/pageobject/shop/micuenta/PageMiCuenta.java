package com.mng.robotest.test80.mango.test.pageobject.shop.micuenta;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.TypeClick.*;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;


public class PageMiCuenta extends PageObjTM {
	
    private static String XPathLinkMisDatos = "//a[@href[contains(.,'account/personalinfo')]]";
//    private static String XPathLinkMisPedidos = "//a[@href[contains(.,'account/orders')]]";
    private static String XPathLinkMisCompras = "//a[@href[contains(.,'/mypurchases')]]";
    private static String XPathLinkSuscripciones = "//a[@href[contains(.,'account/suscriptions')]]";
    private static String XPathLinkDevoluciones = "//span[@data-event-category='devoluciones']";
    private static String XPathLinkReembolsos = "//a[@data-event-category='mi-cuenta-reembolsos']";
    
    private PageMiCuenta(WebDriver driver) {
    	super(driver);
    }
    public static PageMiCuenta getNew(WebDriver driver) {
    	return (new PageMiCuenta(driver));
    }
    
    public boolean isPageUntil(int maxSeconds) {
    	return (state(Visible, By.xpath(XPathLinkMisDatos)).wait(maxSeconds).check());
    }
    
//    public void clickMisPedidos() {
//    	click(By.xpath(XPathLinkMisPedidos)).exec();
//    }
    
    public void clickMisCompras() {
    	click(By.xpath(XPathLinkMisCompras)).exec();
    }
    
    public void clickSuscripciones() {
    	click(By.xpath(XPathLinkSuscripciones)).exec();
    }
    
    public void clickDevoluciones() {
    	click(By.xpath(XPathLinkDevoluciones)).exec();
    }
    
    public void clickReembolsos() {
    	click(By.xpath(XPathLinkReembolsos)).type(javascript).exec();
    }
    
    public void clickMisDatos() {
    	click(By.xpath(XPathLinkMisDatos)).exec();
    }    
}
