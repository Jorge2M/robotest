package com.mng.robotest.test80.mango.test.pageobject.shop.checkout.tmango;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.mng.robotest.test80.mango.test.pageobject.WebdrvWrapp;

@SuppressWarnings("javadoc")
public class PageAmexInputCip extends WebdrvWrapp {

    static String XPathInputCIP = "//input[@name='pin']";
    static String XPathAcceptButton = "//img[@src[contains(.,'daceptar.gif')]]/../../a";
    
    public static boolean isPageUntil(int maxSecondsToWait, WebDriver driver) {
        return isElementPresentUntil(driver, By.xpath(XPathInputCIP), maxSecondsToWait);
    }
    
    public static void inputCIP(String CIP, WebDriver driver) {
        driver.findElement(By.xpath(XPathInputCIP)).sendKeys(CIP);
    }
    
    public static void clickAceptarButton(WebDriver driver) throws Exception {
        clickAndWaitLoad(driver, By.xpath(XPathAcceptButton));
    }
}
