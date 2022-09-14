package com.mng.robotest.domains.footer.pageobjects;

import com.mng.robotest.domains.transversal.PageBase;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

public class PageFormasDePago extends PageBase implements PageFromFooter {
	
	private static final String XPATH_FOR_ID_PAGE = "//*[text()[contains(.,'Métodos de pago')]]";
	
	@Override
	public String getName() {
		return "Formas de pago";
	}
	
	@Override
	public boolean isPageCorrectUntil(int seconds) {
		return state(Present, XPATH_FOR_ID_PAGE).wait(seconds).check();
	}
}
