package com.mng.robotest.tests.domains.footer.pageobjects;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

import com.mng.robotest.tests.domains.base.PageBase;

public class PageNotasPrensa extends PageBase implements PageFromFooter {
	
	private static final String XPATH_FOR_IDPAGE = "//*[text()[contains(.,'NOTAS DE PRENSA')] or text()[contains(.,'Notas de prensa')] or text()[contains(.,'PRESS RELEASES')]]";
	
	@Override
	public String getName() {
		return "Noticias en prensa";
	}
	
	@Override
	public boolean isPageCorrectUntil(int seconds) {
		return state(Present, XPATH_FOR_IDPAGE).wait(seconds).check();
	}
}
