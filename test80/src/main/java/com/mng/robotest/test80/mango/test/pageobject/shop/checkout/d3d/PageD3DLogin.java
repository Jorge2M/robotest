package com.mng.robotest.test80.mango.test.pageobject.shop.checkout.d3d;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.mng.robotest.test80.mango.test.pageobject.WebdrvWrapp;

@SuppressWarnings("javadoc")
public class PageD3DLogin extends WebdrvWrapp {
    
    static String XPathInputUser = "//input[@id='username']";
    static String XPathInputPassword = "//input[@id='password']";
    static String XPathButtonSubmit = "//input[@class[contains(.,'button')] and @type='submit']";
    
    public static boolean isPageUntil(int maxSecondsToWait, WebDriver driver) {
        return (titleContainsUntil(driver, "3D Authentication", maxSecondsToWait));
    }
    
    public static void inputUserPassword(String user, String password, WebDriver driver) {
        driver.findElement(By.xpath(XPathInputUser)).sendKeys(user);
        driver.findElement(By.xpath(XPathInputPassword)).sendKeys(password);
    }
           
    public static void clickButtonSubmit(WebDriver driver) throws Exception {
        clickAndWaitLoad(driver, By.xpath(XPathButtonSubmit));        
    }
}
