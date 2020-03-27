package com.mng.robotest.test80.mango.test.pageobject.shop.checkout.assist;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static com.mng.testmaker.service.webdriver.pageobject.PageObjTM.*;
import static com.mng.testmaker.service.webdriver.pageobject.StateElement.State.*;


public class PageAssistLast {
    
    static String XPathButtonSubmit = "//button[@type='submit']";
    
    public static boolean isPage(WebDriver driver) {
    	return (state(Present, By.xpath(XPathButtonSubmit), driver).check());
    }
    
    public static void clickButtonSubmit(WebDriver driver) throws Exception {
        clickAndWaitLoad(driver, By.xpath(XPathButtonSubmit));
    }
}
