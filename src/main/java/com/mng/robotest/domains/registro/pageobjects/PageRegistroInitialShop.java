package com.mng.robotest.domains.registro.pageobjects;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

import com.github.jorge2m.testmaker.conf.Channel;
import com.mng.robotest.domains.transversal.PageBase;

public class PageRegistroInitialShop extends PageBase {

	private static final String XPATH_MODAL_CONTENT = "//div[@id[contains(.,'registerModal')]]";
	private static final String XPATH_INPUT_EMAIL = XPATH_MODAL_CONTENT + "//input[@id='email']";
	private static final String XPATH_INPUT_PASSWORD = XPATH_MODAL_CONTENT + "//input[@id='password']";
	
	private static final String XPATH_INPUT_BIRTHDATE = "//input[@id='birthdate']";
	
	private static final String XPATH_INPUT_MOVIL = XPATH_MODAL_CONTENT + "//input[@id='mobile-number']";
	private static final String XPATH_RADIO_GIVE_PROMOTIONS = XPATH_MODAL_CONTENT + "//input[@id='newsletter']";
	private static final String XPATH_LINK_GIVE_PROMOTIONS = XPATH_RADIO_GIVE_PROMOTIONS + "/..//*[@data-testid='mng-link']";	
	private static final String XPATH_RADIO_CONSENT_PERSONAL_INFORMATION = "//input[@id='createAccountLegal']";
	private static final String XPATH_LINK_CONSENT_PERSONAL_INFORMATION = XPATH_RADIO_CONSENT_PERSONAL_INFORMATION + "/..//*[@data-testid='mng-link']";
	private static final String XPATH_PERSONAL_INFORMATION_INFO = "//div[@id='createAccountLegal_description']";	

	private static final String XPATH_CREATE_ACCOUNT_BUTTON = XPATH_MODAL_CONTENT + "//div[@class='mng-form-buttons']/button[@type='submit']";	
	
	private static final String XPATH_LINK_POLITICA_PRIVACIDAD = XPATH_MODAL_CONTENT + "//*[@data-testid='registry.consentgdprMobile.container']//*[@data-testid='mng-link']";
	private static final String XPATH_MODAL_POLITICA_PRIVACIDAD_DESKTOP = "//div[@aria-labelledby[contains(.,'gdprLayer')]]";
	private static final String XPATH_MODAL_POLITICA_PRIVACIDAD_MOBILE = "//*[@data-testid='sheet.draggable.dialog']";
	private static final String XPATH_LINK_POLITICA_PRIVACIDAD_MODAL = "//*[@data-testid='mng-link']";
	private static final String XPATH_CLOSE_MODAL_DESKTOP = "//*[@data-testid='modal.close.button']";
	private static final String XPATH_CLOSE_MODAL_DEVICE = "//*[@data-testid='sheet.overlay']";	
	
	private String getXPathModalPoliticaPrivacidad() {
		if (channel==Channel.desktop) {
			return XPATH_MODAL_POLITICA_PRIVACIDAD_DESKTOP;
		}
		return XPATH_MODAL_POLITICA_PRIVACIDAD_MOBILE;
	}
	private String getXPathLinkPoliticaPrivacidadModal() {
		return getXPathModalPoliticaPrivacidad() + XPATH_LINK_POLITICA_PRIVACIDAD_MODAL;
	}
	
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
	
	public void inputBirthDate(String birthdate) {
		getElement(XPATH_INPUT_BIRTHDATE).sendKeys(birthdate);
	}
	
	public void clickRadioGivePromotions() {
		click(XPATH_RADIO_GIVE_PROMOTIONS).exec();
	}
	public void clickLinkGivePromotions() {
		click(XPATH_LINK_GIVE_PROMOTIONS).exec();
	}
	public void clickConsentPersonalInformationRadio() {
		click(XPATH_RADIO_CONSENT_PERSONAL_INFORMATION).exec();
	}	
	public void clickConsentPersonalInformationLink() {
		click(XPATH_LINK_CONSENT_PERSONAL_INFORMATION).exec();
	}
	public boolean checkPersonalInformationInfoVisible() {
		return state(Visible, XPATH_PERSONAL_INFORMATION_INFO).check();
	}
	
	public void clickCreateAccountButton() {
		click(XPATH_CREATE_ACCOUNT_BUTTON).exec();
	}
	
	public void clickPoliticaPrivacidad() {
		click(XPATH_LINK_POLITICA_PRIVACIDAD).exec();
	}
	public boolean isModalPoliticaPrivacidadVisible() {
		return state(Visible, getXPathModalPoliticaPrivacidad()).check();
	}
	public boolean isModalPoliticaPrivacidadInvisible(int seconds) {
		return state(Invisible, getXPathModalPoliticaPrivacidad()).wait(seconds).check();
	}	
	public void clickPoliticaPrivacidadModal() {
		click(getXPathLinkPoliticaPrivacidadModal()).exec();
	}
	public void closeModalPoliticaPrivacidad() {
		if (channel.isDevice()) {
			click(XPATH_CLOSE_MODAL_DEVICE).exec();
		} else {
			click(XPATH_CLOSE_MODAL_DESKTOP).exec();
		}
	}
}
