package com.mng.robotest.test80.mango.test.pageobject.shop.checkout.giropay;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.mng.robotest.test80.arq.webdriverwrapper.WebdrvWrapp;


public class PageGiropayInputDataTest extends WebdrvWrapp {
    
    static String XPathFormPage = "//form[@action[contains(.,'BankNotification')]]";
    static String XPathInputSc = "//input[@name='sc']";
    static String XPathInputExtensionSc = "//input[@name='extensionSc']";
    static String XPathInputCustomerName = "//input[@name[contains(.,'customerName')]]";
    static String XPathInputIBAN = "//input[@name='customerIBAN']";
    static String XPathButtonAbseden = "//input[@value='Absenden']";
    
    public static boolean isPageUntil(int maxSecondsToWait, WebDriver driver) {
        return (isElementPresentUntil(driver, By.xpath(XPathFormPage), maxSecondsToWait));
    }
    
    public static void inputSc(String sc, WebDriver driver) {
        driver.findElement(By.xpath(XPathInputSc)).sendKeys(sc);
    }
    
    public static void inputExtensionSc(String extSc, WebDriver driver) {
        driver.findElement(By.xpath(XPathInputExtensionSc)).sendKeys(extSc);
    }
    
    public static void inputCustomerName(String customerName, WebDriver driver) {
        driver.findElement(By.xpath(XPathInputCustomerName)).sendKeys(customerName);
    }
    
    public static void inputIBAN(String iban, WebDriver driver) {
        driver.findElement(By.xpath(XPathInputIBAN)).sendKeys(iban);
    }
    
    public static void clickButtonAbseden(WebDriver driver) throws Exception {
        clickAndWaitLoad(driver, By.xpath(XPathButtonAbseden));
    }
}
