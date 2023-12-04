package com.mng.robotest.testslegacy.pageobject.shop;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

import com.mng.robotest.tests.domains.base.PageBase;

public class PageIdentAmazon extends PageBase {

	private static final String XP_IMG_LOGO_AMAZON = "//div/img[@src[contains(.,'logo_payments')]]";
	private static final String XP_INPUT_EMAIL = "//input[@id='ap_email']";
	private static final String XP_INPUT_PASSWORD = "//input[@id='ap_password']";

	public boolean isLogoAmazon() { 
		return state(VISIBLE, XP_IMG_LOGO_AMAZON).check();
	}

	public boolean isPageIdent() {
		return (
			state(VISIBLE, XP_INPUT_EMAIL).check() &&
			state(VISIBLE, XP_INPUT_PASSWORD).check());
	}
}
