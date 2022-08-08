package com.mng.robotest.test.pageobject.shop.acceptcookies;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.mng.robotest.domains.transversal.PageBase;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State;

public class SectionCookies extends PageBase {

	private static final String XPathAcceptButton = "//button[@id[contains(.,'accept-btn')]]";
	private static final String XPathSetCookiesButton = "//button[@id[contains(.,'pc-btn')]]";
	
	public SectionCookies(WebDriver driver) {
		super(driver);
	}
	
	public boolean isVisible(int maxSeconds) {
		return state(State.Visible, By.xpath(XPathAcceptButton)).wait(maxSeconds).check();
	}
	
	public boolean isInvisible(int maxSeconds) {
		return state(State.Invisible, By.xpath(XPathAcceptButton)).wait(maxSeconds).check();
	}
	
	public void accept() {
		click(By.xpath(XPathAcceptButton)).exec();
		if (!isInvisible(2)) {
			click(By.xpath(XPathAcceptButton)).exec();
		}
	}
	
	public void setCookies() {
		click(By.xpath(XPathSetCookiesButton)).exec();
	}
}
