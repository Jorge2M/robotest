package com.mng.robotest.tests.domains.micuenta.pageobjects;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

import com.mng.robotest.tests.domains.base.PageBase;


public class PageSuscripciones extends PageBase {

	public enum NewsLetter { she, he, kids, teen }
	
	private static final String XP_BUTTON_GUARDAR_CAMBIOS = "//input[@type='submit' and @value[contains(.,'Guardar')]]";
	private static final String XP_PAGE_RES_OK = "//*[text()[contains(.,'Preferencias actualizadas!')]]";
	private static final String XP_CHECKBOX_NEWSLETTER = "//form/div[@class='checkboxContent'][1]//div[@class='multipleCheckbox']";
	
	private String getXPathNewsletterDesmarcadas() {
		return getXPathNewsletterDesmarcadas("");
	}
	
	private String getXPathRadioNewsletterClickable(NewsLetter idRadio) {
		return ("//input[@data-component-id='SUBS_" + idRadio.toString() + "']/..");
	}
	
	private String getXPathNewsletterDesmarcadas(String linea) {
		return (XP_CHECKBOX_NEWSLETTER + 
				"//input[not(@checked) and @data-component-id[contains(.,'_" + linea.toLowerCase() + "')]]");
	}
	private String getXPathNewsletterMarcadas(String linea) {
		return (XP_CHECKBOX_NEWSLETTER + 
				"//input[@checked and @data-component-id[contains(.,'_" + linea.toLowerCase() + "')]]");
	}	
	
	public boolean isPage() {
		return state(VISIBLE, "//div[@class[contains(.,'Subscriptions')]]").check();
	}
	
	private boolean isRadioNewsletterSelected(NewsLetter idRadio) {
		String xpathRadio = getXPathRadioNewsletterClickable(idRadio);
		return getElement(xpathRadio).getAttribute("class").contains("active");
	}
	
	public void selectRadioNewsletter(NewsLetter idRadio) {
		if (!isRadioNewsletterSelected(idRadio)) {
			getElement(getXPathRadioNewsletterClickable(idRadio)).click();
		}
	}
	
	public void clickGuardarCambios() {
		click(XP_BUTTON_GUARDAR_CAMBIOS).exec();
	}
	
	public boolean isPageResOKUntil(int seconds) { 
		return state(PRESENT, XP_PAGE_RES_OK).wait(seconds).check();
	}
	
	public int getNumNewsletters() {
		return getElements(XP_CHECKBOX_NEWSLETTER).size();
	}
	
	public int getNumNewslettersDesmarcadas() {
		String xpathLineas = getXPathNewsletterDesmarcadas();
		return getElements(xpathLineas).size();
	}
	
	public boolean isNewsletterDesmarcada(String linea) {
		String xpathLinDesmarcada = getXPathNewsletterDesmarcadas(linea);
		return state(PRESENT, xpathLinDesmarcada).check();
	}
	public boolean isNewsletterMarcada(String linea) {
		String xpathLinMarcada = getXPathNewsletterMarcadas(linea);
		return state(PRESENT, xpathLinMarcada).check();
	}	
}
