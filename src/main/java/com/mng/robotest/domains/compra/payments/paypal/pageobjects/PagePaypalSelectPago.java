package com.mng.robotest.domains.compra.payments.paypal.pageobjects;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

import com.mng.robotest.domains.transversal.PageBase;

public class PagePaypalSelectPago extends PageBase {

	private static final String XPATH_CONTINUE_BUTTON = "//*[@data-testid='submit-button-initial']";
	private static final String XPATH_MET_PAGOS = "//section[@data-testid='pay-with']";

	public boolean isPageUntil(int seconds) {
		return state(Visible, XPATH_MET_PAGOS).wait(seconds).check();
	}

	public void clickContinuarButton() {
		click(XPATH_CONTINUE_BUTTON).exec();
	}
}
