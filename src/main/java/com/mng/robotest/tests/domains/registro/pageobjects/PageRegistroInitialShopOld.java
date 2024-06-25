package com.mng.robotest.tests.domains.registro.pageobjects;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

public class PageRegistroInitialShopOld extends PageRegistroInitialShop {

	private static final String XP_MODAL_CONTENT = "//micro-frontend[@id='registry']";
	private static final String XP_INPUT_EMAIL = XP_MODAL_CONTENT + "//input[@data-testid[contains(.,'emailInput')]]";
	private static final String XP_INPUT_PASSWORD = XP_MODAL_CONTENT + "//input[@data-testid[contains(.,'passInput')]]";
	private static final String XP_INPUT_BIRTHDATE = "//input[@id='birthdate']";
	private static final String XP_INPUT_MOVIL = XP_MODAL_CONTENT + "//input[@data-testid[contains(.,'phoneInput')]]";
	private static final String XP_CHECKBOX_GIVE_PROMOTIONS = XP_MODAL_CONTENT + "//input[@data-testid[contains(.,'subscribeCheckbox.subscribeToNewsletter')]]";
	private static final String XP_LINK_GIVE_PROMOTIONS = XP_CHECKBOX_GIVE_PROMOTIONS + "/..//*[@data-testid='mng-link']";	
	private static final String XP_RADIO_CONSENT_PERSONAL_INFORMATION = "//input[@id='createAccountLegal']";
	private static final String XP_LINK_CONSENT_PERSONAL_INFORMATION = XP_RADIO_CONSENT_PERSONAL_INFORMATION + "/..//*[@data-testid='mng-link']";
	private static final String XP_PERSONAL_INFORMATION_INFO = "//div[@id='createAccountLegal_description']";	
	private static final String XP_CREATE_ACCOUNT_BUTTON = XP_MODAL_CONTENT + "//button[@data-testid[contains(.,'submitButton.submit')]]";	
	private static final String XP_LINK_POLITICA_PRIVACIDAD = XP_MODAL_CONTENT + "//div/p/*[@data-testid='mng-link']";
	private static final String XP_LINK_CONDICIONES_VENTA = XP_MODAL_CONTENT + "//*[@data-testid='mng-link' and @href[contains(.,'terms-and-conditions')]]";
	private static final String XP_MESSAGE_ERROR_MOVIL = "//*[@id='mobile-number-error']";	
	
	private static final String XP_MODAL_MESSAGE_ERROR_DESKTOP = "//*[@aria-describedby[contains(.,'genericErrorModal')]]";
	private static final String XP_MODAL_MESSAGE_USER_EXISTS_DESKTOP = XP_MODAL_MESSAGE_ERROR_DESKTOP + "//p[text()[contains(.,'¿Ya tienes cuenta?')]]";
	private static final String XP_CLOSE_MODAL_MESSAGE_ERROR_DESKTOP = XP_MODAL_MESSAGE_ERROR_DESKTOP + "//*[@data-testid='modal.close.button']";
	private static final String XP_MODAL_MESSAGE_ERROR_MOVIL = "//*[@data-testid='sheet.draggable.dialog']";
	private static final String XP_MODAL_MESSAGE_USER_EXISTS_MOVIL = XP_MODAL_MESSAGE_ERROR_MOVIL + "//p[text()[contains(.,'¿Ya tienes cuenta?')]]";
	private static final String XP_CLOSE_MODAL_MESSAGE_ERROR_MOVIL = XP_MODAL_MESSAGE_ERROR_MOVIL + "//button/span[text()='Cancelar']";
	
	private String getXPathModalPoliticaPrivacidad() {
		return
			"//*[text()[contains(.,'" + getLiteralPoliticaPrivacidad() + "')]]" + 
			"/following-sibling::p";
	}
	private String getXPathLinkPoliticaPrivacidad() {
		return getXPathModalPoliticaPrivacidad() + "//*[@data-testid='mng-link']";
	}

	private String getXPathModalMessageUserExists() {
		if (channel.isDevice()) {
			return XP_MODAL_MESSAGE_USER_EXISTS_MOVIL;
		}
		return XP_MODAL_MESSAGE_USER_EXISTS_DESKTOP;
	}
	
	private String getXPathCloseModalMessageError() {
		if (channel.isDevice()) {
			return XP_CLOSE_MODAL_MESSAGE_ERROR_MOVIL;
		}
		return XP_CLOSE_MODAL_MESSAGE_ERROR_DESKTOP;
	}	

	@Override
	public boolean isPage() {
		return isPage(0);
	}
	
	@Override
	public boolean isPage(int seconds) {
		return state(VISIBLE, XP_CHECKBOX_GIVE_PROMOTIONS).wait(seconds).check();
	}
	
	@Override
	public void inputEmail(String email) {
		getElement(XP_INPUT_EMAIL).sendKeys(KEYS_CLEAR_INPUT);
		getElement(XP_INPUT_EMAIL).sendKeys(email);
	}
	
	@Override
	public void inputPassword(String password) {
		state(VISIBLE, XP_INPUT_PASSWORD).wait(1).check();
		getElement(XP_INPUT_PASSWORD).sendKeys(KEYS_CLEAR_INPUT);
		getElement(XP_INPUT_PASSWORD).sendKeys(password);
	}

	@Override
	public void inputMovil(String number) {
		moveToInputMovil();
		getElement(XP_INPUT_MOVIL).sendKeys(KEYS_CLEAR_INPUT);
		getElement(XP_INPUT_MOVIL).sendKeys(number);
	}
	
	private void moveToInputMovil() {
		for (int i=0; i<3; i++) {
			if (state(VISIBLE, XP_INPUT_MOVIL).check()) {
				return;
			}
			keyUp(5);
		}
	}
	
	@Override
	public void inputBirthDate(String birthdate) {
		getElement(XP_INPUT_BIRTHDATE).sendKeys(birthdate);
	}

	@Override
	public void enableCheckBoxGivePromotions() {
		if (!isSelectedCheckboxGivePromotions()) {
			clickCheckBoxGivePromotions();
		}
	}
	
	@Override
	public void disableCheckBoxGivePromotions() {
		if (isSelectedCheckboxGivePromotions()) {
			clickCheckBoxGivePromotions();
		}		
	}	
	
	private void clickCheckBoxGivePromotions() {
		click(XP_CHECKBOX_GIVE_PROMOTIONS).exec();
	}
	
	@Override
	public boolean isSelectedCheckboxGivePromotions() {
		state(VISIBLE, XP_CHECKBOX_GIVE_PROMOTIONS).wait(1).check();
		return getElement(XP_CHECKBOX_GIVE_PROMOTIONS).isSelected();
	}
	
	@Override
	public void clickLinkGivePromotions() {
		click(XP_LINK_GIVE_PROMOTIONS).exec();
	}
	@Override
	public void clickConsentPersonalInformationRadio() {
		click(XP_RADIO_CONSENT_PERSONAL_INFORMATION).exec();
	}	
	@Override
	public void clickConsentPersonalInformationLink() {
		click(XP_LINK_CONSENT_PERSONAL_INFORMATION).exec();
	}
	@Override
	public boolean checkPersonalInformationInfoVisible() {
		return state(VISIBLE, XP_PERSONAL_INFORMATION_INFO).check();
	}
	@Override
	public void clickCreateAccountButton() {
		click(XP_CREATE_ACCOUNT_BUTTON).exec();
	}
	@Override
	public boolean checkUserExistsModalMessage(int seconds) {
		return state(VISIBLE, getXPathModalMessageUserExists()).wait(seconds).check();
	}
	@Override
	public boolean checkMessageErrorMovil(int seconds) {
		return state(VISIBLE, XP_MESSAGE_ERROR_MOVIL).wait(seconds).check();
	}
	@Override
	public void closeModalMessageError() {
		click(getXPathCloseModalMessageError()).exec();
	}
	@Override
	public void clickPoliticaPrivacidad() {
		click(XP_LINK_POLITICA_PRIVACIDAD).exec();
	}
	@Override
	public boolean isModalPoliticaPrivacidadVisible(int seconds) {
		return state(VISIBLE, getXPathModalPoliticaPrivacidad()).wait(seconds).check();
	}
	@Override
	public boolean isModalPoliticaPrivacidadInvisible(int seconds) {
		return state(INVISIBLE, getXPathModalPoliticaPrivacidad()).wait(seconds).check();
	}	
	@Override
	public void clickPoliticaPrivacidadModal() {
		click(getXPathLinkPoliticaPrivacidad()).exec();
	}
	@Override
	public void clickCondicionesVenta() {
		click(XP_LINK_CONDICIONES_VENTA).exec();
	}
	
	@Override
	public void keyDown(int times) {
		clickModalContentCorner();
		super.keyDown(times);
	}
	
	@Override
	public void keyUp(int times) {
		clickModalContentCorner();
		super.keyUp(times);
	}
	
	@Override
	public void clickModalContentCorner() {
		click(XP_MODAL_CONTENT).setX(1).setY(1).exec();
	}
	
}

