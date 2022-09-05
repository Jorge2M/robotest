package com.mng.robotest.test.pageobject.shop.footer;

import com.mng.robotest.domains.transversal.PageBase;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

public class PageGuiaDeTallas extends PageBase implements PageFromFooter {
	
	private static final String XPATH_FOR_ID_PAGE = "//*[text()[contains(.,'Guía de tallas')]]";
	
	@Override
	public String getName() {
		return "Guía de tallas";
	}
	
	@Override
	public boolean isPageCorrectUntil(int maxSeconds) {
		return state(Present, XPATH_FOR_ID_PAGE).wait(maxSeconds).check();
	}
}
