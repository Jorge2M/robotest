package com.mng.robotest.test80.mango.test.pageobject.shop.registro;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.mng.robotest.test80.mango.test.pageobject.TypeOfClick;
import com.mng.robotest.test80.mango.test.pageobject.WebdrvWrapp;


public class PageRegistroFin extends WebdrvWrapp {

    private static final String xpathButtonIrShopping = "//input[@type='submit' and @value='Ir de shopping']";
 
    public static boolean isPageUntil(int maxSecondsToWait, WebDriver driver) {
        return (isElementPresentUntil(driver, By.xpath(xpathButtonIrShopping), maxSecondsToWait));
    }
    
    public static void clickIrDeShopping(WebDriver driver) throws Exception {
        clickAndWaitLoad(driver, By.xpath(xpathButtonIrShopping), TypeOfClick.javascript);
        
        //Existe un problema en Firefox-Gecko con este botón: a veces el 1er click no funciona así que ejecutamos un 2o 
        if (isVisibleButtonIrDeShopping(driver))
        	clickAndWaitLoad(driver, By.xpath(xpathButtonIrShopping), TypeOfClick.javascript);
    }
    
    public static boolean isVisibleButtonIrDeShopping(WebDriver driver) {
    	return (isElementVisible(driver, By.xpath(xpathButtonIrShopping)));
    }
}
