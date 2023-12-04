package com.mng.robotest.tests.domains.compra.payments.paypal.pageobjects;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

import com.mng.robotest.tests.domains.base.PageBase;

public class PagePaypalConfirmacion extends PageBase {

	private static final String XP_REVIEW_PAGE = "//div[@class[contains(.,'review-page')]]";
	private static final String XP_CONTINUE_BUTTON = "//input[@id='confirmButtonTop']";

	public boolean isPage(int seconds) {
		return state(VISIBLE, XP_REVIEW_PAGE).wait(seconds).check();
	}

	public void clickContinuarButton() {
		click(XP_CONTINUE_BUTTON).exec();
	}
}
