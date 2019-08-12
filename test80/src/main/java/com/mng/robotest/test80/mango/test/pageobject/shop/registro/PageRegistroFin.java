package com.mng.robotest.test80.mango.test.pageobject.shop.registro;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.mng.robotest.test80.arq.webdriverwrapper.TypeOfClick;
import com.mng.robotest.test80.arq.webdriverwrapper.WebdrvWrapp;


public class PageRegistroFin extends WebdrvWrapp {

    private static final String xpathButtonIrShopping = "//div[@class[contains(.,'ir-de-shopping')]]/input[@type='submit']";
 
    public static boolean isPageUntil(int maxSecondsToWait, WebDriver driver) {
        return (isElementPresentUntil(driver, By.xpath(xpathButtonIrShopping), maxSecondsToWait));
    }
    
    public static void clickIrDeShopping(WebDriver driver) throws Exception {
    	waitForPageLoaded(driver); //Para evitar StaleElement Exception
        clickAndWaitLoad(driver, By.xpath(xpathButtonIrShopping), TypeOfClick.javascript);
        if (isVisibleButtonIrDeShopping(driver)) {
        	clickAndWaitLoad(driver, By.xpath(xpathButtonIrShopping), TypeOfClick.javascript);
        }
    }
    
    public static boolean isVisibleButtonIrDeShopping(WebDriver driver) {
    	return (isElementVisible(driver, By.xpath(xpathButtonIrShopping)));
    }
}
