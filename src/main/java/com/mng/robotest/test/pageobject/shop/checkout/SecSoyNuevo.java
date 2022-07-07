package com.mng.robotest.test.pageobject.shop.checkout;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.github.jorge2m.testmaker.conf.Channel;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.SeleniumUtils;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.TypeClick;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM.*;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

public class SecSoyNuevo {
	
	public enum ActionNewsL {activate, deactivate}

	private static final String XPATH_FORM_IDENT = "//div[@class='register' or @id='registerCheckOut']//form"; //desktop y mobil
	private static final String XPATH_INPUT_EMAIL = XPATH_FORM_IDENT + "//input[@id[contains(.,'expMail')]]";
	private static final String XPATH_INPUT_CONTENT = XPATH_FORM_IDENT + "//span[@class='eac-cval']";
	private static final String XPATH_BOTON_CONTINUE_MOBIL = "//div[@id='registerCheckOut']//div[@class='submit']/a";
	private static final String XPATH_BOTON_CONTINUE_DESKTOP = "//div[@class='register']//div[@class='submit']/input";
	private static final String XPATH_LINK_POLITICA_PRIVACIDAD = "//span[@data-testid='mng-link']";
	//private static String XPathTextRGPD = "//p[@class='gdpr-text gdpr-profiling']";
	//private static String XPathLegalRGPD = "//p[@class='gdpr-text gdpr-data-protection']";
	
	public static String getXPath_checkPubliNewsletter(Channel channel, boolean active) {
		String sufix = "";
		if (channel.isDevice()) {
			if (active) {
				sufix = " on";
			}
			return ("//div[@class[contains(.,'subscribe__checkbox" + sufix + "')]]");
		}
		
		if (active) {
			sufix = "active";
		}
		return ("//div[@id='publicidad']//div[@class[contains(.,'checkbox__image')] and @class[contains(.,'" + sufix + "')]]");
	}
	
	public static String getXPath_BotonContinue(Channel channel) {
		if (channel==Channel.mobile) {
			return XPATH_BOTON_CONTINUE_MOBIL;
		}
		return XPATH_BOTON_CONTINUE_DESKTOP;
	}

	public static boolean isFormIdentUntil(WebDriver driver, int maxSeconds) { 
		return (state(Present, By.xpath(XPATH_FORM_IDENT), driver).wait(maxSeconds).check());
	}

	public static boolean isCheckedPubliNewsletter(WebDriver driver, Channel channel) {
		String xpathCheckActive = getXPath_checkPubliNewsletter(channel, true/*active*/);
		return (state(Present, By.xpath(xpathCheckActive), driver).check());
	}

	/**
	 * Marca/desmarca el check de la publicidad (Newsletter)
	 */
	public static void setCheckPubliNewsletter(WebDriver driver, ActionNewsL action, Channel channel) {
		boolean isActivated = isCheckedPubliNewsletter(driver, channel);
		switch (action) {
		case activate:
			if (!isActivated) {
				String xpathCheck = getXPath_checkPubliNewsletter(channel, false/*active*/);
				driver.findElement(By.xpath(xpathCheck)).click();
			}
			break;
		case deactivate:
			if (isActivated) {
				String xpathCheck = getXPath_checkPubliNewsletter(channel, true/*active*/);
				driver.findElement(By.xpath(xpathCheck)).click();
			}
			break;
		default:
			break;
		}
	}

	public static void inputEmail(String email, Channel channel, WebDriver driver) {
		inputEmailOneTime(email, driver);
		if (!isInputWithText(email, channel, driver)) {
			inputEmailOneTime(email, driver);
		}
	}
	private static boolean isInputWithText(String text, Channel channel, WebDriver driver) {
		if (channel==Channel.desktop) {
			return (driver.findElement(By.xpath(XPATH_INPUT_CONTENT)).getAttribute("innerHTML").compareTo(text)==0);
		} else {
			return (driver.findElement(By.xpath(XPATH_INPUT_EMAIL)).getAttribute("value").compareTo(text)==0);
		}
	}

	private static void inputEmailOneTime(String email, WebDriver driver) {
		WebElement input = driver.findElement(By.xpath(XPATH_INPUT_EMAIL));
		input.clear();
		input.sendKeys(email);
		SeleniumUtils.waitMillis(500);
		//sendKeysWithRetry(email, By.xpath(XPathInputEmail), 3, driver);
	}

	public static void clickContinue(Channel channel, WebDriver driver) {
		String xpathButton = getXPath_BotonContinue(channel);
		click(By.xpath(xpathButton), driver).type(TypeClick.javascript).exec();
	}

	public static boolean isLinkPoliticaPrivacidad(int maxSeconds, WebDriver driver) {
		return (state(Visible, By.xpath(XPATH_LINK_POLITICA_PRIVACIDAD), driver).wait(maxSeconds).check());
	}
	
//	public static boolean isTextoRGPDVisible(int maxSeconds, WebDriver driver) {
//		return (state(Visible, By.xpath(XPathTextRGPD), driver).wait(maxSeconds).check());
//	}
//
//	public static boolean isTextoLegalRGPDVisible(WebDriver driver) {
//		return (state(Visible, By.xpath(XPathLegalRGPD), driver).check());
//	}
}
