package com.mng.robotest.test80.mango.test.pageobject.shop.checkout.koreancreditcard;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static com.mng.testmaker.service.webdriver.pageobject.PageObjTM.*;
import static com.mng.testmaker.service.webdriver.pageobject.StateElement.State.*;

public class PageKoreanConfDesktop {
	
    private static String XPathSubmitButton = "//input[@id='pay_btn']";

	public static boolean isPage(WebDriver driver) {
		return (state(Visible, By.xpath(XPathSubmitButton), driver).check());
	}
	
	public static void clickButtonSubmit(WebDriver driver) throws Exception {
		clickAndWaitLoad(driver, By.xpath(XPathSubmitButton));
	}
}
