package com.mng.robotest.domains.legal.legaltexts;

import java.util.NoSuchElementException;

/**
 * info https://confluence.mango.com/display/PIUR/Mapeo+de+textos+legales#expand-Suscripcin
 *
 */
public class FactoryLegalTexts {

	public enum PageLegalTexts {
		NUEVO_REGISTRO_LEGAL_TEXTS,
		//ANTIGUO REGISTRO
		//GUEST_CHECKOUT_PASO_1_LEGAL_TEXTS,
		GUEST_CHECKOUT_PASO_2_LEGAL_TEXTS,
		SUSCRIPCION_LEGAL_TEXTS,
		SUSCRIPCION_EN_FOOTER_Y_NON_MODAL_LEGAL_TEXTS,
		AVISAME_Y_SUSCRIPCION_LEGAL_TEXTS,
		MIS_DATOS_LEGAL_TEXTS,
		MANGO_CARD_LEGAL_TEXTS,
		FORMULARIO_DE_AYUDA_LEGAL_TEXTS,
		CHEQUE_REGALO_PAGOS_LEGAL_TEXTS
	}
	
	public static LegalTextsPage make(PageLegalTexts page) {
		switch(page) {
		case NUEVO_REGISTRO_LEGAL_TEXTS:
			return new LTPageRegistroInitialShop();
		case GUEST_CHECKOUT_PASO_2_LEGAL_TEXTS:
			return new LTPage2IdentCheckout();
		case SUSCRIPCION_LEGAL_TEXTS:
		case SUSCRIPCION_EN_FOOTER_Y_NON_MODAL_LEGAL_TEXTS:
			return new LTSecFooter();
		case AVISAME_Y_SUSCRIPCION_LEGAL_TEXTS:
			return new LTModalArticleNotAvailable();
		case MIS_DATOS_LEGAL_TEXTS:
			return new LTPageMisDatos();
		case MANGO_CARD_LEGAL_TEXTS:
			return new LTPageMangoCard();
		case FORMULARIO_DE_AYUDA_LEGAL_TEXTS:
			return new LTPageAyudaContact();
		case CHEQUE_REGALO_PAGOS_LEGAL_TEXTS:
			return new LTPageChequeRegaloInputDataNew();
		default:
			throw new NoSuchElementException(page.name());
		}
			
	}

}
