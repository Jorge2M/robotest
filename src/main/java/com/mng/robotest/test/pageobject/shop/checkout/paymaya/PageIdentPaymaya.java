package com.mng.robotest.test.pageobject.shop.checkout.paymaya;

import org.openqa.selenium.By;

import com.mng.robotest.domains.transversal.PageBase;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State;


public class PageIdentPaymaya extends PageBase {

	private static final String XPATH_WRAPPER_LOGIN = "//div[@class[contains(.,'login-form-index')]]";
	private static final String XPATH_INPUT_USER = "//input[@id='identityValue']";
	private static final String XPATH_INPUT_PASSWORD = "//input[@id='password']";
	private static final String XPATH_LOGIN_BUTTON = "//input[@class[contains(.,'login-btn')]]";
	
	public boolean isPage() {
		return state(State.Visible, By.xpath(XPATH_WRAPPER_LOGIN)).check();
	}
	
	public void login(String user, String password) {
		driver.findElement(By.xpath(XPATH_INPUT_USER)).sendKeys(user);
		driver.findElement(By.xpath(XPATH_INPUT_PASSWORD)).sendKeys(password);
		click(By.xpath(XPATH_LOGIN_BUTTON)).exec();
	}
}
