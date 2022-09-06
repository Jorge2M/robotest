package com.mng.robotest.test.pageobject.shop.checkout.klarna;

import com.mng.robotest.domains.transversal.PageBase;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State;

public class ModalConfUserDataKlarna extends PageBase {

	private static final String XPATH_BUTTON_CONFIRMATION = "//div[@id[contains(.,'footer-button-wrapper')]]//button";
	
	public boolean isModal(int maxSeconds) {
		return state(State.Visible, XPATH_BUTTON_CONFIRMATION).wait(maxSeconds).check();
	}
	
	public void clickButtonConfirmation() {
		click(XPATH_BUTTON_CONFIRMATION).exec();
	}
}
