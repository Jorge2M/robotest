package com.mng.robotest.tests.domains.compranew.pageobjects;

import com.mng.robotest.tests.domains.base.PageBase;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

public class SecPaymentMethod extends PageBase {

	private static final String XP_PAYMENT_METHOD_BLOCK = "//*[@data-testid='checkout.payment.paymentMethodsList']";
	
	public boolean isVisible(int seconds) {
		return state(VISIBLE, XP_PAYMENT_METHOD_BLOCK).wait(seconds).check();
	}

}
