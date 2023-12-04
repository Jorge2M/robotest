package com.mng.robotest.tests.domains.compra.payments.klarna.pageobjects;

import org.openqa.selenium.WebElement;

import com.mng.robotest.tests.domains.base.PageBase;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

public class ModalInputPhoneKlarna extends PageBase {

	private static final String XP_INPUT_PHONE_NUMBER = "//input[@id='email_or_phone']";
	private static final String XP_BUTTON_CONTINUE = "//span[@id[contains(.,'onContinue')]]";
	private static final String XP_INPUT_OTP = "//input[@id='otp_field']";
	private static final String XP_BUTTON_CONFIRM = "//span[@id[contains(.,'kp-purchase-review-continue-button')]]";
	
	public boolean isModal(int seconds) {
		return state(VISIBLE, XP_INPUT_PHONE_NUMBER).wait(seconds).check();
	}
	
	public void inputPhoneNumber(String phoneNumber) {
		WebElement input = getElement(XP_INPUT_PHONE_NUMBER);
		String actualValue = input.getAttribute("value"); 
		if (actualValue.replace(" ", "").compareTo(phoneNumber)!=0) {
			input.clear();
			input.sendKeys(phoneNumber);
		}
	}
	
	public void clickButtonContinue() {
		click(XP_BUTTON_CONTINUE).exec();
	}
	
	private boolean isInputOtpVisible(int seconds) {
		return state(VISIBLE, XP_INPUT_OTP).wait(seconds).check();
	}
	
	public void inputOTP(String otp) {
		isInputOtpVisible(5);
		getElement(XP_INPUT_OTP).sendKeys(otp);
		isButtonConfirmVisible(5);
	}
	
	private boolean isButtonConfirmVisible(int seconds) {
		return state(VISIBLE, XP_BUTTON_CONFIRM).wait(seconds).check();
	}
	
	public void clickButtonConfirm() {
		click(XP_BUTTON_CONFIRM).exec();
	}
	
}
