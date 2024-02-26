package com.mng.robotest.tests.domains.changecountry.pageobjects;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.INVISIBLE;

import com.mng.robotest.tests.domains.base.PageBase;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

public class ModalGeolocation extends PageBase {

	private static final String XP_MODAL = "//*[@id='geolocation-modal']";
	private static final String XP_CLOSE_ICON = "//*[@data-testid='modal.close.button']";
	
	public boolean isVisible() {
		return true;
	}
	
	public void closeModalIfVisible() {
		if (isVisible(0)) {
			getElementVisible(XP_CLOSE_ICON).click();
			state(INVISIBLE, XP_MODAL).wait(1).check();
		}
	}
	
	private boolean isVisible(int seconds) {
		return state(VISIBLE, XP_MODAL).wait(seconds).check();
	}

}
