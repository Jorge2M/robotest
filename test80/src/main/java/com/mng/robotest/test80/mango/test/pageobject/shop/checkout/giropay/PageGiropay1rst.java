package com.mng.robotest.test80.mango.test.pageobject.shop.checkout.giropay;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;

import com.mng.testmaker.utils.otras.Channel;
import com.mng.testmaker.service.webdriver.wrapper.WebdrvWrapp;


public class PageGiropay1rst extends WebdrvWrapp {
    static String XPathListOfPayments = "//ul[@id='paymentMethods']";
    static String XPathCabeceraStep = "//h2[@id[contains(.,'stageheader')]]";
    static String XPathButtonPagoDesktop = "//input[@class[contains(.,'paySubmit')] and @type='submit']";
    static String XPathButtonContinueMobil = "//input[@type='submit' and @id='mainSubmit']";
    static String XPathInputBank = "//input[@id[contains(.,'giropay.bic-selection')]]";
    static String XPathIconoGiropayMobil = XPathListOfPayments + "//input[@class[contains(.,'giropay')]]";
    static String XPathIconoGiropayDesktop = XPathListOfPayments + "/li[@data-variant[contains(.,'giropay')]]";
    
    public static String getXPathIconoGiropay(Channel channel) {
        if (channel==Channel.movil_web) {
            return XPathIconoGiropayMobil;
        }
        return XPathIconoGiropayDesktop;
    }
    
    public static String getXPath_rowListWithBank(String bank) {
        return ("//ul[@id='giropaysuggestionlist']/li[@data-bankname[contains(.,'" + bank + "')]]");
    }
    
    public static boolean isPresentIconoGiropay(Channel channel, WebDriver driver) {
        String xpathPago = getXPathIconoGiropay(channel);
        return (isElementPresent(driver, By.xpath(xpathPago)));
    }
    
    public static boolean isPresentCabeceraStep(WebDriver driver) {
        return (isElementPresent(driver, By.xpath(XPathCabeceraStep)));
    }
    
    public static boolean isPresentButtonPagoDesktop(WebDriver driver) {
        return (isElementPresent(driver, By.xpath(XPathButtonPagoDesktop)));
    }

    public static boolean isVisibleInputBankUntil(int maxSecondsToWait, WebDriver driver) {
        return (isElementVisibleUntil(driver, By.xpath(XPathInputBank), maxSecondsToWait));
    }
    
    public static void inputBank(String bank, Channel channel, WebDriver driver) throws Exception {
        if (channel==Channel.movil_web) {
            if (!isElementVisible(driver, By.xpath(XPathInputBank))) {
                clickIconoGiropay(channel, driver);
            }
        }
        
        driver.findElement(By.xpath(XPathInputBank)).sendKeys(bank);
        waitForPageLoaded(driver, 1/*waitSeconds*/);
    }
    
    public static void inputTabInBank(WebDriver driver) throws Exception {
        driver.findElement(By.xpath(XPathInputBank)).sendKeys(Keys.TAB);
        waitForPageLoaded(driver, 1/*waitSeconds*/);        
    }
    
    public static void clickIconoGiropay(Channel channel, WebDriver driver) throws Exception {
        String xpathPago = getXPathIconoGiropay(channel);
        clickAndWaitLoad(driver, By.xpath(xpathPago));
    }
    
    public static void clickButtonContinuePay(Channel channel, WebDriver driver) throws Exception {
        if (channel==Channel.movil_web) {
            clickButtonContinueMobil(driver);
        } else {
            clickButtonPagoDesktop(driver);
        }
    }
    
    public static void clickButtonPagoDesktop(WebDriver driver) throws Exception {
        clickAndWaitLoad(driver, By.xpath(XPathButtonPagoDesktop));
    }
    
    public static void clickButtonContinueMobil(WebDriver driver) throws Exception {
        clickAndWaitLoad(driver, By.xpath(XPathButtonContinueMobil));
    }
    
    public static boolean isVisibleBankInListUntil(String bank, int maxSecondsToWait, WebDriver driver) {
        String xpathRow = getXPath_rowListWithBank(bank);
        return (isElementVisibleUntil(driver, By.xpath(xpathRow), maxSecondsToWait));
    }
    
    public static boolean isInvisibleBankInListUntil(String bank, int maxSecondsToWait, WebDriver driver) {
        String xpathRow = getXPath_rowListWithBank(bank);
        return (isElementInvisibleUntil(driver, By.xpath(xpathRow), maxSecondsToWait));
    }    
}
