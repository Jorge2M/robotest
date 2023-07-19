package com.mng.robotest.domains.registro.pageobjects;

import com.mng.robotest.domains.base.PageBase;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;
import static com.mng.robotest.domains.legal.legaltexts.FactoryLegalTexts.PageLegalTexts.*;

public class PageRegistroInitialShop extends PageBase {

	//private static final String XPATH_MODAL_CONTENT = "//div[@id[contains(.,'registerModal')]]";
	private static final String XPATH_MODAL_CONTENT = "//micro-frontend[@id='registry']";
	private static final String XPATH_INPUT_EMAIL = XPATH_MODAL_CONTENT + "//input[@data-testid[contains(.,'emailInput')]]";
	private static final String XPATH_INPUT_PASSWORD = XPATH_MODAL_CONTENT + "//input[@data-testid[contains(.,'passInput')]]";
	private static final String XPATH_INPUT_BIRTHDATE = "//input[@id='birthdate']";
	private static final String XPATH_INPUT_MOVIL = XPATH_MODAL_CONTENT + "//input[@id='mobile-number']";
	private static final String XPATH_RADIO_GIVE_PROMOTIONS = XPATH_MODAL_CONTENT + "//input[@id='newsletter']";
	private static final String XPATH_LINK_GIVE_PROMOTIONS = XPATH_RADIO_GIVE_PROMOTIONS + "/..//*[@data-testid='mng-link']";	
	private static final String XPATH_RADIO_CONSENT_PERSONAL_INFORMATION = "//input[@id='createAccountLegal']";
	private static final String XPATH_LINK_CONSENT_PERSONAL_INFORMATION = XPATH_RADIO_CONSENT_PERSONAL_INFORMATION + "/..//*[@data-testid='mng-link']";
	private static final String XPATH_PERSONAL_INFORMATION_INFO = "//div[@id='createAccountLegal_description']";	

	private static final String XPATH_CREATE_ACCOUNT_BUTTON = XPATH_MODAL_CONTENT + "//div[@class='mng-form-buttons']/button[@type='submit']";	
	
	private static final String XPATH_LINK_POLITICA_PRIVACIDAD = XPATH_MODAL_CONTENT + "/div/div/p/*[@data-testid='mng-link']";
	private static final String XPATH_MODAL_POLITICA_PRIVACIDAD = 
			"//*[text()[contains(.,'¿Cómo tratamos y protegemos tus datos?')]]" + 
			"/following-sibling::p";

	private static final String XPATH_LINK_POLITICA_PRIVACIDAD_MODAL = XPATH_MODAL_POLITICA_PRIVACIDAD + "//*[@data-testid='mng-link']";

	public PageRegistroInitialShop() {
		super(NUEVO_REGISTRO_LEGAL_TEXTS);
	}
	
	public boolean isPage() {
		return isPage(0);
	}
	public boolean isPage(int seconds) {
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
		return state(Visible, XPATH_MODAL_POLITICA_PRIVACIDAD).check();
	}
	public boolean isModalPoliticaPrivacidadInvisible(int seconds) {
		return state(Invisible, XPATH_MODAL_POLITICA_PRIVACIDAD).wait(seconds).check();
	}	
	public void clickPoliticaPrivacidadModal() {
		click(XPATH_LINK_POLITICA_PRIVACIDAD_MODAL).exec();
	}
//	public void closeModalPoliticaPrivacidad() {
//		if (channel.isDevice()) {
//			click(XPATH_CLOSE_MODAL_DEVICE).exec();
//		} else {
//			click(XPATH_CLOSE_MODAL_DESKTOP).exec();
//		}
//	}
}
