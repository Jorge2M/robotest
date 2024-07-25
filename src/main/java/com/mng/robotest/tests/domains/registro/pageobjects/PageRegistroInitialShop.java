package com.mng.robotest.tests.domains.registro.pageobjects;

import static com.mng.robotest.tests.domains.legal.legaltexts.FactoryLegalTexts.PageLegalTexts.NUEVO_REGISTRO_LEGAL_TEXTS;

import com.mng.robotest.tests.domains.base.PageBase;

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

	public static PageRegistroInitialShop make() {
		if (!new CommonsRegisterObject().isGenesis()) {
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
			return "Wie schützen und verarbeiten wir Ihre Daten?"; //
		case BG: //"български" (Búlgaro)
			return "По какъв начин защитаваме и обработваме вашите лични данни?"; //
		case CA: //"Català"
			return "Com protegim i tractem les teves dades?"; //
		case CS: //"Čeština" (Checo)
			return "Jak chráníme a zpracováváme vaše údaje?"; //
		case ES: //"Castellano"
			return "¿Cómo protegemos y tratamos tus datos?"; //
		case FR: //"Français"
			return "Comment protégeons-nous et traitons-nous vos données ?"; //
		case HU: //"Magyar" (Húngaro)
			return "Hogyan védjük és kezeljük az adataidat?"; //
		case ID: //"Bahasa indonesia"
			return "Bagaimana kami melindungi dan memperlakukan data Anda?"; //
		case IT: //"Italiano"
			return "Come proteggiamo e trattiamo i tuoi dati?"; //
		case HR: //"Hrvatski" (Croata)
			return "Kako štitimo i obrađujemo vaše podatke?"; //
		case JA: //"日本"
			return "データ保護に関する基本情報";
		case KO: //"한국어"
			return "데이터 보호에 대한 기본 정보";
		case EL: //"Ελληνικά" (Griego)
			return "Πώς προστατεύουμε και διαχειριζόμαστε τα δεδομένα σας;"; //
		case NL: //"Nederlands"
			return "Hoe beschermen en verwerken we je gegevens?"; //
		case NO: //"Norsk"
			return "Hvordan beskytter og behandler vi dataene dine?"; //
		case PL: //"Polski"
			return "Jak chronimy i przetwarzamy Twoje dane?"; //
		case PO: //"Português
			return "Como é que protegemos e processamos os seus dados?"; //
		case RO: //"Româna"
			return "Cum îți protejăm și cum îți prelucrăm datele?"; //
		case RU: //"Русский"
			return "Как мы защищаем и обрабатываем Ваши данные?"; //
		case SV: //"Svenska" (Sueco)
			return "Hur skyddar och behandlar vi dina uppgifter?"; //
		case TH: //"ไทย" (Tailandés)
			return "เราจะปกป้องและประมวลผลข้อมูลของคุณได้อย่างไร?"; //
		case TR: //"Türk"
			return "Veri korumasına ilişkin temel bilgiler";
		case ZH: //"中文" (Chino)
			return "我们如何保护和处理您的数据？";
		case AR: //"العربية"
			return "كيف نحمي بياناتك ونعالجها؟"; //
		case US,IN: //"English (USA)", "English"			
		default:
			return "How do we protect and process your data?"; //
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

