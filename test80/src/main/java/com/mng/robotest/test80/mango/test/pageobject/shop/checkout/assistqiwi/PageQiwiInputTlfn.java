package com.mng.robotest.test80.mango.test.pageobject.shop.checkout.assistqiwi;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.mng.robotest.test80.mango.test.pageobject.WebdrvWrapp;


public class PageQiwiInputTlfn extends WebdrvWrapp {

    private final static String XPathInputPhone = "//input[@id='QIWIMobilePhone']";
    private final static String XPathLinkAceptar = "//input[@name='Submit_Card_1' and not(@disabled)]";
    
    public static void inputQiwiPhone(WebDriver driver, String phone) {
        driver.findElement(By.xpath(XPathInputPhone)).sendKeys(phone);
    }
    
    public static boolean isPresentInputPhone(WebDriver driver) {
        return (isElementPresent(driver, By.xpath(XPathInputPhone)));
    }    
    
    public static boolean isVisibleLinkAceptar(int maxSecondsWait, WebDriver driver) {
    	return (WebdrvWrapp.isElementVisibleUntil(driver, By.xpath(XPathLinkAceptar), maxSecondsWait));
    }
    
    public static void clickLinkAceptar(WebDriver driver) throws Exception {
    	WebdrvWrapp.clickAndWaitLoad(driver, By.xpath(XPathLinkAceptar));
    }
}