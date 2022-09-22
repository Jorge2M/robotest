package com.mng.robotest.domains.compra.payments.paymaya.pageobjects;

import com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State;
import com.mng.robotest.domains.transversal.PageBase;

public class PageIdentPaymaya extends PageBase {

	private static final String XPATH_WRAPPER_LOGIN = "//div[@class[contains(.,'login-form-index')]]";
	private static final String XPATH_INPUT_USER = "//input[@id='identityValue']";
	private static final String XPATH_INPUT_PASSWORD = "//input[@id='password']";
	private static final String XPATH_LOGIN_BUTTON = "//input[@class[contains(.,'login-btn')]]";
	
	public boolean isPage() {
		return state(State.Visible, XPATH_WRAPPER_LOGIN).check();
	}
	
	public void login(String user, String password) {
		getElement(XPATH_INPUT_USER).sendKeys(user);
		getElement(XPATH_INPUT_PASSWORD).sendKeys(password);
		click(XPATH_LOGIN_BUTTON).exec();
	}
}
