package com.mng.robotest.test.pageobject.shop.checkout.amazon;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM.*;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;


public class PageAmazonIdent {
	
	private static final String XPATH_IMG_LOGO_AMAZON = "//div/img[@src[contains(.,'logo_payments')]]";
	private static final String XPATH_INPUT_EMAIL = "//input[@id='ap_email']";
	private static final String XPATH_INPUT_PASSWORD = "//input[@id='ap_password']";
	
	public static boolean isLogoAmazon(WebDriver driver) { 
		if (state(Visible, By.xpath(XPATH_IMG_LOGO_AMAZON), driver).check()) {
			return true;
		}
		return false;
	}
	
	public static boolean isPageIdent(WebDriver driver) {
		if (state(Visible, By.xpath(XPATH_INPUT_EMAIL), driver).check() &&
			state(Visible, By.xpath(XPATH_INPUT_PASSWORD), driver).check()) {
			return true;
		}
		return false;
	}
}
