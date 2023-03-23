package com.mng.robotest.domains.compra.payments.paymaya.pageobjects;

import com.mng.robotest.domains.base.PageBase;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

public class PageResultPaymaya extends PageBase {

	private static final String XPATH_CONFIRM_BUTTON = "//input[@id='confirm-button']";
	
	public boolean isPage() {
		return state(Visible, XPATH_CONFIRM_BUTTON).check();
	}
	
	public void confirmPayment() {
		click(XPATH_CONFIRM_BUTTON).exec();
	}
	
}
