package com.mng.robotest.test80.mango.test.pageobject.shop.checkout.paymaya;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State;

public class PageResultPaymaya extends PageObjTM {

	private final static String XPathConfirmButton = "//input[@id='confirm-button']";
	
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
