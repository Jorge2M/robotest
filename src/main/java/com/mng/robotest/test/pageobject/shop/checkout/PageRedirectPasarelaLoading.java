package com.mng.robotest.test.pageobject.shop.checkout;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM.*;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;


public class PageRedirectPasarelaLoading {

public static String XPathIsPage = "//div[@class[contains(.,'payment-redirect')]]/div[@class='loading' or @class='logo']";

	public static boolean isPageUntil(int maxSeconds, WebDriver driver) {
		return (state(Present, By.xpath(XPathIsPage), driver).wait(maxSeconds).check());
	}

	public static boolean isPageNotVisibleUntil(int maxSeconds, WebDriver driver) {
		return (state(Invisible, By.xpath(XPathIsPage), driver).wait(maxSeconds).check());
	}
}