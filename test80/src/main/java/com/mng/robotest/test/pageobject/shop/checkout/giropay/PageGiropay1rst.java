package com.mng.robotest.test.pageobject.shop.checkout.giropay;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.conf.Channel;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM.*;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;


public class PageGiropay1rst {
	
	static String XPathListOfPayments = "//ul[@id='paymentMethods']";
	static String XPathCabeceraStep = "//h2[@id[contains(.,'stageheader')]]";
	static String XPathButtonPagoDesktop = "//input[@class[contains(.,'paySubmit')] and @type='submit']";
	static String XPathButtonContinueMobil = "//input[@type='submit' and @id='mainSubmit']";
	static String XPathIconoGiropayMobil = XPathListOfPayments + "//input[@class[contains(.,'giropay')]]";
	static String XPathIconoGiropayDesktop = XPathListOfPayments + "/li[@data-variant[contains(.,'giropay')]]";
	
	public static String getXPathIconoGiropay(Channel channel) {
		if (channel.isDevice()) {
			return XPathIconoGiropayMobil;
		}
		return XPathIconoGiropayDesktop;
	}
	
	public static String getXPath_rowListWithBank(String bank) {
		return ("//ul[@id='giropaysuggestionlist']/li[@data-bankname[contains(.,'" + bank + "')]]");
	}
	
	public static boolean isPresentIconoGiropay(Channel channel, WebDriver driver) {
		String xpathPago = getXPathIconoGiropay(channel);
		return (state(Present, By.xpath(xpathPago), driver).check());
	}
	
	public static boolean isPresentCabeceraStep(WebDriver driver) {
		return (state(Present, By.xpath(XPathCabeceraStep), driver).check());
	}
	
	public static boolean isPresentButtonPagoDesktopUntil(int maxSeconds, WebDriver driver) {
		return (state(Present, By.xpath(XPathButtonPagoDesktop), driver)
				.wait(maxSeconds).check());
	}

//	public static boolean isVisibleInputBankUntil(int maxSecondsToWait, WebDriver driver) {
//		return (isElementVisibleUntil(driver, By.xpath(XPathInputBank), maxSecondsToWait));
//	}
//	
//	public static void inputBank(String bank, Channel channel, WebDriver driver) throws Exception {
//		if (channel.isDevice()) {
//			if (!isElementVisible(driver, By.xpath(XPathInputBank))) {
//				clickIconoGiropay(channel, driver);
//			}
//		}
//		
//		driver.findElement(By.xpath(XPathInputBank)).sendKeys(bank);
//		waitForPageLoaded(driver, 1/*waitSeconds*/);
//	}
//	
//	public static void inputTabInBank(WebDriver driver) throws Exception {
//		driver.findElement(By.xpath(XPathInputBank)).sendKeys(Keys.TAB);
//		waitForPageLoaded(driver, 1/*waitSeconds*/);		
//	}

	public static void clickIconoGiropay(Channel channel, WebDriver driver) {
		String xpathPago = getXPathIconoGiropay(channel);
		click(By.xpath(xpathPago), driver).exec();
	}

	public static void clickButtonContinuePay(Channel channel, WebDriver driver) {
		if (channel.isDevice()) {
			clickButtonContinueMobil(driver);
		} else {
			clickButtonPagoDesktop(driver);
		}
	}

	public static void clickButtonPagoDesktop(WebDriver driver) {
		click(By.xpath(XPathButtonPagoDesktop), driver).exec();
	}

	public static void clickButtonContinueMobil(WebDriver driver) {
		click(By.xpath(XPathButtonContinueMobil), driver).exec();
	}
//	
//	public static boolean isVisibleBankInListUntil(String bank, int maxSecondsToWait, WebDriver driver) {
//		String xpathRow = getXPath_rowListWithBank(bank);
//		return (isElementVisibleUntil(driver, By.xpath(xpathRow), maxSecondsToWait));
//	}
//	
//	public static boolean isInvisibleBankInListUntil(String bank, int maxSecondsToWait, WebDriver driver) {
//		String xpathRow = getXPath_rowListWithBank(bank);
//		return (isElementInvisibleUntil(driver, By.xpath(xpathRow), maxSecondsToWait));
//	}	
}
