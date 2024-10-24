package com.mng.robotest.tests.domains.registro.pageobjects;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

import com.mng.robotest.tests.domains.base.PageBase;

public class PagePoliticaPrivacidad extends PageBase {

	private static final String XP_MICRO = "//div[@class[contains(.,'privacyPolicyPage')]]";
	
	public boolean isPage(int seconds) {
		return state(PRESENT, XP_MICRO).wait(seconds).check();
	}

}
