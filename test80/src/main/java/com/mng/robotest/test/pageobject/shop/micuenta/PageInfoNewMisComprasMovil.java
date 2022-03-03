package com.mng.robotest.test.pageobject.shop.micuenta;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.TypeClick.*;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM.*;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;


public class PageInfoNewMisComprasMovil {

	static String XPathButtonToMisCompras = "//div[@class[contains(.,'button')] and @id='goToMyPurchases']";
	
	public static boolean isPage(WebDriver driver) {
		return (state(Visible, By.xpath(XPathButtonToMisCompras), driver).wait(2).check());
	}
	
	public static boolean isVisibleButtonToMisCompras(WebDriver driver) {
		return (state(Visible, By.xpath(XPathButtonToMisCompras), driver).check());
	}
	
	public static void clickButtonToMisCompras(WebDriver driver) {
		click(By.xpath(XPathButtonToMisCompras), driver).exec();
		if (isVisibleButtonToMisCompras(driver)) {
			click(By.xpath(XPathButtonToMisCompras), driver).type(javascript).exec();
		}
	}
}
