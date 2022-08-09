package com.mng.robotest.test.pageobject.shop.checkout.dotpay;

import org.openqa.selenium.By;
import com.mng.robotest.domains.transversal.PageBase;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;


public class PageDotpayPaymentChannel extends PageBase {

	private static final String XPATH_SECTION_PAYMENT_CHANNELS = "//section[@id='payment-channels']";
	private static final String XPATH_BLOCK_INPUT_DATA = "//div[@id='personal-data-form']";
	private static final String XPATH_INPUT_FIRST_NAME = XPATH_BLOCK_INPUT_DATA + "//input[@name='dp-firstname']";
	private static final String XPATH_INPUT_LAST_NAME = XPATH_BLOCK_INPUT_DATA + "//input[@name='dp-lastname']";
	private static final String XPATH_BUTTON_CONFIRMAR = XPATH_BLOCK_INPUT_DATA + "//button[@type='submit' and @id='payment-form-submit-dp']";
	
	public String getXPathPaymentChannelLink(int numPayment) {
		return "//img[@id='channel_image_" + numPayment + "']";
	}
	
	public boolean isPage() {
		return (state(Present, By.xpath(XPATH_SECTION_PAYMENT_CHANNELS)).check());
	}

	public void clickPayment(int numPayment) {
		String xpathPayment = getXPathPaymentChannelLink(numPayment);
		click(By.xpath(xpathPayment)).exec();
	}

	public void sendInputNombre(String firstName, String lastName) {
		driver.findElement(By.xpath(XPATH_INPUT_FIRST_NAME)).sendKeys(firstName);
		driver.findElement(By.xpath(XPATH_INPUT_LAST_NAME)).sendKeys(lastName);
	}

	public void clickButtonConfirm() {
		click(By.xpath(XPATH_BUTTON_CONFIRMAR)).exec();
	}

	public boolean isVisibleBlockInputDataUntil(int maxSeconds) {
		return (state(Visible, By.xpath(XPATH_BLOCK_INPUT_DATA)).wait(maxSeconds).check());
	}
}
