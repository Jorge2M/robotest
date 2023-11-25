package com.mng.robotest.tests.domains.compra.payments.paymaya.pageobjects;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

import com.mng.robotest.tests.domains.base.PageBase;

public class PageResultPaymaya extends PageBase {

	private static final String XP_CONFIRM_BUTTON = "//input[@id='confirm-button']";
	
	public boolean isPage() {
		return state(Visible, XP_CONFIRM_BUTTON).check();
	}
	
	public void confirmPayment() {
		click(XP_CONFIRM_BUTTON).exec();
	}
	
}
