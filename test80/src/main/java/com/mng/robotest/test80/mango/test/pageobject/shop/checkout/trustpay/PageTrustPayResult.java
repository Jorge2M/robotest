package com.mng.robotest.test80.mango.test.pageobject.shop.checkout.trustpay;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM.*;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;


public class PageTrustPayResult {
    
    static String XPathHeader = "//h2[@id='stageheader']";
    static String XPathButtonContinue = "//input[@type='submit' and @value='continue']";
    
    public static String getHeaderText(WebDriver driver) {
        WebElement titleNws = driver.findElement(By.xpath(XPathHeader));
        if (titleNws!=null) {
            return driver.findElement(By.xpath(XPathHeader)).getText();
        }
        return "";
    }
    
    public static boolean headerContains(String textContained, WebDriver driver) {
        return (getHeaderText(driver).contains(textContained));
    }
    
    public static boolean isPresentButtonContinue(WebDriver driver) {
    	return (state(Present, By.xpath(XPathButtonContinue), driver).check());
    }

	public static void clickButtonContinue(WebDriver driver) {
		click(By.xpath(XPathButtonContinue), driver).exec();
	}
}
