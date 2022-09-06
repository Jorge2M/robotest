package com.mng.robotest.test.pageobject.shop.checkout.epayment;

import com.mng.robotest.domains.transversal.PageBase;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

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
