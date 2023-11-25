package com.mng.robotest.tests.domains.compra.payments.paytrail.pageobjects;

import com.mng.robotest.tests.domains.base.PageBase;

public class PagePaytrailEpayment extends PageBase {

	private static final String XP_FORM_CODE_CARD = "//form[@action[contains(.,'AUTH=OLD')]]";
	private static final String XP_BUTTON_OK_FORM_CODE_CARD = XP_FORM_CODE_CARD + "//input[@class='button' and @name='Ok']";
	
	public void clickOkFromCodeCard() {
		click(XP_BUTTON_OK_FORM_CODE_CARD).exec();
	}
}
