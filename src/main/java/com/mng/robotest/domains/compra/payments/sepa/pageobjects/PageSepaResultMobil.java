package com.mng.robotest.domains.compra.payments.sepa.pageobjects;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

import com.mng.robotest.domains.base.PageBase;

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
