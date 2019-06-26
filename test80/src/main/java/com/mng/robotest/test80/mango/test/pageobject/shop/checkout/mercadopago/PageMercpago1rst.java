package com.mng.robotest.test80.mango.test.pageobject.shop.checkout.mercadopago;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.mng.robotest.test80.arq.webdriverwrapper.WebdrvWrapp;


public class PageMercpago1rst extends WebdrvWrapp {

	static String XPathInputNumTarjeta = "//input[@id='cardNumber']";
    static String XPathLinkRegistro = "//a[@href[contains(.,'changeGuestMail')]]";
    
    public static boolean isPageUntil(int maxSecondsToWait, WebDriver driver) {
    	return (isElementVisibleUntil(driver, By.xpath(XPathInputNumTarjeta), maxSecondsToWait));
    }
    
    public static void clickLinkRegistro(WebDriver driver) throws Exception {
        clickAndWaitLoad(driver, By.xpath(XPathLinkRegistro));
    }
}
