package com.mng.robotest.test.pageobject.shop.checkout.sepa;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.conf.Channel;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM.*;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;


public class PageSepa1rst {
	
	static String XPathListOfPayments = "//ul[@id='paymentMethods']";
	static String XPathCabeceraStep = "//h2[@id[contains(.,'stageheader')]]";
	static String XPathButtonPagoDesktop = "//input[@class[contains(.,'paySubmit')] and @type='submit']";
	static String XPathButtonContinueMobil = "//input[@type='submit' and @id='mainSubmit']";
	static String XPathInputTitular = "//input[@id[contains(.,'ownerName')]]";
	static String XPathInputCuenta = "//input[@id[contains(.,'bankAccountNumber')]]";
	static String XPathRadioAcepto = "//input[@id[contains(.,'acceptDirectDebit')]]";
	static String XPathIconoSepaMobil = XPathListOfPayments + "//input[@class[contains(.,'sepa')]]"; 
	static String XPathIconoSepaDesktop = XPathListOfPayments + "/li[@data-variant[contains(.,'sepa')]]";
	
	public static String getXPathIconoSepa(Channel channel) {
		if (channel.isDevice()) {
			return XPathIconoSepaMobil;
		}
		return XPathIconoSepaDesktop;
	}
	
	public static boolean isPresentIconoSepa(Channel channel, WebDriver driver) {
		String xpathPago = getXPathIconoSepa(channel);
		return (state(Present, By.xpath(xpathPago), driver).check());
	}

	public static void clickIconoSepa(Channel channel, WebDriver driver) {
		String xpathPago = getXPathIconoSepa(channel);
		click(By.xpath(xpathPago), driver).exec();
	}

	public static boolean isPresentCabeceraStep(WebDriver driver) {
		return (state(Present, By.xpath(XPathCabeceraStep), driver).check());
	}
	
	public static boolean isPresentButtonPagoDesktop(WebDriver driver) {
		return (state(Present, By.xpath(XPathButtonPagoDesktop), driver).check());
	}

	public static void clickButtonContinuePago(Channel channel, WebDriver driver) {
		if (channel.isDevice()) {
			click(By.xpath(XPathButtonContinueMobil), driver).exec();
		} else {
			click(By.xpath(XPathButtonPagoDesktop), driver).exec();
		}
	}
	
	public static boolean isPresentInputTitular(WebDriver driver) { 
		return (state(Present, By.xpath(XPathInputTitular), driver).check());
	}

	public static boolean isPresentInputCuenta(WebDriver driver) { 
		return (state(Present, By.xpath(XPathInputCuenta), driver).check());
	}
	
	public static void inputTitular(String titular, WebDriver driver) {
		driver.findElement(By.xpath(XPathInputTitular)).sendKeys(titular);
	}
	
	public static void inputCuenta(String cuenta, WebDriver driver) {
		driver.findElement(By.xpath(XPathInputCuenta)).sendKeys(cuenta);
	}
	
	public static void clickAcepto(WebDriver driver) {
		driver.findElement(By.xpath(XPathRadioAcepto)).click();
	}
}
