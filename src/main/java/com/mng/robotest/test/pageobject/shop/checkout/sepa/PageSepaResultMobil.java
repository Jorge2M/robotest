package com.mng.robotest.test.pageobject.shop.checkout.sepa;

import com.mng.robotest.domains.transversal.PageBase;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

public class PageSepaResultMobil extends PageBase {

	private static final String XPATH_BUTTON_PAY = "//input[@type='submit' and @id='mainSubmit']";
	private static final String XPATH_STAGE_3HEADER = "//h2[@id='stageheader' and text()[contains(.,'3:')]]";

	public boolean isPage() {
		return state(Present, XPATH_STAGE_3HEADER).check();
	}

	public void clickButtonPay() {
		click(XPATH_BUTTON_PAY).exec();
	}
}
