package com.mng.robotest.test80.mango.test.pageobject.shop.modales;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM.*;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;


public class ModalBuscadorTiendasMisCompras {

    static String XPathModalContainer = "//div[@class[contains(.,'selectStoreContainer')]]";
    static String XPathTienda = XPathModalContainer + "//div[@class[contains(.,'dp__element')]]";
    static String XPathAspaForClose = "//span[@class[contains(.,'iconClose')]]";
    
    public static boolean isVisible(WebDriver driver) {
    	return (state(Visible, By.xpath(XPathModalContainer), driver).check());
    }
    
    public static boolean isPresentAnyTiendaUntil(WebDriver driver, int maxSeconds) {
    	return (state(Present, By.xpath(XPathTienda), driver).wait(maxSeconds).check());
    }
    
    public static void clickAspaForClose(WebDriver driver) throws Exception {
        driver.findElement(By.xpath(XPathAspaForClose)).click();
        waitForPageLoaded(driver);
    }
}
