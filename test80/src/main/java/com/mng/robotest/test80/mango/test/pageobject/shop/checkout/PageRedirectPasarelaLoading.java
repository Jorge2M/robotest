package com.mng.robotest.test80.mango.test.pageobject.shop.checkout;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.mng.robotest.test80.mango.test.pageobject.WebdrvWrapp;


public class PageRedirectPasarelaLoading extends WebdrvWrapp {

public static String XPathIsPage = "//div[@class[contains(.,'payment-redirect')]]/div[@class='loading' or @class='logo']";
    
    public static boolean isPageUntil(int maxSecondsToWait, WebDriver driver) {
        return (isElementPresentUntil(driver, By.xpath(XPathIsPage), maxSecondsToWait));
    }
    
    public static boolean isPageNotVisibleUntil(int maxSecondsToWait, WebDriver driver) {
        return (isElementInvisibleUntil(driver, By.xpath(XPathIsPage), maxSecondsToWait));
    }
}
