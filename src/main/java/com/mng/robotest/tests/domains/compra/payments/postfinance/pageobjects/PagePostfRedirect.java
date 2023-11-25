package com.mng.robotest.tests.domains.compra.payments.postfinance.pageobjects;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

import com.mng.robotest.tests.domains.base.PageBase;

public class PagePostfRedirect extends PageBase {

	private static final String XP_BUTTON_OK = "//form/input[@type='button' and @value[contains(.,'OK')]]";
	
	public boolean isPresentButtonOk() {
		return state(Present, XP_BUTTON_OK).check();
	}
	
	public boolean isInvisibleButtonOkUntil(int seconds) {
		return state(Invisible, XP_BUTTON_OK).wait(seconds).check();
	}
}
