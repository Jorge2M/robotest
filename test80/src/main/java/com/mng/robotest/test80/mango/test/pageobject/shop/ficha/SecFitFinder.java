package com.mng.robotest.test80.mango.test.pageobject.shop.ficha;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.mng.robotest.test80.mango.test.pageobject.WebdrvWrapp;


public class SecFitFinder extends WebdrvWrapp {
    
    static String XPathWrapper = "//div[@id='uclw_wrapper']";
    static String XPathInputAltura = XPathWrapper + "//div[@data-ref='height']//input";
    static String XPathInputPeso = XPathWrapper + "//div[@data-ref='weight']//input";
    static String XPathAspaForClose = XPathWrapper + "//div[@data-ref='close']";
    
    public static boolean isVisibleUntil(int maxSecondsToWait, WebDriver driver) {
        return isElementVisibleUntil(driver, By.xpath(XPathInputAltura), maxSecondsToWait);
    }
    
    public static boolean isInvisibileUntil(int maxSecondsToWait, WebDriver driver) {
        return isElementInvisibleUntil(driver, By.xpath(XPathWrapper), maxSecondsToWait);
    }
    
    public static boolean isVisibleInputAltura(WebDriver driver) {
        return isElementVisible(driver, By.xpath(XPathInputAltura));
    }
        
    public static boolean isVisibleInputPeso(WebDriver driver) {
        return isElementVisible(driver, By.xpath(XPathInputPeso));
    }
    
    public static boolean clickAspaForCloseAndWait(WebDriver driver) {
    	driver.findElement(By.xpath(XPathAspaForClose)).click();
    	return (isInvisibileUntil(1/*maxSecondsToWait*/, driver));
    }
}
