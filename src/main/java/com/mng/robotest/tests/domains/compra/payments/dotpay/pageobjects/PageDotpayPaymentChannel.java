package com.mng.robotest.tests.domains.compra.payments.dotpay.pageobjects;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

import com.mng.robotest.tests.domains.base.PageBase;


public class PageDotpayPaymentChannel extends PageBase {

	private static final String XP_SECTION_PAYMENT_CHANNELS = "//section[@id='payment-channels']";
	private static final String XP_BLOCK_INPUT_DATA = "//div[@id='personal-data-form']";
	private static final String XP_INPUT_FIRST_NAME = XP_BLOCK_INPUT_DATA + "//input[@name='dp-firstname']";
	private static final String XP_INPUT_LAST_NAME = XP_BLOCK_INPUT_DATA + "//input[@name='dp-lastname']";
	private static final String XP_BUTTON_CONFIRMAR = XP_BLOCK_INPUT_DATA + "//button[@type='submit' and @id='payment-form-submit-dp']";
	
	public String getXPathPaymentChannelLink(int numPayment) {
		return "//img[@id='channel_image_" + numPayment + "']";
	}
	
	public boolean isPage() {
		return state(PRESENT, XP_SECTION_PAYMENT_CHANNELS).check();
	}

	public void clickPayment(int numPayment) {
		click(getXPathPaymentChannelLink(numPayment)).exec();
	}

	public void sendInputNombre(String firstName, String lastName) {
		getElement(XP_INPUT_FIRST_NAME).sendKeys(firstName);
		getElement(XP_INPUT_LAST_NAME).sendKeys(lastName);
	}

	public void clickButtonConfirm() {
		click(XP_BUTTON_CONFIRMAR).exec();
	}

	public boolean isVisibleBlockInputDataUntil(int seconds) {
		return state(VISIBLE, XP_BLOCK_INPUT_DATA).wait(seconds).check();
	}
}
