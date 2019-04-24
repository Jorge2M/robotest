package com.mng.robotest.test80.mango.test.pageobject.shop.checkout.mercadopago;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class PageMercpagoDatosTrjMobil extends PageMercpagoDatosTrj {

    static String XPathWrapperVisa = "//div[@class='ui-card__wrapper']";
    static String XPathWrapperVisaUnactive = XPathWrapperVisa + "//div[@class='ui-card']";
    static String XPathWrapperVisaActive = XPathWrapperVisa + "//div[@class[contains(.,'ui-card__brand-debvisa')]]";
    static String XPathNextButton = "//button[@id[contains(.,'next')]]";
    static String XPathBackButton = "//button[@id[contains(.,'back')]]";
    static String XPathButtonNextPay = "//button[@id='submit' and @class[contains(.,'submit-arrow')]]";
    
    private PageMercpagoDatosTrjMobil(WebDriver driver) {
    	super(driver);
    }
    
    public static PageMercpagoDatosTrjMobil newInstance(WebDriver driver) {
    	return (new PageMercpagoDatosTrjMobil(driver));
    }

    @Override
    public boolean isPageUntil(int maxSecondsToWait) {
        return (isElementVisibleUntil(driver, By.xpath(XPathInputCvc), maxSecondsToWait));
    }
    
    @Override
    public void sendCaducidadTarj(String fechaVencimiento) {
        int i=0;
        while (!isElementClickableUntil(driver, By.xpath(XPathInputFecCaducidad), 1/*maxSeconds*/) && i<3) {
            clickNextButton();
            i+=1;
        }
        
        driver.findElement(By.xpath(XPathInputFecCaducidad)).sendKeys(fechaVencimiento);
    }
    
    @Override
    public void sendCvc(String securityCode) {
        int i=0;
        while (!isElementClickableUntil(driver, By.xpath(XPathInputCvc), 1/*maxSeconds*/) && i<3) {
            clickNextButton();
            i+=1;
        }
        
        driver.findElement(By.xpath(XPathInputCvc)).sendKeys(securityCode);        
    }
    
    public boolean isActiveWrapperVisaUntil(int maxSecondsToWait) {
        return (isElementVisibleUntil(driver, By.xpath(XPathWrapperVisaActive), maxSecondsToWait));
    }
    
    public void clickNextButton() {
        driver.findElement(By.xpath(XPathNextButton)).click();
    }
    
    public void clickBackButton() {
        driver.findElement(By.xpath(XPathBackButton)).click();
    }    
    
    public boolean isClickableButtonNextPayUntil(int maxSecondsToWait) {
        return (isElementClickableUntil(driver, By.xpath(XPathButtonNextPay), maxSecondsToWait));
    }
    
    public void clickButtonForPay() throws Exception {
        clickAndWaitLoad(driver, By.xpath(XPathButtonNextPay + " | " + XPathBotonPagar));
    }
}
