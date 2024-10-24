package com.mng.robotest.tests.domains.compra.payments.yandex.pageobjects;

import org.openqa.selenium.WebElement;

import com.mng.robotest.tests.domains.base.PageBase;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

public class PageYandexPayingByCode extends PageBase {
	
	private static final String XP_DIV_PAYMENT_CODE = "//div[@class[contains(.,'payment-code-wrapper')]]";
	private static final String XP_PAYMENT_CODE = XP_DIV_PAYMENT_CODE + "//div[@class[contains(.,'payment-code-value')]]";
	private static final String XP_BUTTON_BACK_TO_MANGO_DESKTOP = "//div[@class[contains(.,'backward-button')]]//a";
	private static final String XP_BUTTON_BACK_TO_MANGO_MOVIL = "//div[@class[contains(.,'back-link')]]//a";
	
	private String getXPathBackToMangoLink() {
		switch (channel) {
		case desktop:
			return XP_BUTTON_BACK_TO_MANGO_DESKTOP;
		default:
		case mobile:
			return XP_BUTTON_BACK_TO_MANGO_MOVIL;
		}		
	}
	
	private String getXPathDataUnitThatContains(String value) {
		return ("//div[@class[contains(.,'data-unit__base')] and text()[contains(.,'" + value + "')]]");
	}
	
	public boolean isPage() {
		return state(VISIBLE, XP_DIV_PAYMENT_CODE).check();
	}
		
	public boolean isVisibleEmail(String emailUsr) {
		String xpathEmail = getXPathDataUnitThatContains(emailUsr);
		return state(VISIBLE, xpathEmail).check();
	}
	
	public boolean isPresentEmail(String emailUsr) {
		String xpathEmail = getXPathDataUnitThatContains(emailUsr);
		return state(PRESENT, xpathEmail).check();
	}	

	public void clickBackToMango() {
		click(getXPathBackToMangoLink()).exec();
	}

	public boolean isVisiblePaymentCode() {
		return state(VISIBLE, XP_PAYMENT_CODE).check();
	}
	
	public String getPaymentCode() {
		WebElement paymentCode = getElement(XP_PAYMENT_CODE);
		if (paymentCode==null) {
			return "";
		}
		return getElement(XP_PAYMENT_CODE).getText();
	}
}
