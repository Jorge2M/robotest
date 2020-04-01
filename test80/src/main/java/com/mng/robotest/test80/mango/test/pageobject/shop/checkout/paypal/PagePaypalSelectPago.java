package com.mng.robotest.test80.mango.test.pageobject.shop.checkout.paypal;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static com.mng.testmaker.service.webdriver.pageobject.PageObjTM.*;
import static com.mng.testmaker.service.webdriver.pageobject.StateElement.State.*;


public class PagePaypalSelectPago {

    private final static String XPathContinueButton = "//*[@class[contains(.,'continueButton')]]";
    private final static String XPathMetPagos = "//div[@class[contains(.,'paywith')]]";

    public static boolean isPageUntil(int maxSeconds, WebDriver driver) {
    	return (state(Visible, By.xpath(XPathMetPagos), driver).wait(maxSeconds).check());
    }

	public static void clickContinuarButton(WebDriver driver) {
		click(By.xpath(XPathContinueButton), driver).exec();
	}
}
