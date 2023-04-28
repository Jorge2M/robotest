package com.mng.robotest.domains.legal.pageobjects;

import java.util.NoSuchElementException;

/**
 * info https://confluence.mango.com/display/PIUR/Mapeo+de+textos+legales#expand-Suscripcin
 *
 */
public class FactoryLegalTexts {

	public enum PageLegalText {
		NUEVO_REGISTRO,
		//ANTIGUO REGISTRO
		GUEST_CHECKOUT_PASO_1,
		GUEST_CHECKOUT_PASO_2,
		SUSCRIPCION,
		SUSCRIPCION_EN_FOOTER_Y_NON_MODAL,
		AVISAME_Y_SUSCRIPCION,
		MIS_DATOS,
		MANGO_CARD,
		FORMULARIO_DE_AYUDA,
		CHEQUE_REGALO_PAGOS;
	}
	
	public static LegalTextsPage make(PageLegalText page) {
		switch(page) {
		case NUEVO_REGISTRO:
		case GUEST_CHECKOUT_PASO_1:
		case GUEST_CHECKOUT_PASO_2:
		case SUSCRIPCION:
		case SUSCRIPCION_EN_FOOTER_Y_NON_MODAL:
			return new LTSecFooter();
		case AVISAME_Y_SUSCRIPCION:
			return new LTModalArticleNotAvailable();
		case MIS_DATOS:
			return new LTPageMisDatos();
		case MANGO_CARD:
			return new LTPageMangoCard();
		case FORMULARIO_DE_AYUDA:
			return new LTPageAyudaContact();
		case CHEQUE_REGALO_PAGOS:
			return new LTPageChequeRegaloInputDataNew();
		default:
			throw new NoSuchElementException(page.name());
		}
			
	}

}
