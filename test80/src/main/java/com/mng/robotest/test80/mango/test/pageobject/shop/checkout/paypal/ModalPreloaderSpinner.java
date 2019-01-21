package com.mng.robotest.test80.mango.test.pageobject.shop.checkout.paypal;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.mng.robotest.test80.mango.test.pageobject.WebdrvWrapp;

public class ModalPreloaderSpinner extends WebdrvWrapp {
	
    private final static String XPathPreloaderSpinner = "//div[@id='preloaderSpinner']";

    public static boolean isVisibleUntil(int maxSecondsToWait, WebDriver driver) {
        return (isElementVisibleUntil(driver, By.xpath(XPathPreloaderSpinner), maxSecondsToWait));
    }
    
    public static boolean isNotVisibleUntil(int maxSecondsToWait, WebDriver driver) {
        return (isElementInvisibleUntil(driver, By.xpath(XPathPreloaderSpinner), maxSecondsToWait));
    }
}
