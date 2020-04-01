package com.mng.robotest.test80.mango.test.pageobject.shop.registro;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static com.mng.testmaker.service.webdriver.pageobject.TypeClick.*;
import static com.mng.testmaker.service.webdriver.pageobject.PageObjTM.*;
import static com.mng.testmaker.service.webdriver.pageobject.StateElement.State.*;


public class PageRegistroFin {

    private static final String xpathButtonIrShopping = "//div[@class[contains(.,'ir-de-shopping')]]/input[@type='submit']";
 
    public static boolean isPageUntil(int maxSeconds, WebDriver driver) {
    	return (state(Present, By.xpath(xpathButtonIrShopping), driver)
    			.wait(maxSeconds).check());
    }
    
    public static void clickIrDeShopping(WebDriver driver) {
    	waitForPageLoaded(driver); //Para evitar StaleElement Exception
    	click(By.xpath(xpathButtonIrShopping), driver).type(javascript).exec();
        if (isVisibleButtonIrDeShopping(driver)) {
        	click(By.xpath(xpathButtonIrShopping), driver).exec();
        }
    }
    
    public static boolean isVisibleButtonIrDeShopping(WebDriver driver) {
    	return (state(Visible, By.xpath(xpathButtonIrShopping), driver).check());
    }
}
