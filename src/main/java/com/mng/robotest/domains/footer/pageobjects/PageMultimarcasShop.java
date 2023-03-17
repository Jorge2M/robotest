package com.mng.robotest.domains.footer.pageobjects;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

import com.mng.robotest.domains.base.PageBase;

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
