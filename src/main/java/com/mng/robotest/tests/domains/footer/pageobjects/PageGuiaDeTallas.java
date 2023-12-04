package com.mng.robotest.tests.domains.footer.pageobjects;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

import com.mng.robotest.tests.domains.base.PageBase;

public class PageGuiaDeTallas extends PageBase implements PageFromFooter {
	
	private static final String XP_FOR_ID_PAGE = "//*[text()[contains(.,'Guía de tallas')]]";
	
	@Override
	public String getName() {
		return "Guía de tallas";
	}
	
	@Override
	public boolean isPageCorrectUntil(int seconds) {
		return state(PRESENT, XP_FOR_ID_PAGE).wait(seconds).check();
	}
}
