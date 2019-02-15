package com.mng.robotest.test80.mango.test.pageobject.shop.modales;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.mng.robotest.test80.mango.test.pageobject.WebdrvWrapp;


public class ModalBuscadorTallaTiendas extends WebdrvWrapp {

    static String XPathModalContainer = "//div[@class[contains(.,'container')]]";
    static String XPathTienda = XPathModalContainer + "//div[@class[contains(.,'dp__element')]]";
    static String XPathAspaForClose = "//span[@class[contains(.,'iconClose')]]";
    
    public static boolean isVisible(WebDriver driver) {
        return (isElementVisible(driver, By.xpath(XPathModalContainer))); 
    }
    
    public static boolean isPresentAnyTiendaUntil(WebDriver driver, int maxSecondsToWait) {
        return (isElementPresentUntil(driver, By.xpath(XPathTienda), maxSecondsToWait));
    }
    
    public static void clickAspaForClose(WebDriver driver) {
        driver.findElement(By.xpath(XPathAspaForClose)).click();
    }
}
