package com.mng.robotest.tests.domains.compranew.pageobjects;

import com.mng.robotest.tests.domains.base.PageBase;

public class PageCheckoutPayment extends PageBase {

	private static final SecDeliveryMethod sDeliveryMethod = new SecDeliveryMethod();
	private static final SecPaymentMethod sPaymentMethod = new SecPaymentMethod();
	
	public boolean isPage(int seconds) {
		return sPaymentMethod.isVisible(seconds);
	}

}
