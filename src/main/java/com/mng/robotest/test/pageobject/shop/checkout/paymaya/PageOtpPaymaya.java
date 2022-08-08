package com.mng.robotest.test.pageobject.shop.checkout.paymaya;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.mng.robotest.domains.transversal.PageBase;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State;

public class PageOtpPaymaya extends PageBase {
	
	private static final String XPathInputOtp = "//input[@id='otp']";
	private static final String XPathProceedButton = "//button[@id='btn-proceed']";
	
	public PageOtpPaymaya(WebDriver driver) {
		super(driver);
	}
	
	public boolean isPage() {
		return state(State.Visible, By.xpath(XPathInputOtp)).check();
	}
	
	public void proceed(String otp) {
		driver.findElement(By.xpath(XPathInputOtp)).sendKeys(otp);
		click(By.xpath(XPathProceedButton)).exec();
	}
}
