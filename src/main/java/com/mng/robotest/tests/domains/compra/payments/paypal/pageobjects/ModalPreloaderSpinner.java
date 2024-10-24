package com.mng.robotest.tests.domains.compra.payments.paypal.pageobjects;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

import com.mng.robotest.tests.domains.base.PageBase;

public class ModalPreloaderSpinner extends PageBase {
	
	private static final String XP_PRE_LOADER_SPINNER = "//div[@id='preloaderSpinner']";

	public boolean isVisibleUntil(int seconds) {
		return state(VISIBLE, XP_PRE_LOADER_SPINNER).wait(seconds).check();
	}
	
	public boolean isNotVisibleUntil(int seconds) {
		return state(INVISIBLE, XP_PRE_LOADER_SPINNER).wait(seconds).check();
	}
}
