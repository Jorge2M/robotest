package com.mng.robotest.domains.footer.pageobjects;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

import com.mng.robotest.domains.transversal.PageBase;

public class PageMangoShoppingApp extends PageBase implements PageFromFooter {
	
	private static final String XPATH_FOR_IDPAGE = "//*[text()[contains(.,'MANGO SHOPPING APP')]]";
	
	@Override
	public String getName() {
		return "MANGO SHOPPING APP";
	}
	
	@Override
	public boolean isPageCorrectUntil(int seconds) {
		return state(Present, XPATH_FOR_IDPAGE).wait(seconds).check();
	}
}
