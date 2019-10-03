package com.mng.robotest.test80.mango.test.pageobject.shop.checkout.sepa;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.mng.testmaker.utils.otras.Channel;
import com.mng.testmaker.webdriverwrapper.WebdrvWrapp;


public class PageSepa1rst extends WebdrvWrapp {
    
    static String XPathListOfPayments = "//ul[@id='paymentMethods']";
    static String XPathCabeceraStep = "//h2[@id[contains(.,'stageheader')]]";
    static String XPathButtonPagoDesktop = "//input[@class[contains(.,'paySubmit')] and @type='submit']";
    static String XPathButtonContinueMobil = "//input[@type='submit' and @id='mainSubmit']";
    static String XPathInputTitular = "//input[@id[contains(.,'ownerName')]]";
    static String XPathInputCuenta = "//input[@id[contains(.,'bankAccountNumber')]]";
    static String XPathRadioAcepto = "//input[@id[contains(.,'acceptDirectDebit')]]";
    static String XPathIconoSepaMobil = XPathListOfPayments + "//input[@class[contains(.,'sepa')]]"; 
    static String XPathIconoSepaDesktop = XPathListOfPayments + "/li[@data-variant[contains(.,'sepa')]]";
    
    public static String getXPathIconoSepa(Channel channel) {
        if (channel==Channel.movil_web) {
            return XPathIconoSepaMobil;
        }
        return XPathIconoSepaDesktop;
    }
    
    public static boolean isPresentIconoSepa(Channel channel, WebDriver driver) {
        String xpathPago = getXPathIconoSepa(channel);
        return (isElementPresent(driver, By.xpath(xpathPago)));
    }
    
    public static void clickIconoSepa(Channel channel, WebDriver driver) throws Exception {
        String xpathPago = getXPathIconoSepa(channel);
        clickAndWaitLoad(driver, By.xpath(xpathPago));
    }
    
    public static boolean isPresentCabeceraStep(WebDriver driver) {
        return (isElementPresent(driver, By.xpath(XPathCabeceraStep)));
    }
    
    public static boolean isPresentButtonPagoDesktop(WebDriver driver) {
        return (isElementPresent(driver, By.xpath(XPathButtonPagoDesktop)));
    }

    public static void clickButtonContinuePago(Channel channel, WebDriver driver) throws Exception {
        if (channel==Channel.movil_web) {
            clickAndWaitLoad(driver, By.xpath(XPathButtonContinueMobil));
        } else {
            clickAndWaitLoad(driver, By.xpath(XPathButtonPagoDesktop));
        }
    }
    
    public static boolean isPresentInputTitular(WebDriver driver) { 
        return (isElementPresent(driver, By.xpath(XPathInputTitular)));
    }

    public static boolean isPresentInputCuenta(WebDriver driver) { 
        return (isElementPresent(driver, By.xpath(XPathInputCuenta)));
    }
    
    public static void inputTitular(String titular, WebDriver driver) {
        driver.findElement(By.xpath(XPathInputTitular)).sendKeys(titular);
    }
    
    public static void inputCuenta(String cuenta, WebDriver driver) {
        driver.findElement(By.xpath(XPathInputCuenta)).sendKeys(cuenta);
    }
    
    public static void clickAcepto(WebDriver driver) {
        driver.findElement(By.xpath(XPathRadioAcepto)).click();
    }
}
