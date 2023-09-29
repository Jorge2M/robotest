package com.mng.robotest.tests.domains.footer.pageobjects;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

import com.mng.robotest.tests.domains.base.PageBase;

public class PageEnvio extends PageBase implements PageFromFooter {
	
	private static final String XPATH_FOR_ID_PAGE = "//*[text()[contains(.,'Métodos y coste del envío')]]";
	
	@Override
	public String getName() {
		return "Envíos";
	}
	
	@Override
	public boolean isPageCorrectUntil(int seconds) {
		return state(Present, XPATH_FOR_ID_PAGE).wait(seconds).check();
	}
}
