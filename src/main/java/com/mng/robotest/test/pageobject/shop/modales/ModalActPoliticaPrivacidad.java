package com.mng.robotest.test.pageobject.shop.modales;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

import com.mng.robotest.domains.base.PageBase;

public class ModalActPoliticaPrivacidad extends PageBase {

	private static final String XPATH_MODAL = "//div[@class='modal-info-gdpr']";
	private static final String XPATH_BUTTON_OK = XPATH_MODAL + "//input[@type='submit']";
	
	public boolean isVisible() {
		return state(Visible, XPATH_MODAL).check();
	}
	
	public void clickOk() {
		click(XPATH_BUTTON_OK).exec();
	}
	
	public void clickOkIfVisible() {
		if (isVisible()) {
			clickOk();
		}
	}
}
