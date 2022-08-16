package com.mng.robotest.test.pageobject.shop.micuenta;

import com.mng.robotest.domains.transversal.PageBase;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;


public class PageSuscripciones extends PageBase {

	public enum NewsLetter { she, he, kids, teen }
	
	private static final String XPATH_BUTTON_GUARDAR_CAMBIOS = "//input[@type='submit' and @value[contains(.,'Guardar')]]";
	private static final String XPATH_PAGE_RES_OK = "//*[text()[contains(.,'Preferencias actualizadas!')]]";
	private static final String XPATH_CHECKBOX_NEWSLETTER = "//form/div[@class='checkboxContent'][1]//div[@class='multipleCheckbox']";
	
	private String getXPathNewsletterDesmarcadas() {
		return (getXPathNewsletterDesmarcadas(""));
	}
	
	private String getXPathRadioNewsletterClickable(NewsLetter idRadio) {
		return ("//input[@data-component-id='SUBS_" + idRadio.toString() + "']/..");
	}
	
	private String getXPathNewsletterDesmarcadas(String linea) {
		return (XPATH_CHECKBOX_NEWSLETTER + "//input[not(@checked) and @data-component-id[contains(.,'_" + linea + "')]]");
	}
	private String getXPathNewsletterMarcadas(String linea) {
		return (XPATH_CHECKBOX_NEWSLETTER + "//input[@checked and @data-component-id[contains(.,'_" + linea + "')]]");
	}	
	
	public boolean isPage() {
		return state(Visible, "//div[@class[contains(.,'Subscriptions')]]").check();
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
		click(XPATH_BUTTON_GUARDAR_CAMBIOS).exec();
	}
	
	public boolean isPageResOKUntil(int maxSeconds) { 
		return state(Present, XPATH_PAGE_RES_OK).wait(maxSeconds).check();
	}
	
	public int getNumNewsletters() {
		return getElements(XPATH_CHECKBOX_NEWSLETTER).size();
	}
	
	public int getNumNewslettersDesmarcadas() {
		String xpathLineas = getXPathNewsletterDesmarcadas();
		return getElements(xpathLineas).size();
	}
	
	public boolean isNewsletterDesmarcada(String linea) {
		String xpathLinDesmarcada = getXPathNewsletterDesmarcadas(linea);
		return state(Present, xpathLinDesmarcada).check();
	}
	public boolean isNewsletterMarcada(String linea) {
		String xpathLinMarcada = getXPathNewsletterMarcadas(linea);
		return state(Present, xpathLinMarcada).check();
	}	
}
