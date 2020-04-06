package com.mng.robotest.test80.mango.test.pageobject.shop.checkout.amazon;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM.*;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;


public class PageAmazonIdent {
	
    static String XPathImgLogoAmazon = "//div/img[@src[contains(.,'logo_payments')]]";
    static String XPathInputEmail = "//input[@id='ap_email']";
    static String XPathInputPassword = "//input[@id='ap_password']";
    
    public static boolean isLogoAmazon(WebDriver driver) { 
    	if (state(Visible, By.xpath(XPathImgLogoAmazon), driver).check()) {
            return true;
        }
        return false;
    }
    
    public static boolean isPageIdent(WebDriver driver) {
    	if (state(Visible, By.xpath(XPathInputEmail), driver).check() &&
    		state(Visible, By.xpath(XPathInputPassword), driver).check()) {
            return true;
        }
        return false;
    }
}
