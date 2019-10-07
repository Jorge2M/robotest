package com.mng.robotest.test80.mango.test.pageobject.shop.checkout.assist;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.mng.testmaker.service.webdriver.wrapper.WebdrvWrapp;


public class PageAssistLast extends WebdrvWrapp {
    
    static String XPathButtonSubmit = "//button[@type='submit']";
    
    public static boolean isPage(WebDriver driver) {
        return (isElementPresent(driver, By.xpath(XPathButtonSubmit)));
    }
    
    public static void clickButtonSubmit(WebDriver driver) throws Exception {
        clickAndWaitLoad(driver, By.xpath(XPathButtonSubmit));
    }
}
