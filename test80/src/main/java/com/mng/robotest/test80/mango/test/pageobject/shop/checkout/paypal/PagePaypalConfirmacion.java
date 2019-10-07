package com.mng.robotest.test80.mango.test.pageobject.shop.checkout.paypal;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.mng.testmaker.service.webdriver.wrapper.WebdrvWrapp;


public class PagePaypalConfirmacion extends WebdrvWrapp {

    private final static String XPathReviewPage = "//div[@class[contains(.,'review-page')]]";
    private final static String XPathContinueButton = "//input[@id='confirmButtonTop']";

    public static boolean isPageUntil(int maxSecondsWait, WebDriver driver) {
    	return (WebdrvWrapp.isElementVisibleUntil(driver, By.xpath(XPathReviewPage), maxSecondsWait));
    }

    public static void clickContinuarButton(WebDriver driver) throws Exception {
    	WebdrvWrapp.clickAndWaitLoad(driver, By.xpath(XPathContinueButton));
    }
}
