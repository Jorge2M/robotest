package com.mng.robotest.tests.domains.compra.payments.paypal.pageobjects;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

import com.mng.robotest.tests.domains.base.PageBase;

public class PageEpaymentIdent extends PageBase {

	private static final String XPATH_INPUT_USER = "//input[@name[contains(.,'USERID')] and @type='password']";
	private static final String XPATH_INPUT_CODE = "//input[@name[contains(.,'IDNBR')] and @type='password']";
	
	public boolean isPage() {
		return (driver.getTitle().contains("E-payment"));
	}
	
	public boolean isPresentInputUserTypePassword() {
		return state(Present, XPATH_INPUT_USER).check();
	}
	
	public boolean isPresentCodeUserTypePassword() {
		return state(Present, XPATH_INPUT_CODE).check();
	}
}
