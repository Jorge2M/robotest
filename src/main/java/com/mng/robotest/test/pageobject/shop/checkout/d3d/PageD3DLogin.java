package com.mng.robotest.test.pageobject.shop.checkout.d3d;

import com.mng.robotest.domains.transversal.PageBase;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.TypeClick;

public class PageD3DLogin extends PageBase {
	
	private static final String XPATH_INPUT_USER = "//input[@id='username']";
	private static final String XPATH_INPUT_PASSWORD = "//input[@id='password']";
	private static final String XPATH_BUTTON_SUBMIT = "//input[@class[contains(.,'button')] and @type='submit']";
	
	public boolean isPageUntil(int maxSecondsToWait) {
		return (titleContainsUntil(driver, "3D Authentication", maxSecondsToWait));
	}
	
	public void inputUserPassword(String user, String password) {
		getElement(XPATH_INPUT_USER).sendKeys(user);
		getElement(XPATH_INPUT_PASSWORD).sendKeys(password);
	}
		   
	public void clickButtonSubmit() {
		click(XPATH_BUTTON_SUBMIT).type(TypeClick.javascript).exec();
	}
}
