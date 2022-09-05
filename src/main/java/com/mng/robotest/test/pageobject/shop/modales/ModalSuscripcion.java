package com.mng.robotest.test.pageobject.shop.modales;

import com.mng.robotest.domains.transversal.PageBase;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

public class ModalSuscripcion extends PageBase {

	private static String XPATH_LEGAL_RGPD = "//p[@class='gdpr-text gdpr-data-protection']";

	public boolean isTextoLegalRGPDPresent() {
		return state(Present, XPATH_LEGAL_RGPD).check();
	}
	
}