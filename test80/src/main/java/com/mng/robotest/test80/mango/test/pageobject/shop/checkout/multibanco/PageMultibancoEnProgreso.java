package com.mng.robotest.test80.mango.test.pageobject.shop.checkout.multibanco;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.mng.robotest.test80.arq.webdriverwrapper.WebdrvWrapp;


public class PageMultibancoEnProgreso extends WebdrvWrapp {
    
    static String XPathCabeceraEnProgreso = "//h2[text()[contains(.,'Pagamento em progresso')]]";
    static String XPathButtonNextStep = "//input[@id='mainSubmit' and @type='submit']";
    
    public static boolean isPageUntil(int maxSecondsToWait, WebDriver driver) {
        return (isElementPresentUntil(driver, By.xpath(XPathCabeceraEnProgreso), maxSecondsToWait));
    }
    
    public static boolean isButonNextStep(WebDriver driver) {
        return (isElementPresent(driver, By.xpath(XPathButtonNextStep)));
    }
    
    public static void clickButtonNextStep(WebDriver driver) throws Exception {
        clickAndWaitLoad(driver, By.xpath(XPathButtonNextStep));
    }
}
