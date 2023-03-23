package com.mng.robotest.domains.compra.payments.postfinance.pageobjects;

import com.mng.robotest.domains.base.PageBase;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

public class PagePostfRedirect extends PageBase {

	private static final String XPATH_BUTTON_OK = "//form/input[@type='button' and @value[contains(.,'OK')]]";
	
	public boolean isPresentButtonOk() {
		return state(Present, XPATH_BUTTON_OK).check();
	}
	
	public boolean isInvisibleButtonOkUntil(int seconds) {
		return state(Invisible, XPATH_BUTTON_OK).wait(seconds).check();
	}
}
