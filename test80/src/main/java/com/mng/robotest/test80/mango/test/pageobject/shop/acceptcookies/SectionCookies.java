package com.mng.robotest.test80.mango.test.pageobject.shop.acceptcookies;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State;

public class SectionCookies extends PageObjTM {

	private static final String XPathAcceptButton = "//button[@id[contains(.,'accept-btn')]]";
	private static final String XPathSetCookiesButton = "//button[@id[contains(.,'pc-btn')]]";
	
	public SectionCookies(WebDriver driver) {
		super(driver);
	}
	
	public boolean isVisible() {
		return state(State.Visible, By.xpath(XPathAcceptButton)).check();
	}
	
	public void accept() {
		click(By.xpath(XPathAcceptButton)).exec();
	}
	
	public void setCookies() {
		click(By.xpath(XPathSetCookiesButton)).exec();
	}
}
