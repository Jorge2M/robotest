package com.mng.robotest.test80.mango.test.pageobject.shop.checkout.paypal;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.mng.testmaker.service.webdriver.wrapper.TypeOfClick;
import static com.mng.testmaker.service.webdriver.pageobject.PageObjTM.*;
import static com.mng.testmaker.service.webdriver.pageobject.StateElement.State.*;

public class PagePaypalCreacionCuenta {
 
    static String XPathButtonIniciarSesion = "//div[@class[contains(.,'LoginButton')]]/a";
    static String XPathButtonAceptarYPagar = "//input[@track-submit='signup']";
    static String XPathButtonPagarAhora = "//input[@track-submit='guest_xo']";
    
    public static boolean isPageUntil(int maxSeconds, WebDriver driver) {
    	String xpath = "(" + XPathButtonAceptarYPagar + ") | (" + XPathButtonPagarAhora + ") | (" + XPathButtonIniciarSesion + ")";
    	return (state(Present, By.xpath(xpath), driver).wait(maxSeconds).check());
    }
    
    public static void clickButtonIniciarSesion(WebDriver driver) throws Exception {
        clickAndWaitLoad(driver, By.xpath(XPathButtonIniciarSesion), TypeOfClick.javascript);
    }
}
