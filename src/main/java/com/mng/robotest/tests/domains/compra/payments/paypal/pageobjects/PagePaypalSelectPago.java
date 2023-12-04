package com.mng.robotest.tests.domains.compra.payments.paypal.pageobjects;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

import com.mng.robotest.tests.domains.base.PageBase;

public class PagePaypalSelectPago extends PageBase {

	private static final String XP_CONTINUE_BUTTON = "//*[@data-testid='submit-button-initial']";
	private static final String XP_MET_PAGOS = "//section[@data-testid='pay-with']";

	public boolean isPage(int seconds) {
		return state(VISIBLE, XP_MET_PAGOS).wait(seconds).check();
	}

	public void clickContinuarButton() {
		click(XP_CONTINUE_BUTTON).exec();
	}
}
