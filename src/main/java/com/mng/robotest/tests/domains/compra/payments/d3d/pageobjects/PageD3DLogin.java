package com.mng.robotest.tests.domains.compra.payments.d3d.pageobjects;

import com.mng.robotest.tests.domains.base.PageBase;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.TypeClick.*;

public class PageD3DLogin extends PageBase {
	
	private static final String XP_INPUT_USER = "//input[@id='username']";
	private static final String XP_INPUT_PASSWORD = "//input[@id='password']";
	private static final String XP_BUTTON_SUBMIT = "//input[@class[contains(.,'button')] and @type='submit']";
	
	public boolean isPage(int seconds) {
		return (titleContainsUntil(driver, "3D Authentication", seconds));
	}
	
	public void inputUserPassword(String user, String password) {
		getElement(XP_INPUT_USER).sendKeys(user);
		getElement(XP_INPUT_PASSWORD).sendKeys(password);
	}
		   
	public void clickButtonSubmit() {
		click(XP_BUTTON_SUBMIT).type(JAVASCRIPT).exec();
	}
}
