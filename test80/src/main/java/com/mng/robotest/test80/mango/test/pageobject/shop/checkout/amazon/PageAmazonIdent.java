package com.mng.robotest.test80.mango.test.pageobject.shop.checkout.amazon;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.mng.robotest.test80.mango.test.pageobject.WebdrvWrapp;


public class PageAmazonIdent extends WebdrvWrapp {
    static String XPathImgLogoAmazon = "//div/img[@src[contains(.,'logo_payments')]]";
    static String XPathInputEmail = "//input[@id='ap_email']";
    static String XPathInputPassword = "//input[@id='ap_password']";
    
    public static boolean isLogoAmazon(WebDriver driver) { 
        if (isElementVisible(driver, By.xpath(XPathImgLogoAmazon)))
            return true;
        
        return false;
    }
    
    public static boolean isPageIdent(WebDriver driver) {
        if (isElementVisible(driver, By.xpath(XPathInputEmail)) &&
            isElementVisible(driver, By.xpath(XPathInputPassword)) )
            return true;
        
        return false;
    }
}
