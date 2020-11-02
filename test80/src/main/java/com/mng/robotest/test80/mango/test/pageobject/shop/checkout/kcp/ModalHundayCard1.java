package com.mng.robotest.test80.mango.test.pageobject.shop.checkout.kcp;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State;

public class ModalHundayCard1 extends ModalHundayCard {

	private static final String XPathIconoHunday = "//img[@src[contains(.,'logo_hcard')]]";
	private static final String XPathPagoGeneralButton = "//p[text()[contains(.,'일반 결제')]]/../a";
	
	public ModalHundayCard1(WebDriver driver) {
		super(driver);
	}
	
	public boolean isPage(int maxSeconds) {
		gotoIframeModal();
		boolean result = state(State.Visible, By.xpath(XPathIconoHunday)).wait(maxSeconds).check();
		leaveIframe();
		return result;
	}
	
	public void clickPagoGeneral() {
		gotoIframeModal();
		click(By.xpath(XPathPagoGeneralButton)).exec();
		leaveIframe();
	}
	
}
