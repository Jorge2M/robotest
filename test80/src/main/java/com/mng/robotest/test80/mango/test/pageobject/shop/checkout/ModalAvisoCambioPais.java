package com.mng.robotest.test80.mango.test.pageobject.shop.checkout;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.mng.testmaker.webdriverwrapper.WebdrvWrapp;


public class ModalAvisoCambioPais extends WebdrvWrapp {

    static String XPathModal = "//div[@class[contains(.,'modal-alert-change-country')]]";
    static String XPathButtonConfCambio = "//input[@id[contains(.,'ModalDatosEnvio')] and @class[contains(.,'changeCountry')]]";
    
    public static boolean isVisibleUntil(int maxSecondsToWait, WebDriver driver) {
        return (isElementVisibleUntil(driver, By.xpath(XPathModal), maxSecondsToWait));
    }
    
    public static boolean isInvisibleUntil(int maxSecondsToWait, WebDriver driver) {
        return (isElementInvisibleUntil(driver, By.xpath(XPathModal), maxSecondsToWait));
    }
    
    public static void clickConfirmarCambio(WebDriver driver) throws Exception {
        waitForPageLoaded(driver);
        moveToElement(By.xpath(XPathButtonConfCambio), driver);
        clickAndWaitLoad(driver, By.xpath(XPathButtonConfCambio));
    }
}
