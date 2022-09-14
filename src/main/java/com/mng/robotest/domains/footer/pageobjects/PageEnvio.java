package com.mng.robotest.domains.footer.pageobjects;

import com.mng.robotest.domains.transversal.PageBase;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

public class PageEnvio extends PageBase implements PageFromFooter {
	
	private final static String XPATH_FOR_ID_PAGE = "//*[text()[contains(.,'Métodos y coste del envío')]]";
	
	@Override
	public String getName() {
		return "Envíos";
	}
	
	@Override
	public boolean isPageCorrectUntil(int seconds) {
		return state(Present, XPATH_FOR_ID_PAGE).wait(seconds).check();
	}
}
