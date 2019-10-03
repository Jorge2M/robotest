package com.mng.robotest.test80.mango.test.pageobject.shop.checkout.dotpay;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.mng.testmaker.webdriverwrapper.WebdrvWrapp;


public class PageDotpayAcceptSimulation extends WebdrvWrapp {
    
    static String XPathRedButtonAceptar = "//input[@id='submit_success' and @type='submit']";
    static String title = "Dotpay payment simulation";
    
    public static boolean isPage(WebDriver driver) {
        return (driver.getTitle().contains(title));
    }
    
    public static boolean isPresentRedButtonAceptar(WebDriver driver) {
        return (isElementPresent(driver, By.xpath(XPathRedButtonAceptar)));
    }
    
    public static void clickRedButtonAceptar(WebDriver driver) throws Exception {
        clickAndWaitLoad(driver, By.xpath(XPathRedButtonAceptar));
    }
}
