package com.mng.robotest.testslegacy.pageobject.shop.modales;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

import com.mng.robotest.tests.domains.base.PageBase;

public class ModalActPoliticaPrivacidad extends PageBase {

	private static final String XP_MODAL = "//div[@class='modal-info-gdpr']";
	private static final String XP_BUTTON_OK = XP_MODAL + "//input[@type='submit']";
	
	public boolean isVisible() {
		return state(VISIBLE, XP_MODAL).check();
	}
	
	public void clickOk() {
		click(XP_BUTTON_OK).exec();
	}
	
	public void clickOkIfVisible() {
		if (isVisible()) {
			clickOk();
		}
	}
}
