package com.mng.robotest.test80.mango.test.pageobject.shop.checkout.kcp;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State;


public class ModalHundayCard4 extends ModalHundayCard {

	private final static String XPathSubmitPaymentButton = "//button[@id='cardNext']";
	
	public ModalHundayCard4(WebDriver driver) {
		super(driver);
	}
	
	public boolean isPage(int maxSeconds) {
		gotoIframeModal();
		boolean result = state(State.Visible, By.xpath(XPathSubmitPaymentButton)).wait(maxSeconds).check();
		leaveIframe();
		return result;
	}
	
	public void submitPayment() {
		gotoIframeModal();
		click(By.xpath(XPathSubmitPaymentButton)).exec();
		leaveIframe();
	}
	
}
