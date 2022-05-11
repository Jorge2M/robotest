package com.mng.robotest.test.pageobject.shop.checkout.klarna;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State;

public class ModalInputPhoneKlarna extends PageObjTM {

	private final static String XPathInputPhoneNumber = "//input[@id='email_or_phone']";
	private final static String XPathButtonContinue = "//span[@id[contains(.,'btn-continue')]]";
	private final static String XPathInputOTP = "//input[@id='otp_field']";
	private final static String XPathButtonConfirm = "//span[@id[contains(.,'kp-purchase-review-continue-button')]]";
	
	public ModalInputPhoneKlarna(WebDriver driver) {
		super(driver);
	}
	
	public boolean isModal(int maxSeconds) {
		return state(State.Visible, By.xpath(XPathInputPhoneNumber)).check();
	}
	
	public void inputPhoneNumber(String phoneNumber) {
		driver.findElement(By.xpath(XPathInputPhoneNumber)).sendKeys(phoneNumber);
	}
	
	public void clickButtonContinue() {
		click(By.xpath(XPathButtonContinue)).exec();
	}
	
	private boolean isInputOtpVisible(int maxSeconds) {
		return state(State.Visible, By.xpath(XPathInputOTP)).wait(maxSeconds).check();
	}
	
	public void inputOTP(String OTP) {
		isInputOtpVisible(5);
		driver.findElement(By.xpath(XPathInputOTP)).sendKeys(OTP);
		isButtonConfirmVisible(5);
	}
	
	private boolean isButtonConfirmVisible(int maxSeconds) {
		return state(State.Visible, By.xpath(XPathButtonConfirm)).wait(maxSeconds).check();
	}
	
	public void clickButtonConfirm() {
		click(By.xpath(XPathButtonConfirm)).exec();
	}
	
}
