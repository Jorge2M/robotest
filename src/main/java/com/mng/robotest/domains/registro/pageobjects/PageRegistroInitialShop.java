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
	private static final String XPATH_INPUT_MOVIL = XPATH_MODAL_CONTENT + "//input[@data-testid[contains(.,'phoneInput')]]";
	private static final String XPATH_CHECKBOX_GIVE_PROMOTIONS = XPATH_MODAL_CONTENT + "//input[@data-testid[contains(.,'subscribeCheckbox.subscribeToNewsletter')]]";
	private static final String XPATH_LINK_GIVE_PROMOTIONS = XPATH_CHECKBOX_GIVE_PROMOTIONS + "/..//*[@data-testid='mng-link']";	
	private static final String XPATH_RADIO_CONSENT_PERSONAL_INFORMATION = "//input[@id='createAccountLegal']";
	private static final String XPATH_LINK_CONSENT_PERSONAL_INFORMATION = XPATH_RADIO_CONSENT_PERSONAL_INFORMATION + "/..//*[@data-testid='mng-link']";
	private static final String XPATH_PERSONAL_INFORMATION_INFO = "//div[@id='createAccountLegal_description']";	
	private static final String XPATH_CREATE_ACCOUNT_BUTTON = XPATH_MODAL_CONTENT + "//button[@data-testid[contains(.,'submitButton.submit')]]";	
	private static final String XPATH_LINK_POLITICA_PRIVACIDAD = XPATH_MODAL_CONTENT + "/div/div/p/*[@data-testid='mng-link']";
	private static final String XPATH_LINK_CONDICIONES_VENTA = XPATH_MODAL_CONTENT + "//*[@data-testid='mng-link' and @href[contains(.,'terms-and-conditions')]]";

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
		case KR: //"한국어"
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
		case RU: //"Русский"
		case SE: //"Svenska" (Sueco)
			return "Grundläggande information om uppgiftsskydd";
		case TH: //"ไทย" (Tailandés)
			return "??????";
		case TR: //"Türk"
			return "Veri korumasına ilişkin temel bilgiler";
		case ZH: //"中文" (Chino)
			return "数据保护基本信息";
		case AR: //"العربية"
			return "معلومات أساسية عن حماية البيانات";
		case US: //"English (USA)"			
		case IN: //"English"
		default:
			return "Basic information on data protection";
			
		}
	}
	
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
		moveToInputMovil();
		getElement(XPATH_INPUT_MOVIL).sendKeys(number);
	}
	private void moveToInputMovil() {
		for (int i=0; i<3; i++) {
			if (state(Visible, XPATH_INPUT_MOVIL).check()) {
				return;
			}
			keyUp(5);
		}
	}
	
	public void inputBirthDate(String birthdate) {
		getElement(XPATH_INPUT_BIRTHDATE).sendKeys(birthdate);
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
		click(XPATH_CHECKBOX_GIVE_PROMOTIONS).exec();
	}
	public boolean isSelectedCheckboxGivePromotions() {
		return getElement(XPATH_CHECKBOX_GIVE_PROMOTIONS).isSelected();
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
	public boolean isModalPoliticaPrivacidadVisible(int seconds) {
		return state(Visible, getXPathModalPoliticaPrivacidad()).wait(seconds).check();
	}
	public boolean isModalPoliticaPrivacidadInvisible(int seconds) {
		return state(Invisible, getXPathModalPoliticaPrivacidad()).wait(seconds).check();
	}	
	public void clickPoliticaPrivacidadModal() {
		click(getXPathLinkPoliticaPrivacidad()).exec();
	}
	public void clickCondicionesVenta() {
		click(XPATH_LINK_CONDICIONES_VENTA).exec();
	}
	public void keyDown(int times) {
		clickModalContentCorner();
		super.keyDown(times);
	}
	public void keyUp(int times) {
		clickModalContentCorner();
		super.keyUp(times);
	}
	private void clickModalContentCorner() {
		click(XPATH_MODAL_CONTENT).setX(1).setY(1).exec();
	}
}
