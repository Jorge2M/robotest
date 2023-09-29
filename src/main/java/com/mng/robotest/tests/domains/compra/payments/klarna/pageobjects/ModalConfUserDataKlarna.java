package com.mng.robotest.tests.domains.compra.payments.klarna.pageobjects;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

import com.mng.robotest.tests.domains.base.PageBase;

public class ModalConfUserDataKlarna extends PageBase {

	private static final String XPATH_BUTTON_CONFIRMATION = "//div[@id[contains(.,'footer-button-wrapper')]]//button";
	
	public boolean isModal(int seconds) {
		return state(Visible, XPATH_BUTTON_CONFIRMATION).wait(seconds).check();
	}
	
	public void clickButtonConfirmation() {
		click(XPATH_BUTTON_CONFIRMATION).exec();
	}
}
