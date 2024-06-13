package com.mng.robotest.tests.domains.registro.pageobjects;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

import com.mng.robotest.tests.domains.base.PageBase;

public class PageCondicionesVenta extends PageBase {

	private static final String XP_PAGE = "//div[@class[contains(.,'termsAndConditionsPage')]]";
	
	public boolean isPage(int seconds) {
		return state(PRESENT, XP_PAGE).wait(seconds).check();
	}

}
