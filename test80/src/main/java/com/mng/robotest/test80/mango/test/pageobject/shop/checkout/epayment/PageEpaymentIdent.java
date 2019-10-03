package com.mng.robotest.test80.mango.test.pageobject.shop.checkout.epayment;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.mng.testmaker.webdriverwrapper.WebdrvWrapp;


public class PageEpaymentIdent extends WebdrvWrapp {

    static String XPathInputUser = "//input[@name[contains(.,'USERID')] and @type='password']";
    static String XPathInputCode = "//input[@name[contains(.,'IDNBR')] and @type='password']";
    
    public static boolean isPage(WebDriver driver) {
        return (driver.getTitle().contains("E-payment"));
    }
    
    public static boolean isPresentInputUserTypePassword(WebDriver driver) {
        return (isElementPresent(driver, By.xpath(XPathInputUser)));
    }
    
    public static boolean isPresentCodeUserTypePassword(WebDriver driver) {
        return (isElementPresent(driver, By.xpath(XPathInputCode)));
    }
}
