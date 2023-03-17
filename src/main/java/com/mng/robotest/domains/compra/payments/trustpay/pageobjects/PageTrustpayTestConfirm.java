package com.mng.robotest.domains.compra.payments.trustpay.pageobjects;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

import com.mng.robotest.domains.base.PageBase;

public class PageTrustpayTestConfirm extends PageBase {
	
	public enum typeButtons { OK, ANNOUNCED, FAIL, PENDING }
	private static final String XPATH_BUTTON_OK = "//input[@id='btnOK']";
	private static final String XPATH_BUTTON_ANNOUNCED = "//input[@id='btnANNOUNCED']"; 
	private static final String XPATH_BUTTON_FAIL = "//input[@id='btnFAIL']";
	private static final String XPATH_BUTTON_PENDING = "//input[@id='btnOUT']";
	
	public static String getXPathButton(typeButtons typeButton) {
		switch (typeButton) {
		case OK:
			return XPATH_BUTTON_OK;
		case ANNOUNCED:
			return XPATH_BUTTON_ANNOUNCED;
		case FAIL:
			return XPATH_BUTTON_FAIL;
		case PENDING:
			return XPATH_BUTTON_PENDING;
		default:
			return "";
		}		
	}
	
	public boolean isPresentButton(typeButtons typeButton) {
		String xpathButton = getXPathButton(typeButton);
		return state(Present, xpathButton).check();
	}

	public void clickButton(typeButtons typeButton) {
		click(getXPathButton(typeButton)).exec();
	}
}
