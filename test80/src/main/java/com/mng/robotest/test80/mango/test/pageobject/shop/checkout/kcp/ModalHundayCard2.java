package com.mng.robotest.test80.mango.test.pageobject.shop.checkout.kcp;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State;

public class ModalHundayCard2 extends ModalHundayCard {

	private final static String XPathDataCard = "//div[@class='visual-card-back']";
	private final static String XPathAcceptButton = "//div[@id='DIV_BTN']/a[@onclick[contains(.,'doSubmit')]]";
	
	public ModalHundayCard2(WebDriver driver) {
		super(driver);
	}
	
	public boolean isPage(int maxSeconds) {
		gotoIframeModal();
		boolean result = state(State.Visible, By.xpath(XPathDataCard)).wait(maxSeconds).check();
		leaveIframe();
		return result;
	}
	
	public void inputCardNumber(String cardNumber16digits) {
		gotoIframeModal();
		driver.findElement(By.id("cardno1")).sendKeys(cardNumber16digits.substring(0,3));
		driver.findElement(By.id("cardno2")).sendKeys(cardNumber16digits.substring(4,7));
		driver.findElement(By.id("cardno3")).sendKeys(cardNumber16digits.substring(8,11));
		driver.findElement(By.id("cardno4")).sendKeys(cardNumber16digits.substring(12,15));
		leaveIframe();
	}
	
	public void accept() {
		gotoIframeModal();
		click(By.xpath(XPathAcceptButton)).exec();
		leaveIframe();
	}
	
}
