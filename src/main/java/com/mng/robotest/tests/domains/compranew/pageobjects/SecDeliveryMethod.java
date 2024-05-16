package com.mng.robotest.tests.domains.compranew.pageobjects;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.VISIBLE;

import com.mng.robotest.tests.domains.base.PageBase;

public class SecDeliveryMethod extends PageBase {

	private static final String XP_DELIVERY_METHOD_BLOCK = "//*[@data-testid='checkout.step2.deliveryAddress']";
	
	public boolean isVisible() {
		return state(VISIBLE, XP_DELIVERY_METHOD_BLOCK).check();
	}
	
}
