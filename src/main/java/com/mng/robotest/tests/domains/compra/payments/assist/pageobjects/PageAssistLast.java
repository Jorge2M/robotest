package com.mng.robotest.tests.domains.compra.payments.assist.pageobjects;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

import com.mng.robotest.tests.domains.base.PageBase;

public class PageAssistLast extends PageBase {

	private static final String XPATH_BUTTON_SUBMIT = "//button[@type='submit']";

	public boolean isPage() {
		return state(Present, XPATH_BUTTON_SUBMIT).check();
	}

	public void clickButtonSubmit() {
		click(XPATH_BUTTON_SUBMIT).exec();
	}
}
