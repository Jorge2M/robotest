package com.mng.robotest.test80.mango.test.pageobject.shop;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM.*;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;


public class PageIdentAmazon {

	static String XPathImgLogoAmazon = "//div/img[@src[contains(.,'logo_payments')]]";
	static String XPathInputEmail = "//input[@id='ap_email']";
	static String XPathInputPassword = "//input[@id='ap_password']";

	/**
	 * @return indicador de si realmente estamos en la página de identificación de Amazon (comprobamos si aparece el logo de Amazon)
	 */
	public static boolean isLogoAmazon(WebDriver driver) { 
		return (state(Visible, By.xpath(XPathImgLogoAmazon), driver).check());
	}

	public static boolean isPageIdent(WebDriver driver) {
		return (
			state(Visible, By.xpath(XPathInputEmail), driver).check() &&
			state(Visible, By.xpath(XPathInputPassword), driver).check());
	}
}
