package com.mng.robotest.test.pageobject.shop.checkout.paypal;

import com.mng.robotest.domains.transversal.PageBase;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

public class ModalPreloaderSpinner extends PageBase {
	
	private static final String XPATH_PRE_LOADER_SPINNER = "//div[@id='preloaderSpinner']";

	public boolean isVisibleUntil(int maxSeconds) {
		return state(Visible, XPATH_PRE_LOADER_SPINNER).wait(maxSeconds).check();
	}
	
	public boolean isNotVisibleUntil(int maxSeconds) {
		return state(Invisible, XPATH_PRE_LOADER_SPINNER).wait(maxSeconds).check();
	}
}
