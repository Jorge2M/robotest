package com.mng.robotest.test80.mango.test.pageobject.shop.checkout.paypal;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM.*;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;


public class PagePaypalLogin {
    
	private final static String XPathContainer = "//div[@class[contains(.,'contentContainer')]]";
    private final static String XPathInputLogin = XPathContainer + "//input[@id='email']";
    private final static String XPathInputPassword = XPathContainer + "//input[@id='password' and not(@disabled='disabled')]";
    private final static String XPathIniciarSesionButton = XPathContainer + "//div/button[@id='btnLogin' or @id='login']";
    
    public static boolean isPageUntil(int maxSeconds, WebDriver driver) {
    	return (state(Present, By.xpath(XPathInputPassword), driver)
    			.wait(maxSeconds).check());
    }
    
    public static void inputUserAndPassword(String userMail, String password, WebDriver driver) {
    	waitForPageLoaded(driver); //For avoid StaleElementReferenceException
    	sendKeysWithRetry(userMail, By.xpath(XPathInputLogin), 2, driver);
    	if (state(Visible, By.xpath(XPathInputPassword), driver).check()) {
            driver.findElement(By.xpath(XPathInputPassword)).sendKeys(password);
        } else {
            PagePaypalLogin.clickIniciarSesion(driver);
            if (state(Visible, By.xpath(XPathInputPassword), driver).wait(3).check()) {
                driver.findElement(By.xpath(XPathInputPassword)).sendKeys(password);
            }
        }
    }

	public static void clickIniciarSesion(WebDriver driver) {
		click(By.xpath(XPathIniciarSesionButton), driver).exec();
    }
}
