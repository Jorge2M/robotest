package com.mng.robotest.test80.mango.test.pageobject.shop.modales;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.mng.robotest.test80.arq.webdriverwrapper.WebdrvWrapp;


public class ModalBuscadorTiendas extends WebdrvWrapp {

    static String XPathModalContainer = "//div[@class[contains(.,'garment-finder-container')]]";
    static String XPathTienda = XPathModalContainer + "//div[@class[contains(.,'list-store')]]";
    static String XPathAspaForClose = "//button[@class[contains(.,'close-modal')]]";
    
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
