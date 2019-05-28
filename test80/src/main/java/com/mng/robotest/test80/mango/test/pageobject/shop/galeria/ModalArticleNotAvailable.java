package com.mng.robotest.test80.mango.test.pageobject.shop.galeria;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.mng.robotest.test80.arq.webdriverwrapper.WebdrvWrapp;


public class ModalArticleNotAvailable extends WebdrvWrapp {

    public enum StateModal {visible, notvisible}    
    static String XPathModal = "//div[@id='bocataAvisame']";
    static String XPathAspaForClose = XPathModal + "//div[@class[contains(.,'botonCerrarAvisame')]]";
    static String XPathRPGDText = XPathModal + "//p[@class='gdpr-text gdpr-data-protection']";
    
    public static boolean inStateUntil(StateModal stateModal, int maxSecondsToWait, WebDriver driver) {
        switch (stateModal) {
        case visible:
            return isVisibleUntil(maxSecondsToWait, driver);
        default:
        case notvisible:
            return isNotVisibleUntil(maxSecondsToWait, driver);
        }
    }
    
    public static boolean isVisibleUntil(int maxSecondsToWait, WebDriver driver) {
        return (isElementVisibleUntil(driver, By.xpath(XPathModal), maxSecondsToWait));
    }
    
    public static boolean isNotVisibleUntil(int maxSecondsToWait, WebDriver driver) {
        return (isElementInvisibleUntil(driver, By.xpath(XPathModal), maxSecondsToWait));
    }

    public static boolean isVisibleRPGD(int maxSecondsToWait, WebDriver driver) {
        return (isElementVisibleUntil(driver, By.xpath(XPathModal), maxSecondsToWait));
    }
    
    public static void clickAspaForClose(WebDriver driver) {
        driver.findElement(By.xpath(XPathAspaForClose)).click();
    }
}
