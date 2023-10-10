package com.mng.robotest.tests.domains.footer.pageobjects;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

import com.mng.robotest.tests.domains.base.PageBase;

public class PageMultimarcasShop extends PageBase implements PageFromFooter {
	
	private static final String XPATH_FOR_IDPAGE = "//title[text()[contains(.,'MANGO')]]";
	
	@Override
	public String getName() {
		return "Multimarcas Shop";
	}
	
	@Override
	public boolean isPageCorrectUntil(int seconds) {
		return state(Present, XPATH_FOR_IDPAGE).wait(seconds).check();
	}
}