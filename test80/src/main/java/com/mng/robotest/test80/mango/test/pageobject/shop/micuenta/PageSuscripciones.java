package com.mng.robotest.test80.mango.test.pageobject.shop.micuenta;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;


public class PageSuscripciones extends PageObjTM {

	//Los valores que permiten identificar los radios correspondientes a los newsletter seleccionables
	public enum NewsLetter { she, he, kids, teen }
	
	private final static String XPathButtonGuardarCambios = "//input[@type='submit' and @value[contains(.,'Guardar')]]";
	private final static String XPathPageResOK = "//*[text()[contains(.,'Preferencias actualizadas!')]]";
	private final static String XPathCheckboxNewsletter = "//form/div[@class='checkboxContent'][1]//div[@class='multipleCheckbox']";
	
	public PageSuscripciones(WebDriver driver) {
		super(driver);
	}
	
	private String getXPath_newsletterDesmarcadas() {
		return (getXPath_newsletterDesmarcadas(""));
	}
	
	private String getXPATH_radioNewsletterClickable(NewsLetter idRadio) {
		return ("//input[@data-component-id='SUBS_" + idRadio.toString() + "']/..");
	}
	
	private String getXPath_newsletterDesmarcadas(String linea) {
		return (XPathCheckboxNewsletter + "//input[not(@checked) and @data-component-id[contains(.,'_" + linea + "')]]");
	}
	
	public boolean isPage() {
		return (state(Visible, By.xpath("//div[@class[contains(.,'Subscriptions')]]"), driver).check());
	}
	
	private boolean isRadioNewsletterSelected(NewsLetter idRadio) {
		By byRadio = By.xpath(getXPATH_radioNewsletterClickable(idRadio));
		return driver.findElement(byRadio).getAttribute("class").contains("active");
	}
	
	public void selectRadioNewsletter(NewsLetter idRadio) {
		if (!isRadioNewsletterSelected(idRadio)) {
			driver.findElement(By.xpath(getXPATH_radioNewsletterClickable(idRadio))).click();
		}
	}
	
	public void clickGuardarCambios() {
		click(By.xpath(XPathButtonGuardarCambios), driver).exec();
	}
	
	public boolean isPageResOKUntil(int maxSeconds) { 
		return (state(Present, By.xpath(XPathPageResOK), driver).wait(maxSeconds).check());
	}
	
	public int getNumNewsletters() {
		return (driver.findElements(By.xpath(XPathCheckboxNewsletter)).size());
	}
	
	public int getNumNewslettersDesmarcadas() {
		String xpathLineas = getXPath_newsletterDesmarcadas();
		return (driver.findElements(By.xpath(xpathLineas)).size());
	}
	
	public boolean isNewsletterDesmarcada(String linea) {
		String xpathLinDesmarcada = getXPath_newsletterDesmarcadas(linea);
		return (state(Present, By.xpath(xpathLinDesmarcada), driver).check());
	}
}
