package com.mng.robotest.tests.domains.compra.payments.paymaya.pageobjects;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

import com.mng.robotest.tests.domains.base.PageBase;

public class PageOtpPaymaya extends PageBase {
	
	private static final String XP_INPUT_OTP = "//input[@id='otp']";
	private static final String XP_PROCEED_BUTTON = "//button[@id='btn-proceed']";
	
	public boolean isPage() {
		return state(VISIBLE, XP_INPUT_OTP).check();
	}
	
	public void proceed(String otp) {
		getElement(XP_INPUT_OTP).sendKeys(otp);
		click(XP_PROCEED_BUTTON).exec();
	}
}
