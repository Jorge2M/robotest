package com.mng.robotest.tests.domains.compra.payments.sepa.pageobjects;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

import com.mng.robotest.tests.domains.base.PageBase;

public class PageSepaResultMobil extends PageBase {

	private static final String XP_BUTTON_PAY = "//input[@type='submit' and @id='mainSubmit']";
	private static final String XP_STAGE_3HEADER = "//h2[@id='stageheader' and text()[contains(.,'3:')]]";

	public boolean isPage() {
		return state(PRESENT, XP_STAGE_3HEADER).check();
	}

	public void clickButtonPay() {
		click(XP_BUTTON_PAY).exec();
	}
}
