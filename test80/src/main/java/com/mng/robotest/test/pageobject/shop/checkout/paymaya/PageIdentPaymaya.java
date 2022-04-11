package com.mng.robotest.test.pageobject.shop.checkout.paymaya;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State;

public class PageIdentPaymaya extends PageObjTM {

	private final static String XPathWrapperLogin = "//div[@class[contains(.,'login-form-index')]]";
	private final static String XPathInputUser = "//input[@id='identityValue']";
	private final static String XPathInputPassword = "//input[@id='password']";
	private final static String XPathLoginButton = "//input[@class[contains(.,'login-btn')]]";
	
	public PageIdentPaymaya(WebDriver driver) {
		super(driver);
	}
	
	public boolean isPage() {
		return state(State.Visible, By.xpath(XPathWrapperLogin)).check();
	}
	
	public void login(String user, String password) {
		driver.findElement(By.xpath(XPathInputUser)).sendKeys(user);
		driver.findElement(By.xpath(XPathInputPassword)).sendKeys(password);
		click(By.xpath(XPathLoginButton)).exec();
	}
}