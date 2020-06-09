package com.mng.robotest.test80.mango.test.pageobject.shop.checkout.paypal;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM.*;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;


public class PagePaypalSelectPago {

    private final static String XPathContinueButton = "//*[@class[contains(.,'ButtonPrimary')]]";
    private final static String XPathMetPagos = "//section[@class[contains(.,'PayWith')]]";

    public static boolean isPageUntil(int maxSeconds, WebDriver driver) {
    	return (state(Visible, By.xpath(XPathMetPagos), driver).wait(maxSeconds).check());
    }

	public static void clickContinuarButton(WebDriver driver) {
		click(By.xpath(XPathContinueButton), driver).exec();
	}
}
