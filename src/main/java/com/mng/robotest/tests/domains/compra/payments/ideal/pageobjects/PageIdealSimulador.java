package com.mng.robotest.tests.domains.compra.payments.ideal.pageobjects;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

import com.mng.robotest.tests.domains.base.PageBase;

public class PageIdealSimulador extends PageBase {

	private static final String XP_CONTINUE_BUTTON = "//input[@type='submit' and @class='btnLink']";

	public boolean isPage() {
		return state(VISIBLE, "//h3[text()[contains(.,'iDEAL Issuer Simulation')]]").check();
	}

	public void clickButtonContinue() {
		click(XP_CONTINUE_BUTTON).exec();
	}
}
