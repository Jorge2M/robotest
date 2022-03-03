package com.mng.robotest.test.pageobject.shop.checkout.paysecureqiwi;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM.*;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;


public class PageQiwiInputTlfn {

	private final static String XPathInputPhone = "//input[@id='QIWIMobilePhone']";
	private final static String XPathLinkAceptar = "//input[@name='Submit_Card_1' and not(@disabled)]";
	
	public static void inputQiwiPhone(WebDriver driver, String phone) {
		driver.findElement(By.xpath(XPathInputPhone)).sendKeys(phone);
	}
	
	public static boolean isPresentInputPhone(WebDriver driver) {
		return (state(Present, By.xpath(XPathInputPhone), driver).check());
	}	
	
	public static boolean isVisibleLinkAceptar(int maxSeconds, WebDriver driver) {
		return (state(Visible, By.xpath(XPathLinkAceptar), driver)
				.wait(maxSeconds).check());
	}
	
	public static void clickLinkAceptar(WebDriver driver) {
		click(By.xpath(XPathLinkAceptar), driver).exec();
	}
}