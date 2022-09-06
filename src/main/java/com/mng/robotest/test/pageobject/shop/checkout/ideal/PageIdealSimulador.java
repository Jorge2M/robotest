package com.mng.robotest.test.pageobject.shop.checkout.ideal;

import com.mng.robotest.domains.transversal.PageBase;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

public class PageIdealSimulador extends PageBase {

	private static final String XPATH_CONTINUE_BUTTON = "//input[@type='submit' and @class='btnLink']";

	public boolean isPage() {
		return state(Visible, "//h3[text()[contains(.,'iDEAL Issuer Simulation')]]").check();
	}

	public void clickButtonContinue() {
		click(XPATH_CONTINUE_BUTTON).exec();
	}
}
