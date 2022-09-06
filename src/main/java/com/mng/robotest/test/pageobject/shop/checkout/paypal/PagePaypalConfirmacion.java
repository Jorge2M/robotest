package com.mng.robotest.test.pageobject.shop.checkout.paypal;

import com.mng.robotest.domains.transversal.PageBase;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

public class PagePaypalConfirmacion extends PageBase {

	private static final String XPATH_REVIEW_PAGE = "//div[@class[contains(.,'review-page')]]";
	private static final String XPATH_CONTINUE_BUTTON = "//input[@id='confirmButtonTop']";

	public boolean isPageUntil(int maxSeconds) {
		return state(Visible, XPATH_REVIEW_PAGE).wait(maxSeconds).check();
	}

	public void clickContinuarButton() {
		click(XPATH_CONTINUE_BUTTON).exec();
	}
}
