package com.mng.robotest.domains.compra.payments.klarna.pageobjects;

import org.openqa.selenium.WebElement;

import com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State;
import com.mng.robotest.domains.transversal.PageBase;

public class ModalInputPhoneKlarna extends PageBase {

	private static final String XPATH_INPUT_PHONE_NUMBER = "//input[@id='email_or_phone']";
	private static final String XPATH_BUTTON_CONTINUE = "//span[@id[contains(.,'onContinue')]]";
	private static final String XPATH_INPUT_OTP = "//input[@id='otp_field']";
	private static final String XPATH_BUTTON_CONFIRM = "//span[@id[contains(.,'kp-purchase-review-continue-button')]]";
	
	public boolean isModal(int seconds) {
		return state(State.Visible, XPATH_INPUT_PHONE_NUMBER).wait(seconds).check();
	}
	
	public void inputPhoneNumber(String phoneNumber) {
		WebElement input = getElement(XPATH_INPUT_PHONE_NUMBER);
		String actualValue = input.getAttribute("value"); 
		if (actualValue.replace(" ", "").compareTo(phoneNumber)!=0) {
			input.clear();
			input.sendKeys(phoneNumber);
		}
	}
	
	public void clickButtonContinue() {
		click(XPATH_BUTTON_CONTINUE).exec();
	}
	
	private boolean isInputOtpVisible(int seconds) {
		return state(State.Visible, XPATH_INPUT_OTP).wait(seconds).check();
	}
	
	public void inputOTP(String OTP) {
		isInputOtpVisible(5);
		getElement(XPATH_INPUT_OTP).sendKeys(OTP);
		isButtonConfirmVisible(5);
	}
	
	private boolean isButtonConfirmVisible(int seconds) {
		return state(State.Visible, XPATH_BUTTON_CONFIRM).wait(seconds).check();
	}
	
	public void clickButtonConfirm() {
		click(XPATH_BUTTON_CONFIRM).exec();
	}
	
}
