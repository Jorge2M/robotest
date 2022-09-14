package com.mng.robotest.test.pageobject.shop.checkout.paypal;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

import com.mng.robotest.domains.transversal.PageBase;

public class ModalPreloaderSpinner extends PageBase {
	
	private static final String XPATH_PRE_LOADER_SPINNER = "//div[@id='preloaderSpinner']";

	public boolean isVisibleUntil(int seconds) {
		return state(Visible, XPATH_PRE_LOADER_SPINNER).wait(seconds).check();
	}
	
	public boolean isNotVisibleUntil(int seconds) {
		return state(Invisible, XPATH_PRE_LOADER_SPINNER).wait(seconds).check();
	}
}
