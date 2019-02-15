package com.mng.robotest.test80.mango.test.pageobject.shop.checkout.mercadopago;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.mng.robotest.test80.mango.test.pageobject.WebdrvWrapp;


public class PageMercpagoDatosTrjDesktop extends WebdrvWrapp {
    
    static String XPathInputNumTarj = "//input[@name='cardNumber']";
    static String XPathInputFecCaducidad = "//input[@name='cardExpiration']";
    static String XPathInputCVC = "//input[@id='securityCode']";
    static String XPathVisaIconNumTarj = "//span[@id='paymentmethod-logo']";
    static String XPathBotonContinuar = "//button[@id='submit']";
    static String XPathDivBancoToClick = "//div[@class[contains(.,'select-wrapper')]]";
    static String XPathOpcionBanco = "//ul[@class[contains(.,'select')]]/li";
    
    public static String getXPathOptionBanco(String litBanco) {
    	return XPathOpcionBanco + "//self::*[text()='" + litBanco + "']";
    }
    
    public static boolean isPageUntil(int maxSecondsToWait, WebDriver driver) {
        return (isElementVisibleUntil(driver, By.xpath(XPathInputCVC), maxSecondsToWait));
    }
    
    public static void sendNumTarj(String numTarjeta, WebDriver driver) {
        driver.findElement(By.xpath(XPathInputNumTarj)).sendKeys(numTarjeta);
    }
    
    public static boolean isVisibleVisaIconUntil(int maxSecondsToWait, WebDriver driver) {
        return (isElementVisibleUntil(driver, By.xpath(XPathVisaIconNumTarj), maxSecondsToWait));
    }
    
    public static void sendCaducidadTarj(String fechaVencimiento, WebDriver driver) {
        driver.findElement(By.xpath(XPathInputFecCaducidad)).sendKeys(fechaVencimiento);
    }
    
    public static void sendCVC(String cvc, WebDriver driver) {
        sendKeysWithRetry(3, cvc, By.xpath(XPathInputCVC), driver);
    }
    
    public static void selectBanco(String litBanco, WebDriver driver) {
    	driver.findElement(By.xpath(XPathDivBancoToClick)).click();
    	String xpathBanco = getXPathOptionBanco(litBanco);
    	driver.findElement(By.xpath(xpathBanco)).click();
    }
    
    public static void clickBotonContinuar(WebDriver driver) throws Exception {
        clickAndWaitLoad(driver, By.xpath(XPathBotonContinuar));    
    }
}
