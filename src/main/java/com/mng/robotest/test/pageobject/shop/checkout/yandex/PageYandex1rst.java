package com.mng.robotest.test.pageobject.shop.checkout.yandex;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM.*;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;


public class PageYandex1rst {

	static String XPathInputEmail = "//input[@name='cps_email']";
	static String XPathButtonContinue = "//div[@class[contains(.,'payment-submit')]]//button";
	static String XPathInputTelefono = "//input[@name[contains(.,'phone')]]";
	static String XPathRetryButton = "//span[@class='button__text' and text()[contains(.,'Повторить попытку')]]";

	public static boolean isPage(WebDriver driver) {
		return (driver.getTitle().toLowerCase().contains("yandex"));
	}

	//Revisar
	public static boolean isValueEmail(String emailUsr, WebDriver driver) {
		String valueEmail = getValueInputEmail(driver);
		return (valueEmail.contains(emailUsr));
	}
	
	public static String getValueInputEmail(WebDriver driver) {
		return (driver.findElement(By.xpath(XPathInputEmail)).getAttribute("value"));
	}

	public static void clickContinue(WebDriver driver) {
		click(By.xpath(XPathButtonContinue), driver).exec();
	}

	public static void inputTelefono(String telefono, WebDriver driver) {
		driver.findElement(By.xpath(XPathInputTelefono)).clear();
		sendKeysWithRetry(telefono, By.xpath(XPathInputTelefono), 2, driver);
	}

	public static boolean retryButtonExists(WebDriver driver) {
		return (state(Present, By.xpath(XPathRetryButton), driver).check());
	}

	public static void clickOnRetry(WebDriver driver) {
		click(By.xpath(XPathRetryButton), driver).waitLoadPage(2).exec();
	}
}
