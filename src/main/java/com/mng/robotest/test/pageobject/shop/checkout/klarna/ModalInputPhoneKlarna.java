package com.mng.robotest.test.pageobject.shop.checkout.klarna;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State;

public class ModalInputPhoneKlarna extends PageObjTM {

	private static final String XPathInputPhoneNumber = "//input[@id='email_or_phone']";
	//private static final String XPathButtonContinue = "//span[@id[contains(.,'btn-continue')]]";
	private static final String XPathButtonContinue = "//span[@id[contains(.,'onContinue')]]";
	private static final String XPathInputOTP = "//input[@id='otp_field']";
	private static final String XPathButtonConfirm = "//span[@id[contains(.,'kp-purchase-review-continue-button')]]";
	
	public ModalInputPhoneKlarna(WebDriver driver) {
		super(driver);
	}
	
	public boolean isModal(int maxSeconds) {
		return state(State.Visible, By.xpath(XPathInputPhoneNumber)).wait(maxSeconds).check();
	}
	
	public void inputPhoneNumber(String phoneNumber) {
		WebElement input = driver.findElement(By.xpath(XPathInputPhoneNumber));
		String actualValue = input.getAttribute("value"); 
		if (actualValue.replace(" ", "").compareTo(phoneNumber)!=0) {
			input.clear();
			input.sendKeys(phoneNumber);
		}
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
