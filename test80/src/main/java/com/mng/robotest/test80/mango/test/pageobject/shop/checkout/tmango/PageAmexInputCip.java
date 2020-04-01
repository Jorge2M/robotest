package com.mng.robotest.test80.mango.test.pageobject.shop.checkout.tmango;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static com.mng.testmaker.service.webdriver.pageobject.PageObjTM.*;
import static com.mng.testmaker.service.webdriver.pageobject.StateElement.State.*;


public class PageAmexInputCip {

    static String XPathInputCIP = "//input[@name='pin']";
    static String XPathAcceptButton = "//img[@src[contains(.,'daceptar.gif')]]/../../a";
    
    public static boolean isPageUntil(int maxSeconds, WebDriver driver) {
    	return (state(Present, By.xpath(XPathInputCIP), driver)
    			.wait(maxSeconds).check());
    }
    
    public static void inputCIP(String CIP, WebDriver driver) {
        driver.findElement(By.xpath(XPathInputCIP)).sendKeys(CIP);
    }

	public static void clickAceptarButton(WebDriver driver) {
		click(By.xpath(XPathAcceptButton), driver).exec();
	}
}
