package com.mng.robotest.domains.registro.pageobjects;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

import com.mng.robotest.domains.base.PageBase;

public class PagePoliticaPrivacidad extends PageBase {

	private static final String XPATH_MICRO = "//micro-frontend[@id='legalPolicy']";
	
	public boolean isPageUntil(int seconds) {
		return state(Present, XPATH_MICRO).wait(seconds).check();
	}

}
