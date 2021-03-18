package com.mng.robotest.test80.mango.test.pageobject.shop.checkout.tmango;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM.*;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;


public class PageAmexResult {

    static String XPathSectionOK = "//div[@class[contains(.,'code ok')]]";
    static String XPathContinueButton = "//input[@class[contains(.,'btn-continue')]]";
    
    public static boolean isResultOkUntil(int maxSeconds, WebDriver driver) {
    	return (state(Present, By.xpath(XPathSectionOK), driver)
    			.wait(maxSeconds).check());
    }
    
    public static boolean isPresentContinueButton(WebDriver driver) {
    	return (state(Present, By.xpath(XPathContinueButton), driver).check());
    }

	public static void clickContinuarButton(WebDriver driver) {
		click(By.xpath(XPathContinueButton), driver).exec();
	}
}
