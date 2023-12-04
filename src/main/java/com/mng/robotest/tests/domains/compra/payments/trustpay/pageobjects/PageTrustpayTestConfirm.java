package com.mng.robotest.tests.domains.compra.payments.trustpay.pageobjects;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

import com.mng.robotest.tests.domains.base.PageBase;

public class PageTrustpayTestConfirm extends PageBase {
	
	public enum typeButtons { OK, ANNOUNCED, FAIL, PENDING }
	private static final String XP_BUTTON_OK = "//input[@id='btnOK']";
	private static final String XP_BUTTON_ANNOUNCED = "//input[@id='btnANNOUNCED']"; 
	private static final String XP_BUTTON_FAIL = "//input[@id='btnFAIL']";
	private static final String XP_BUTTON_PENDING = "//input[@id='btnOUT']";
	
	public static String getXPathButton(typeButtons typeButton) {
		switch (typeButton) {
		case OK:
			return XP_BUTTON_OK;
		case ANNOUNCED:
			return XP_BUTTON_ANNOUNCED;
		case FAIL:
			return XP_BUTTON_FAIL;
		case PENDING:
			return XP_BUTTON_PENDING;
		default:
			return "";
		}		
	}
	
	public boolean isPresentButton(typeButtons typeButton) {
		String xpathButton = getXPathButton(typeButton);
		return state(PRESENT, xpathButton).check();
	}

	public void clickButton(typeButtons typeButton) {
		click(getXPathButton(typeButton)).exec();
	}
}
