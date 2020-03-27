package com.mng.robotest.test80.mango.test.pageobject.shop.checkout.sepa;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static com.mng.testmaker.service.webdriver.pageobject.PageObjTM.*;
import static com.mng.testmaker.service.webdriver.pageobject.StateElement.State.*;


public class PageSepaResultMobil {

    static String XPathButtonPay = "//input[@type='submit' and @id='mainSubmit']";
    static String XPathStage3Header = "//h2[@id='stageheader' and text()[contains(.,'3:')]]";
    
    public static boolean isPage(WebDriver driver) {
    	return (state(Present, By.xpath(XPathStage3Header), driver).check());
    }
    
    public static void clickButtonPay(WebDriver driver) throws Exception {
        clickAndWaitLoad(driver, By.xpath(XPathButtonPay));
    }
}
