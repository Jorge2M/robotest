package com.mng.robotest.domains.registro.pageobjects;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

import com.mng.robotest.domains.transversal.PageBase;

public class PageRegistroInitialShop extends PageBase {

	private static final String XPATH_MODAL_CONTENT = "//div[@id[contains(.,'registerModal')]]";
	private static final String XPATH_INPUT_EMAIL = XPATH_MODAL_CONTENT + "//input[@id='email']";
	private static final String XPATH_INPUT_PASSWORD = XPATH_MODAL_CONTENT + "//input[@id='password']";
	private static final String XPATH_INPUT_MOVIL = XPATH_MODAL_CONTENT + "//input[@id='mobile-number']";
	private static final String XPATH_RADIO_GIVE_PROMOTIONS = XPATH_MODAL_CONTENT + "//input[@id='newsletter']";
	private static final String XPATH_CREATE_ACCOUNT_BUTTON = XPATH_MODAL_CONTENT + "//div[@class='mng-form-buttons']/button[@type='submit']";
	
	public boolean isPage() {
		return isPageUntil(0);
	}
	public boolean isPageUntil(int seconds) {
		return state(Present, XPATH_INPUT_EMAIL).wait(seconds).check();
	}
	
	public void inputEmail(String email) {
		getElement(XPATH_INPUT_EMAIL).sendKeys(email);
	}
	
	public void inputPassword(String password) {
		getElement(XPATH_INPUT_PASSWORD).sendKeys(password);
	}

	public void inputMovil(String number) {
		getElement(XPATH_INPUT_MOVIL).sendKeys(number);
	}	
	
	public void clickRadioGivePromotions() {
		click(XPATH_RADIO_GIVE_PROMOTIONS).exec();
	}
	
	public void clickCreateAccountButton() {
		click(XPATH_CREATE_ACCOUNT_BUTTON).exec();
	}
}
