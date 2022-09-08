package com.mng.robotest.domains.footer.pageobjects;

import org.openqa.selenium.By;

import com.mng.robotest.domains.transversal.PageBase;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;


public class PageNotasPrensa extends PageBase implements PageFromFooter {
	
	private static final String XPATH_FOR_IDPAGE = "//*[text()[contains(.,'NOTAS DE PRENSA')] or text()[contains(.,'Notas de prensa')] or text()[contains(.,'PRESS RELEASES')]]";
	
	@Override
	public String getName() {
		return "Noticias en prensa";
	}
	
	@Override
	public boolean isPageCorrectUntil(int maxSeconds) {
		return state(Present, XPATH_FOR_IDPAGE).wait(maxSeconds).check();
	}
}
