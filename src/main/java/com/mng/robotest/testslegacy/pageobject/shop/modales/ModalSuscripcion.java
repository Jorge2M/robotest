package com.mng.robotest.testslegacy.pageobject.shop.modales;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

import com.mng.robotest.tests.domains.base.PageBase;

public class ModalSuscripcion extends PageBase {

	private static final String XP_LEGAL_RGPD = "//p[@class='gdpr-text gdpr-data-protection']";

	public boolean isTextoLegalRGPDPresent() {
		return state(PRESENT, XP_LEGAL_RGPD).check();
	}
	
}