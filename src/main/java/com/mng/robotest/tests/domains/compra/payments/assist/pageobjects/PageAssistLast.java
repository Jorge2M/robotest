package com.mng.robotest.tests.domains.compra.payments.assist.pageobjects;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

import com.mng.robotest.tests.domains.base.PageBase;

public class PageAssistLast extends PageBase {

	private static final String XP_BUTTON_SUBMIT = "//button[@type='submit']";

	public boolean isPage() {
		return state(Present, XP_BUTTON_SUBMIT).check();
	}

	public void clickButtonSubmit() {
		click(XP_BUTTON_SUBMIT).exec();
	}
}
