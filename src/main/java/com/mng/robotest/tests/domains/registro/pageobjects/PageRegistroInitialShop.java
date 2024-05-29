package com.mng.robotest.tests.domains.registro.pageobjects;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;
import static com.mng.robotest.tests.domains.legal.legaltexts.FactoryLegalTexts.PageLegalTexts.*;

import com.mng.robotest.tests.domains.base.PageBase;

public class PageRegistroInitialShop extends PageBase {

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

	//TODO pedir data-testid
	private String getLiteralPoliticaPrivacidad() {
		switch (dataTest.getIdioma().getCodigo()) {
		case AL: //"Deutsch"
			return "Grundlegende Informationen zum Datenschutz";
		case BG: //"български" (Búlgaro)
			return "Основна информация за защита на данните";
		case CA: //"Català"
			return "Informació bàsica sobre protecció de dades";
		case CS: //"Čeština" (Checo)
			return "Základní informace o ochraně osobních údajů";
		case ES: //"Castellano"
			return "Información básica sobre protección de datos";
		case FR: //"Français"
			return "Informations sur la protection des données";
		case HU: //"Magyar" (Húngaro)
			return "Alapvető adatvédelmi információk";
		case ID: //"Bahasa indonesia"
			return "Informasi dasar tentang perlindungan data";
		case IT: //"Italiano"
			return "Informazioni di base sulla protezione dei dati";
		case HR: //"Hrvatski" (Croata)
			return "Osnovne informacije o zaštiti podataka";
		case JA: //"日本"
			return "データ保護に関する基本情報";
		case KO: //"한국어"
			return "데이터 보호에 대한 기본 정보";
		case EL: //"Ελληνικά" (Griego)
			return "Βασικές πληροφορίες περί προστασίας δεδομένων";
		case NL: //"Nederlands"
			return "Basisinformatie inzake gegevensbescherming";
		case NO: //"Norsk"
			return "Grunnleggende informasjon om databeskyttelse";
		case PL: //"Polski"
			return "Podstawowe informacje o ochronie danych";
		case PO: //"Português
			return "Informação básica sobre proteção de dados";
		case RO: //"Româna"
			return "Informații de bază privind protecția datelor personale";
		case RU,SV: //"Русский", "Svenska" (Sueco)
			return "Grundläggande information om uppgiftsskydd";
		case TH: //"ไทย" (Tailandés)
			return "??????";
		case TR: //"Türk"
			return "Veri korumasına ilişkin temel bilgiler";
		case ZH: //"中文" (Chino)
			return "数据保护基本信息";
		case AR: //"العربية"
			return "معلومات أساسية عن حماية البيانات";
		case US,IN: //"English (USA)", "English"			
		default:
			return "Basic information on data protection";
		}
	}
	
	public PageRegistroInitialShop() {
		super(NUEVO_REGISTRO_LEGAL_TEXTS);
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
	
	
	public boolean isPage() {
		return isPage(0);
	}
	public boolean isPage(int seconds) {
		return state(VISIBLE, XP_CHECKBOX_GIVE_PROMOTIONS).wait(seconds).check();
	}
	
	public void inputEmail(String email) {
		getElement(XP_INPUT_EMAIL).sendKeys(KEYS_CLEAR_INPUT);
		getElement(XP_INPUT_EMAIL).sendKeys(email);
	}
	
	public void inputPassword(String password) {
		state(VISIBLE, XP_INPUT_PASSWORD).wait(1).check();
		getElement(XP_INPUT_PASSWORD).sendKeys(KEYS_CLEAR_INPUT);
		getElement(XP_INPUT_PASSWORD).sendKeys(password);
	}

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
	
	public void inputBirthDate(String birthdate) {
		getElement(XP_INPUT_BIRTHDATE).sendKeys(birthdate);
	}

	public void enableCheckBoxGivePromotions() {
		if (!isSelectedCheckboxGivePromotions()) {
			clickCheckBoxGivePromotions();
		}
	}
	public void disableCheckBoxGivePromotions() {
		if (isSelectedCheckboxGivePromotions()) {
			clickCheckBoxGivePromotions();
		}		
	}	
	private void clickCheckBoxGivePromotions() {
		click(XP_CHECKBOX_GIVE_PROMOTIONS).exec();
	}
	public boolean isSelectedCheckboxGivePromotions() {
		state(VISIBLE, XP_CHECKBOX_GIVE_PROMOTIONS).wait(1).check();
		return getElement(XP_CHECKBOX_GIVE_PROMOTIONS).isSelected();
	}
	
	public void clickLinkGivePromotions() {
		click(XP_LINK_GIVE_PROMOTIONS).exec();
	}
	public void clickConsentPersonalInformationRadio() {
		click(XP_RADIO_CONSENT_PERSONAL_INFORMATION).exec();
	}	
	public void clickConsentPersonalInformationLink() {
		click(XP_LINK_CONSENT_PERSONAL_INFORMATION).exec();
	}
	public boolean checkPersonalInformationInfoVisible() {
		return state(VISIBLE, XP_PERSONAL_INFORMATION_INFO).check();
	}
	
	public void clickCreateAccountButton() {
		click(XP_CREATE_ACCOUNT_BUTTON).exec();
	}
	
	public boolean checkUserExistsModalMessage(int seconds) {
		return state(VISIBLE, getXPathModalMessageUserExists()).wait(seconds).check();
	}
	public boolean checkMessageErrorMovil(int seconds) {
		return state(VISIBLE, XP_MESSAGE_ERROR_MOVIL).wait(seconds).check();
	}
	
	public void closeModalMessageError() {
		click(getXPathCloseModalMessageError()).exec();
	}
	
	public void clickPoliticaPrivacidad() {
		click(XP_LINK_POLITICA_PRIVACIDAD).exec();
	}
	public boolean isModalPoliticaPrivacidadVisible(int seconds) {
		return state(VISIBLE, getXPathModalPoliticaPrivacidad()).wait(seconds).check();
	}
	public boolean isModalPoliticaPrivacidadInvisible(int seconds) {
		return state(INVISIBLE, getXPathModalPoliticaPrivacidad()).wait(seconds).check();
	}	
	public void clickPoliticaPrivacidadModal() {
		click(getXPathLinkPoliticaPrivacidad()).exec();
	}
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
	private void clickModalContentCorner() {
		click(XP_MODAL_CONTENT).setX(1).setY(1).exec();
	}
	
}
