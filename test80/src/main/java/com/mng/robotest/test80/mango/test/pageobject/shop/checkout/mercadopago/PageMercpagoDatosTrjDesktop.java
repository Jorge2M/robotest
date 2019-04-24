package com.mng.robotest.test80.mango.test.pageobject.shop.checkout.mercadopago;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class PageMercpagoDatosTrjDesktop extends PageMercpagoDatosTrj {
    
    final static String XPathVisaIconNumTarj = "//span[@id='paymentmethod-logo']";
    final static String XPathBotonContinuar = "//button[@id='submit']";
    final static String XPathDivBancoToClick = "//div[@class[contains(.,'select-wrapper')]]";
    //final static String XPathOpcionBanco = "//ul[@class[contains(.,'select')]]/li";
    
    private PageMercpagoDatosTrjDesktop(WebDriver driver) {
    	super(driver);
    }
    
    public static PageMercpagoDatosTrjDesktop newInstance(WebDriver driver) {
    	return (new PageMercpagoDatosTrjDesktop(driver));
    }
    
//    public String getXPathOptionBanco(String litBanco) {
//    	return XPathOpcionBanco + "//self::*[text()='" + litBanco + "']";
//    }
    
    @Override
    public boolean isPageUntil(int maxSecondsToWait) {
        return (isElementVisibleUntil(driver, By.xpath(XPathInputCvc), maxSecondsToWait));
    }
    
    @Override
    public void sendCaducidadTarj(String fechaVencimiento) {
        driver.findElement(By.xpath(XPathInputFecCaducidad)).sendKeys(fechaVencimiento);
    }
    
    @Override
    public void sendCvc(String cvc) {
        sendKeysWithRetry(3, cvc, By.xpath(XPathInputCvc), driver);
    }
    
    public boolean isVisibleVisaIconUntil(int maxSecondsToWait) {
        return (isElementVisibleUntil(driver, By.xpath(XPathVisaIconNumTarj), maxSecondsToWait));
    }
    
//    public void selectBanco(String litBanco) {
//    	driver.findElement(By.xpath(XPathDivBancoToClick)).click();
//    	String xpathBanco = getXPathOptionBanco(litBanco);
//    	driver.findElement(By.xpath(xpathBanco)).click();
//    }
    
    public void clickBotonForContinue() throws Exception {
        clickAndWaitLoad(driver, By.xpath(XPathBotonContinuar + " | " + XPathBotonPagar));    
    }
}
