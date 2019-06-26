package com.mng.robotest.test80.mango.test.pageobject.shop.checkout.sepa;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.mng.robotest.test80.arq.webdriverwrapper.WebdrvWrapp;


public class PageSepaResultMobil extends WebdrvWrapp {

    static String XPathButtonPay = "//input[@type='submit' and @id='mainSubmit']";
    static String XPathStage3Header = "//h2[@id='stageheader' and text()[contains(.,'3:')]]";
    
    public static boolean isPage(WebDriver driver) {
        return (isElementPresent(driver, By.xpath(XPathStage3Header)));
    }
    
    public static void clickButtonPay(WebDriver driver) throws Exception {
        clickAndWaitLoad(driver, By.xpath(XPathButtonPay));
    }
}
