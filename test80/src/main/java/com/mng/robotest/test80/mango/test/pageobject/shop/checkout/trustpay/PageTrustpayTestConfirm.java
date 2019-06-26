package com.mng.robotest.test80.mango.test.pageobject.shop.checkout.trustpay;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.mng.robotest.test80.arq.webdriverwrapper.WebdrvWrapp;


public class PageTrustpayTestConfirm extends WebdrvWrapp {
    
    public enum typeButtons {OK, ANNOUNCED, FAIL, PENDING}
    static String XPathButtonOK = "//input[@id='btnOK']";
    static String XPathButtonAnnounced = "//input[@id='btnANNOUNCED']"; 
    static String XPathButtonFail = "//input[@id='btnFAIL']";
    static String XPathButtonPending = "//input[@id='btnOUT']";
    
    public static String getXPathButton(typeButtons typeButton) {
        switch (typeButton) {
        case OK:
            return XPathButtonOK;
        case ANNOUNCED:
            return XPathButtonAnnounced;
        case FAIL:
            return XPathButtonFail;
        case PENDING:
            return XPathButtonPending;
        default:
            return "";
        }        
    }
    
    public static boolean isPresentButton(typeButtons typeButton, WebDriver driver) {
        String xpathButton = getXPathButton(typeButton);
        return (isElementPresent(driver, By.xpath(xpathButton)));
    }
    
    public static void clickButton(typeButtons typeButton, WebDriver driver) throws Exception {
        String xpathButton = getXPathButton(typeButton);
        clickAndWaitLoad(driver, By.xpath(xpathButton));
    }
}
