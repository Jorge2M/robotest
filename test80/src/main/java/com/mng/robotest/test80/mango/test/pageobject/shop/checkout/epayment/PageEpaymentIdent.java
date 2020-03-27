package com.mng.robotest.test80.mango.test.pageobject.shop.checkout.epayment;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static com.mng.testmaker.service.webdriver.pageobject.PageObjTM.*;
import static com.mng.testmaker.service.webdriver.pageobject.StateElement.State.*;


public class PageEpaymentIdent {

    static String XPathInputUser = "//input[@name[contains(.,'USERID')] and @type='password']";
    static String XPathInputCode = "//input[@name[contains(.,'IDNBR')] and @type='password']";
    
    public static boolean isPage(WebDriver driver) {
        return (driver.getTitle().contains("E-payment"));
    }
    
    public static boolean isPresentInputUserTypePassword(WebDriver driver) {
    	return (state(Present, By.xpath(XPathInputUser), driver).check());
    }
    
    public static boolean isPresentCodeUserTypePassword(WebDriver driver) {
    	return (state(Present, By.xpath(XPathInputCode), driver).check());
    }
}
