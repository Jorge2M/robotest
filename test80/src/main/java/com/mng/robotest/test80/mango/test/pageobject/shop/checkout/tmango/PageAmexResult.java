package com.mng.robotest.test80.mango.test.pageobject.shop.checkout.tmango;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.mng.robotest.test80.mango.test.pageobject.WebdrvWrapp;

@SuppressWarnings("javadoc")
public class PageAmexResult extends WebdrvWrapp {

    static String XPathSectionOK = "//div[@class[contains(.,'code ok')]]";
    static String XPathContinueButton = "//input[@class[contains(.,'btn-continue')]]";
    
    public static boolean isResultOkUntil(int maxSecondsToWait, WebDriver driver) {
        return (isElementPresentUntil(driver, By.xpath(XPathSectionOK), maxSecondsToWait));
    }
    
    public static boolean isPresentContinueButton(WebDriver driver) {
        return (isElementPresent(driver, By.xpath(XPathContinueButton)));
    }
    
    public static void clickContinuarButton(WebDriver driver) throws Exception {
        clickAndWaitLoad(driver, By.xpath(XPathContinueButton));
    }
}
