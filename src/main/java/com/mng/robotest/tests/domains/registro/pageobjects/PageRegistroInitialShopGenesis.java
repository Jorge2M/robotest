package com.mng.robotest.tests.domains.registro.pageobjects;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

import com.github.jorge2m.testmaker.service.webdriver.pageobject.TypeClick;

public class PageRegistroInitialShopGenesis extends PageRegistroInitialShop {

	private static final String XP_MODAL_CONTENT = "//div[@class[contains(.,'RegistryPageLayout')]]";
	private static final String XP_INPUT_EMAIL = XP_MODAL_CONTENT + "//input[@data-testid[contains(.,'emailInput')]]";
	private static final String XP_INPUT_PASSWORD = XP_MODAL_CONTENT + "//input[@data-testid[contains(.,'passwordInput')]]";
	private static final String XP_INPUT_BIRTHDATE = "//input[@id='birthdate']";
	private static final String XP_INPUT_MOVIL = XP_MODAL_CONTENT + "//input[@data-testid[contains(.,'phoneInput')]]";
	private static final String XP_CHECKBOX_GIVE_PROMOTIONS = XP_MODAL_CONTENT + "//input[@data-testid[contains(.,'registry.newsletterSubscription.subscribe')]]";	
	private static final String XP_LINK_GIVE_PROMOTIONS = XP_CHECKBOX_GIVE_PROMOTIONS + "/..//*[@data-testid='mng-link']";	
	private static final String XP_RADIO_CONSENT_PERSONAL_INFORMATION = "//input[@id='createAccountLegal']";
	private static final String XP_LINK_CONSENT_PERSONAL_INFORMATION = XP_RADIO_CONSENT_PERSONAL_INFORMATION + "/..//*[@data-testid='mng-link']";
	private static final String XP_PERSONAL_INFORMATION_INFO = "//div[@id='createAccountLegal_description']";	
	private static final String XP_CREATE_ACCOUNT_BUTTON = XP_MODAL_CONTENT + "//*[@data-testid='registry.registryButton.registry']";	
	private static final String XP_LINK_POLITICA_PRIVACIDAD = XP_MODAL_CONTENT + "//*[@data-testid='registry.privacyPolicy.expand']";	
	private static final String XP_LINK_CONDICIONES_VENTA = XP_MODAL_CONTENT + "//*[@data-testid='mng-link' and @href[contains(.,'terms-and-conditions')]]";
	private static final String XP_LINK_POLITICA_PRIVACIDAD_MODAL = XP_MODAL_CONTENT + "//*[@data-testid='registry.privacyPolicy.linkToRGPD']";
	private static final String XP_MESSAGE_ERROR_MOVIL = "//*[@id[contains(.,'phone-input-number-error')]]";	
	
	private static final String XP_MODAL_MESSAGE_ERROR = "//*[@id='registry-generic-modal']";
	private static final String XP_MODAL_MESSAGE_USER_EXISTS = XP_MODAL_MESSAGE_ERROR + "//p[text()[contains(.,'¿Ya tienes cuenta?')]]";
	private static final String XP_CLOSE_MODAL_MESSAGE_ERROR = XP_MODAL_MESSAGE_ERROR + "//*[@data-testid='registry.genericError.emptyFields']";

	private String getXPathModalPoliticaPrivacidad() {
		return
			"//*[text()[contains(.,'" + getLiteralPoliticaPrivacidad() + "')]]" + 
			"/following-sibling::p";
	}

	@Override
	protected String getLiteralPoliticaPrivacidad() {
		return super.getLiteralPoliticaPrivacidad();
	}
	
	@Override
	public boolean isPage() {
		return isPage(0);
	}
	
	@Override
	public boolean isPage(int seconds) {
		return state(PRESENT, XP_CHECKBOX_GIVE_PROMOTIONS).wait(seconds).check();
	}
	
	@Override
	public void inputEmail(String email) {
		getElement(XP_INPUT_EMAIL).sendKeys(KEYS_CLEAR_INPUT);
		getElement(XP_INPUT_EMAIL).sendKeys(email);
	}
	
	@Override
	public void inputPassword(String password) {
		state(PRESENT, XP_INPUT_PASSWORD).wait(1).check();
		getElement(XP_INPUT_PASSWORD).sendKeys(KEYS_CLEAR_INPUT);
		getElement(XP_INPUT_PASSWORD).sendKeys(password);
	}

	@Override
	public void inputMovil(String number) {
		moveToInputMovil();
		var inputMobil = getElement(XP_INPUT_MOVIL);
		inputMobil.sendKeys(KEYS_CLEAR_INPUT);
		
		//Workarround for paliate bug in sendKeys
	    for (char ch : number.toCharArray()) {
	        inputMobil.sendKeys(String.valueOf(ch));
	    }
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
		state(PRESENT, XP_CHECKBOX_GIVE_PROMOTIONS).wait(1).check();
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
		return state(VISIBLE, XP_MODAL_MESSAGE_USER_EXISTS).wait(seconds).check();
	}
	@Override
	public boolean checkMessageErrorMovil(int seconds) {
		return state(VISIBLE, XP_MESSAGE_ERROR_MOVIL).wait(seconds).check();
	}
	@Override
	public void closeModalMessageError() {
		click(XP_CLOSE_MODAL_MESSAGE_ERROR).exec();
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
		click(XP_LINK_POLITICA_PRIVACIDAD_MODAL).type(TypeClick.JAVASCRIPT).exec();
	}
	@Override
	public void clickCondicionesVenta() {
		click(XP_LINK_CONDICIONES_VENTA).exec();
	}
	
	@Override
	public void clickModalContentCorner() {
		click(XP_INPUT_EMAIL).exec();
		click(XP_MODAL_CONTENT + "/div").setX(1).setY(1).exec();
	}
	
}
