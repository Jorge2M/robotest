package com.mng.robotest.test80.mango.test.pageobject.shop.checkout.paypal;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.mng.robotest.test80.mango.test.pageobject.WebdrvWrapp;


public class PagePaypalLogin extends WebdrvWrapp {
    
	private final static String XPathContainer = "//div[@class[contains(.,'contentContainer')]]";
    private final static String XPathInputLogin = XPathContainer + "//input[@id='email']";
    private final static String XPathInputPassword = XPathContainer + "//input[@id='password' and not(@disabled='disabled')]";
    private final static String XPathIniciarSesionButton = XPathContainer + "//div/button[@id='btnLogin' or @id='login']";
    
    public static boolean isPageUntil(int maxSecondsToWait, WebDriver driver) {
        return (isElementPresentUntil(driver, By.xpath(XPathInputPassword), maxSecondsToWait));
    }
    
    public static void inputUserAndPassword(String userMail, String password, WebDriver driver) throws Exception {
    	waitForPageLoaded(driver); //For avoid StaleElementReferenceException
    	sendKeysWithRetry(2, userMail, By.xpath(XPathInputLogin), driver);
        if (isElementVisible(driver, By.xpath(XPathInputPassword))) {
            driver.findElement(By.xpath(XPathInputPassword)).sendKeys(password);
        }
        else {
            PagePaypalLogin.clickIniciarSesion(driver);
            if (isElementVisibleUntil(driver, By.xpath(XPathInputPassword), 3/*seconds*/))
                driver.findElement(By.xpath(XPathInputPassword)).sendKeys(password);
        }
    }

    public static void clickIniciarSesion(WebDriver driver) throws Exception {
    	clickAndWaitLoad(driver, By.xpath(XPathIniciarSesionButton));
    }
}
