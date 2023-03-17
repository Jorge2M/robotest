package com.mng.robotest.domains.compra.payments.paypal.pageobjects;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

import com.mng.robotest.domains.base.PageBase;

public class PagePaypalConfirmacion extends PageBase {

	private static final String XPATH_REVIEW_PAGE = "//div[@class[contains(.,'review-page')]]";
	private static final String XPATH_CONTINUE_BUTTON = "//input[@id='confirmButtonTop']";

	public boolean isPageUntil(int seconds) {
		return state(Visible, XPATH_REVIEW_PAGE).wait(seconds).check();
	}

	public void clickContinuarButton() {
		click(XPATH_CONTINUE_BUTTON).exec();
	}
}
