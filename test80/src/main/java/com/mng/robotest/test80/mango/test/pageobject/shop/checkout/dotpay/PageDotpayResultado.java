package com.mng.robotest.test80.mango.test.pageobject.shop.checkout.dotpay;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.mng.robotest.test80.mango.test.pageobject.WebdrvWrapp;


public class PageDotpayResultado extends WebdrvWrapp {

    static String XPathButtonNext = "//input[@type='submit' and @value='Next']";
    
    public static boolean isPageResultadoOk(WebDriver driver) {
        return (driver.getPageSource().contains("Payment successful"));
    }

    public static void clickButtonNext(WebDriver driver) throws Exception {
        clickAndWaitLoad(driver, By.xpath(XPathButtonNext));
    }
}
