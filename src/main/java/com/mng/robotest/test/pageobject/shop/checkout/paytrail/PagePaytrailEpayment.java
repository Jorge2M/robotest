package com.mng.robotest.test.pageobject.shop.checkout.paytrail;

import com.mng.robotest.domains.transversal.PageBase;

public class PagePaytrailEpayment extends PageBase {

	private static final String XPATH_FORM_CODE_CARD = "//form[@action[contains(.,'AUTH=OLD')]]";
	private static final String XPATH_BUTTON_OK_FORM_CODE_CARD = XPATH_FORM_CODE_CARD + "//input[@class='button' and @name='Ok']";
	
	public void clickOkFromCodeCard() {
		click(XPATH_BUTTON_OK_FORM_CODE_CARD).exec();
	}
}
