package com.mng.robotest.test80.mango.test.pageobject.shop.checkout.paypal;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.mng.robotest.test80.mango.test.pageobject.WebdrvWrapp;

@SuppressWarnings("javadoc")
public class PagePaypalCreacionCuenta extends WebdrvWrapp {
 
    static String XPathButtonIniciarSesion = "//div[@class[contains(.,'LoginButton')]]/a";
    static String XPathButtonAceptarYPagar = "//input[@track-submit='signup']";
    static String XPathButtonPagarAhora = "//input[@track-submit='guest_xo']";
    
    public static boolean isPageUntil(int maxSecondsToWait, WebDriver driver) {
        return (isElementPresentUntil(driver, By.xpath("(" + XPathButtonAceptarYPagar + ") | (" + XPathButtonPagarAhora + ") | (" + XPathButtonIniciarSesion + ")"), maxSecondsToWait));
    }
    
    public static void clickButtonIniciarSesion(WebDriver driver) throws Exception {
        clickAndWaitLoad(driver, By.xpath(XPathButtonIniciarSesion), TypeOfClick.javascript);
    }
}
