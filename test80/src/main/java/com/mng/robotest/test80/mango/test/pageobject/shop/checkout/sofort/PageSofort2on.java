package com.mng.robotest.test80.mango.test.pageobject.shop.checkout.sofort;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;

import com.mng.testmaker.webdriverwrapper.WebdrvWrapp;


/**
 * Page2: la página de selección del país
 * @author jorge.munoz
 *
 */
public class PageSofort2on extends WebdrvWrapp {
    static String XPathSelectPaises = "//select[@id[contains(.,'Country')]]";
    static String XPathInputBankCode = "//input[@id[contains(.,'BankCodeSearch')]]";
    static String XPathSubmitButton = "//form//button[@class[contains(.,'primary')]]";
    
    public static boolean isPageUntil(int maxSecondsToWait, WebDriver driver) {
        return (isElementPresentUntil(driver, By.xpath(XPathSelectPaises), maxSecondsToWait));
    }
    
    public static void selectPais(WebDriver driver, String pais) {
        new Select(driver.findElement(By.xpath(XPathSelectPaises))).selectByValue(pais);
    }    
    
    public static void inputBankcode(String bankCode, WebDriver driver) {
        driver.findElement(By.xpath(XPathInputBankCode)).sendKeys(bankCode);
    }
    
    public static void clickSubmitButtonPage3(WebDriver driver) throws Exception {
        clickAndWaitLoad(driver, By.xpath(XPathSubmitButton));
    }   
}
