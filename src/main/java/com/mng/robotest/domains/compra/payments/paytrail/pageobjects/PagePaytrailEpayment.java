package com.mng.robotest.domains.compra.payments.paytrail.pageobjects;

import com.mng.robotest.domains.base.PageBase;

public class PagePaytrailEpayment extends PageBase {

	private static final String XPATH_FORM_CODE_CARD = "//form[@action[contains(.,'AUTH=OLD')]]";
	private static final String XPATH_BUTTON_OK_FORM_CODE_CARD = XPATH_FORM_CODE_CARD + "//input[@class='button' and @name='Ok']";
	
	public void clickOkFromCodeCard() {
		click(XPATH_BUTTON_OK_FORM_CODE_CARD).exec();
	}
}
