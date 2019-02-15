package com.mng.robotest.test80.mango.test.pageobject.shop.checkout.paypal;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.mng.robotest.test80.mango.test.pageobject.WebdrvWrapp;


public class PagePaypalSelectPago extends WebdrvWrapp {

    private final static String XPathContinueButton = "//*[@class[contains(.,'continueButton')]]";
    private final static String XPathMetPagos = "//div[@class[contains(.,'paywith')]]";

    public static boolean isPageUntil(int maxSecondsWait, WebDriver driver) {
    	return (WebdrvWrapp.isElementVisibleUntil(driver, By.xpath(XPathMetPagos), maxSecondsWait));
    }

    public static void clickContinuarButton(WebDriver driver) throws Exception {
    	WebdrvWrapp.clickAndWaitLoad(driver, By.xpath(XPathContinueButton));
    }
}
