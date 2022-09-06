package com.mng.robotest.test.pageobject.shop.checkout.paymaya;

import com.mng.robotest.domains.transversal.PageBase;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State;

public class PageResultPaymaya extends PageBase {

	private static final String XPATH_CONFIRM_BUTTON = "//input[@id='confirm-button']";
	
	public boolean isPage() {
		return state(State.Visible, XPATH_CONFIRM_BUTTON).check();
	}
	
	public void confirmPayment() {
		click(XPATH_CONFIRM_BUTTON).exec();
	}
	
}
