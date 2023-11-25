package com.mng.robotest.tests.domains.compra.payments.paymaya.pageobjects;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

import com.mng.robotest.tests.domains.base.PageBase;

public class PageIdentPaymaya extends PageBase {

	private static final String XP_WRAPPER_LOGIN = "//div[@class[contains(.,'login-form-index')]]";
	private static final String XP_INPUT_USER = "//input[@id='identityValue']";
	private static final String XP_INPUT_PASSWORD = "//input[@id='password']";
	private static final String XP_LOGIN_BUTTON = "//input[@class[contains(.,'login-btn')]]";
	
	public boolean isPage() {
		return state(Visible, XP_WRAPPER_LOGIN).check();
	}
	
	public void login(String user, String password) {
		getElement(XP_INPUT_USER).sendKeys(user);
		getElement(XP_INPUT_PASSWORD).sendKeys(password);
		click(XP_LOGIN_BUTTON).exec();
	}
}
