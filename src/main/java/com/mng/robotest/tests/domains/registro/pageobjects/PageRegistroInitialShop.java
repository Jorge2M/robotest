package com.mng.robotest.tests.domains.registro.pageobjects;

import static com.mng.robotest.tests.domains.legal.legaltexts.FactoryLegalTexts.PageLegalTexts.NUEVO_REGISTRO_LEGAL_TEXTS;

import com.github.jorge2m.testmaker.conf.Channel;
import com.mng.robotest.tests.domains.base.PageBase;
import com.mng.robotest.testslegacy.beans.Pais;
import com.mng.robotest.testslegacy.data.PaisShop;

public abstract class PageRegistroInitialShop extends PageBase {

	public abstract boolean isPage();
	public abstract boolean isPage(int seconds);
	public abstract void inputEmail(String email);
	public abstract void inputPassword(String password);
	public abstract void inputMovil(String number);
	public abstract void inputBirthDate(String birthdate);
	public abstract void enableCheckBoxGivePromotions();
	public abstract void disableCheckBoxGivePromotions();
	public abstract boolean isSelectedCheckboxGivePromotions();
	public abstract void clickLinkGivePromotions();
	public abstract void clickConsentPersonalInformationRadio();
	public abstract void clickConsentPersonalInformationLink();
	public abstract boolean checkPersonalInformationInfoVisible();
	public abstract void clickCreateAccountButton();
	public abstract boolean checkUserExistsModalMessage(int seconds);
	public abstract boolean checkMessageErrorMovil(int seconds);
	public abstract void closeModalMessageError();
	public abstract void clickPoliticaPrivacidad();
	public abstract boolean isModalPoliticaPrivacidadVisible(int seconds);
	public abstract boolean isModalPoliticaPrivacidadInvisible(int seconds);
	public abstract void clickPoliticaPrivacidadModal();
	public abstract void clickCondicionesVenta();
	public abstract void clickModalContentCorner();

	public static PageRegistroInitialShop make(String urlBase, Pais pais, Channel channel) {
		if (PageBase.isPRO(urlBase) ||
			!PaisShop.ESPANA.isEquals(pais) ||
			channel.isDevice()) {
			return new PageRegistroInitialShopOld();
		}
		return new PageRegistroInitialShopGenesis();
	}
	
	PageRegistroInitialShop() {
		super(NUEVO_REGISTRO_LEGAL_TEXTS);
	}
	
	//TODO pedir data-testid
	protected String getLiteralPoliticaPrivacidad() {
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
	
}

