package com.mng.robotest.test80.mango.test.pageobject.shop.checkout.paypal;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static com.mng.testmaker.service.webdriver.pageobject.PageObjTM.*;
import static com.mng.testmaker.service.webdriver.pageobject.StateElement.State.*;


public class PagePaypalConfirmacion {

    private final static String XPathReviewPage = "//div[@class[contains(.,'review-page')]]";
    private final static String XPathContinueButton = "//input[@id='confirmButtonTop']";

    public static boolean isPageUntil(int maxSeconds, WebDriver driver) {
    	return (state(Visible, By.xpath(XPathReviewPage), driver)
    			.wait(maxSeconds).check());
    }

    public static void clickContinuarButton(WebDriver driver) throws Exception {
    	clickAndWaitLoad(driver, By.xpath(XPathContinueButton));
    }
}
