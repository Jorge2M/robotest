package com.mng.robotest.test80.mango.test.pageobject.shop.checkout.dotpay;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM.*;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;


public class PageDotpayAcceptSimulation {
    
    static String XPathRedButtonAceptar = "//input[@id='submit_success' and @type='submit']";
    static String title = "Dotpay payment simulation";
    
    public static boolean isPage(WebDriver driver) {
        return (driver.getTitle().contains(title));
    }
    
    public static boolean isPresentRedButtonAceptar(WebDriver driver) {
    	return (state(Present, By.xpath(XPathRedButtonAceptar), driver).check());
    }

	public static void clickRedButtonAceptar(WebDriver driver) {
		click(By.xpath(XPathRedButtonAceptar), driver).exec();
	}
}
