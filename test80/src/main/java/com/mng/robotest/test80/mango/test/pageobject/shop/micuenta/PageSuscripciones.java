package com.mng.robotest.test80.mango.test.pageobject.shop.micuenta;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM.*;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;


public class PageSuscripciones {

	//Los valores que permiten identificar los radios correspondientes a los newsletter seleccionables
	public enum idNewsletters { she, he, kids, teen }
	
	static String XPathButtonGuardarCambios = "//input[@type='submit' and @value[contains(.,'Guardar')]]";
	static String XPathPageResOK = "//*[text()[contains(.,'Tus preferencias han sido actualizadas')]]";
	static String XPathCheckboxNewsletter = "//form/div[@class='checkboxContent'][1]//div[@class='multipleCheckbox']";
	
	public static String getXPath_newsletterDesmarcadas() {
		return (getXPath_newsletterDesmarcadas(""));
	}
	
	public static String getXPATH_radioNewsletterClickable(idNewsletters idRadio) {
		return ("//input[@data-component-id='SUBS_" + idRadio.toString() + "']/..");
	}
	
	public static String getXPath_newsletterDesmarcadas(String linea) {
		return (XPathCheckboxNewsletter + "//input[not(@checked) and @data-component-id[contains(.,'_" + linea + "')]]");
	}
	
	public static boolean isPage(WebDriver driver) {
		return (state(Visible, By.xpath("//div[@class[contains(.,'Subscriptions')]]"), driver).check());
	}
	
	public static void clickRadioNewsletter(WebDriver driver, idNewsletters idRadio) {
		driver.findElement(By.xpath(getXPATH_radioNewsletterClickable(idRadio))).click();
	}
	
	public static void clickGuardarCambios(WebDriver driver) {
		click(By.xpath(XPathButtonGuardarCambios), driver).exec();
	}
	
	public static boolean isPageResOKUntil(int maxSeconds, WebDriver driver) { 
		return (state(Present, By.xpath(XPathPageResOK), driver).wait(maxSeconds).check());
	}
	
	public static int getNumNewsletters(WebDriver driver) {
		return (driver.findElements(By.xpath(XPathCheckboxNewsletter)).size());
	}
	
	public static int getNumNewslettersDesmarcadas(WebDriver driver) {
		String xpathLineas = getXPath_newsletterDesmarcadas();
		return (driver.findElements(By.xpath(xpathLineas)).size());
	}
	
	public static boolean isNewsletterDesmarcada(String linea, WebDriver driver) {
		String xpathLinDesmarcada = getXPath_newsletterDesmarcadas(linea);
		return (state(Present, By.xpath(xpathLinDesmarcada), driver).check());
	}
}
