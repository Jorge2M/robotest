package com.mng.robotest.test80.mango.test.pageobject.shop.checkout.mercadopago;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.mng.robotest.test80.mango.test.pageobject.WebdrvWrapp;


public class PageMercpagoDatosTrjMobil extends WebdrvWrapp {

    static String XPathInputCardNumber = "//input[@id='cardNumber']";
    static String XPathInputFecCaducidad = "//input[@id='cardExpiration']";
    static String XPathInputCvc = "//input[@id='securityCode']";
    static String XPathWrapperVisa = "//div[@class='ui-card__wrapper']";
    static String XPathWrapperVisaUnactive = XPathWrapperVisa + "//div[@class='ui-card']";
    static String XPathWrapperVisaActive = XPathWrapperVisa + "//div[@class[contains(.,'ui-card__brand-debvisa')]]";
    static String XPathNextButton = "//button[@id[contains(.,'next')]]";
    static String XPathBackButton = "//button[@id[contains(.,'back')]]";
    static String XPathButtonNextPay = "//button[@id='submit' and @class[contains(.,'submit-arrow')]]";
    
    public static boolean isPage(WebDriver driver) {
        return (isElementVisible(driver, By.xpath(XPathInputCardNumber)));
    }
    
    public static boolean isActiveWrapperVisaUntil(int maxSecondsToWait, WebDriver driver) {
        return (isElementVisibleUntil(driver, By.xpath(XPathWrapperVisaActive), maxSecondsToWait));
    }
    
    public static void sendNumTarj(String numTarj, WebDriver driver) {
        driver.findElement(By.xpath(XPathInputCardNumber)).sendKeys(numTarj);
    }
    
    public static void sendCaducidadTarj(String fechaVencimiento, WebDriver driver) {
        int i=0;
        while (!isElementClickableUntil(driver, By.xpath(XPathInputFecCaducidad), 1/*maxSeconds*/) && i<3) {
            clickNextButton(driver);
            i+=1;
        }
        
        driver.findElement(By.xpath(XPathInputFecCaducidad)).sendKeys(fechaVencimiento);
    }
    
    public static void sendCVC(String securityCode, WebDriver driver) {
        int i=0;
        while (!isElementClickableUntil(driver, By.xpath(XPathInputCvc), 1/*maxSeconds*/) && i<3) {
            clickNextButton(driver);
            i+=1;
        }
        
        driver.findElement(By.xpath(XPathInputCvc)).sendKeys(securityCode);        
    }
    
    public static void clickNextButton(WebDriver driver) {
        driver.findElement(By.xpath(XPathNextButton)).click();
    }
    
    public static void clickBackButton(WebDriver driver) {
        driver.findElement(By.xpath(XPathBackButton)).click();
    }    
    
    public static boolean isClickableButtonNextPayUntil(int maxSecondsToWait, WebDriver driver) {
        return (isElementClickableUntil(driver, By.xpath(XPathButtonNextPay), maxSecondsToWait));
    }
    
    public static void clickButtonNextPay(WebDriver driver) throws Exception {
        clickAndWaitLoad(driver, By.xpath(XPathButtonNextPay));
    }
}
