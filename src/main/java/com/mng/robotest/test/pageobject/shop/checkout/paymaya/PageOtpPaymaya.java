package com.mng.robotest.test.pageobject.shop.checkout.paymaya;

import org.openqa.selenium.By;

import com.mng.robotest.domains.transversal.PageBase;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State;


public class PageOtpPaymaya extends PageBase {
	
	private static final String XPATH_INPUT_OTP = "//input[@id='otp']";
	private static final String XPATH_PROCEED_BUTTON = "//button[@id='btn-proceed']";
	
	public boolean isPage() {
		return state(State.Visible, By.xpath(XPATH_INPUT_OTP)).check();
	}
	
	public void proceed(String otp) {
		driver.findElement(By.xpath(XPATH_INPUT_OTP)).sendKeys(otp);
		click(By.xpath(XPATH_PROCEED_BUTTON)).exec();
	}
}
