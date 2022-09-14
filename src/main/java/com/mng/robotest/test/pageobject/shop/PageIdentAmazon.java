package com.mng.robotest.test.pageobject.shop;

import com.mng.robotest.domains.transversal.PageBase;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

public class PageIdentAmazon extends PageBase {

	private static final String XPATH_IMG_LOGO_AMAZON = "//div/img[@src[contains(.,'logo_payments')]]";
	private static final String XPATH_INPUT_EMAIL = "//input[@id='ap_email']";
	private static final String XPATH_INPUT_PASSWORD = "//input[@id='ap_password']";

	public boolean isLogoAmazon() { 
		return state(Visible, XPATH_IMG_LOGO_AMAZON).check();
	}

	public boolean isPageIdent() {
		return (
			state(Visible, XPATH_INPUT_EMAIL).check() &&
			state(Visible, XPATH_INPUT_PASSWORD).check());
	}
}
