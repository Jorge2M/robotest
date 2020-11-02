package com.mng.robotest.test80.mango.test.pageobject.shop.checkout.kcp;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State;

public class ModalHundayCard3 extends ModalHundayCard {

	private final static String XPathInputPassword = "//input[@id='authPassword']";
	private final static String XPathInputCVC = "//input[@id='inputCVC']";
	private final static String XPathAcceptButton = "//div[@id='DIV_BTN']/a[@onclick[contains(.,'doSubmit')]]";
	
	public ModalHundayCard3(WebDriver driver) {
		super(driver);
	}
	
	public boolean isPage(int maxSeconds) {
		gotoIframeModal();
		boolean result = state(State.Visible, By.xpath(XPathInputCVC), driver).wait(maxSeconds).check();
		leaveIframe();
		return result;
	}
	
	public void inputPassword(String password) {
		gotoIframeModal();
		driver.findElement(By.xpath(XPathInputPassword)).sendKeys(password);
		leaveIframe();
	}
	
	public void inputCVC(String cvc) {
		gotoIframeModal();
		driver.findElement(By.xpath(XPathInputCVC)).sendKeys(cvc);
		leaveIframe();
	}
	
	public void accept() {
		gotoIframeModal();
		click(By.xpath(XPathAcceptButton)).exec();
		leaveIframe();
	}
}
