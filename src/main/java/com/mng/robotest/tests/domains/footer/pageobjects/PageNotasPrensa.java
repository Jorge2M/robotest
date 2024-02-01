package com.mng.robotest.tests.domains.footer.pageobjects;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

import com.mng.robotest.tests.domains.base.PageBase;

public class PageNotasPrensa extends PageBase implements PageFromFooter {
	
	@Override
	public String getName() {
		return "Mango pressroom";
	}
	
	@Override
	public boolean isPageCorrectUntil(int seconds) {
		return state(PRESENT, "//section[@class='mango-prensa-h1']").wait(seconds).check();
	}
}
