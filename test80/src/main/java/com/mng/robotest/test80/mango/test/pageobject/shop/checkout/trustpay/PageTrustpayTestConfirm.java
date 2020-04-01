package com.mng.robotest.test80.mango.test.pageobject.shop.checkout.trustpay;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static com.mng.testmaker.service.webdriver.pageobject.PageObjTM.*;
import static com.mng.testmaker.service.webdriver.pageobject.StateElement.State.*;


public class PageTrustpayTestConfirm {
    
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
        return (state(Present, By.xpath(xpathButton), driver).check());
    }

	public static void clickButton(typeButtons typeButton, WebDriver driver) {
		String xpathButton = getXPathButton(typeButton);
		click(By.xpath(xpathButton), driver).exec();
	}
}
