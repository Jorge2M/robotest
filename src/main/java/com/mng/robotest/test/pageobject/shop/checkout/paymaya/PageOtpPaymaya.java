package com.mng.robotest.test.pageobject.shop.checkout.paymaya;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State;

public class PageOtpPaymaya extends PageObjTM {
	
	private final static String XPathInputOtp = "//input[@id='otp']";
	private final static String XPathProceedButton = "//button[@id='btn-proceed']";
	
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
