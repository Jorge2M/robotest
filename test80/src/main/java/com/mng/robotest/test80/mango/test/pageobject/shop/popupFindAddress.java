package com.mng.robotest.test80.mango.test.pageobject.shop;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.mng.robotest.test80.arq.utils.controlTest.fmwkTest;
import com.mng.robotest.test80.arq.webdriverwrapper.WebdrvWrapp;


/**
 * Implementa la API para el pupup que aparece cuando seleccionamos el botón "Find Address" desde la página de introducción de datos del usuario (actualmente sólo existe en Corea del Sur)
 * @author jorge.munoz
 *
 */
public class popupFindAddress extends WebdrvWrapp {
    private final static Logger pLogger = LogManager.getLogger(fmwkTest.log4jLogger);
    private final static String XPathInputBuscador = "//input[@id='region_name']";
    private final static String XPathButtonLupa = "//button[@class='btn_search']";
    private final static String XPathLinkDirecc = "//button[@class='link_post']";
    
    public static String goToPopupAndWait(String mainWindowHandle, int maxSecondsToWait, WebDriver driver) throws Exception { 
        String popupBuscador = switchToAnotherWindow(driver, mainWindowHandle);
        try {
            isIFrameUntil(maxSecondsToWait, driver);
        }
        catch (Exception e) {
            pLogger.warn("Exception going to Find Address Popup. ", e);
        }
        //wbdrvUtils.waitForPageLoaded(driver, maxSecondsToWait);
        return popupBuscador;
    }
    
    public static boolean isIFrameUntil(int maxSecondsToWait, WebDriver driver) {
        //driver.navigate().refresh();
        return (isElementPresentUntil(driver, By.xpath("//iframe"), maxSecondsToWait));
    }
    
    public static boolean isBuscadorClickableUntil(int maxSecondsToWait, WebDriver driver) {
        return (isElementClickableUntil(driver, By.xpath(XPathInputBuscador), maxSecondsToWait));
    }
    
    public static void setDataBuscador(WebDriver driver, String data) {
        driver.findElement(By.xpath(XPathInputBuscador)).sendKeys(data);
    }
    
    public static void clickButtonLupa(WebDriver driver) throws Exception {
        clickAndWaitLoad(driver, By.xpath(XPathButtonLupa));
    }
    
    public static void clickFirstDirecc(WebDriver driver) {
        driver.findElement(By.xpath(XPathLinkDirecc)).click();
    }
    
    public static void switchToIFrame(WebDriver driver) {
        driver.switchTo().frame(0);
    }
}
