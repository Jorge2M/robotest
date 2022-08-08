package com.mng.robotest.test.pageobject.shop.checkout.paymaya;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.mng.robotest.domains.transversal.PageBase;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State;

public class PageResultPaymaya extends PageBase {

	private static final String XPathConfirmButton = "//input[@id='confirm-button']";
	
	public PageResultPaymaya(WebDriver driver) {
		super(driver);
	}
	
	public boolean isPage() {
		return state(State.Visible, By.xpath(XPathConfirmButton)).check();
	}
	
	public void confirmPayment() {
		click(By.xpath(XPathConfirmButton)).exec();
	}
	
}
