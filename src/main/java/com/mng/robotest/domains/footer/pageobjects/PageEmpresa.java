package com.mng.robotest.domains.footer.pageobjects;

import com.mng.robotest.domains.transversal.PageBase;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

public class PageEmpresa extends PageBase implements PageFromFooter {
	
	private static final String XPATH_FOR_ID_PAGE_NEW = "//img[@src[contains(.,'empresa-mango')]]";
	
	@Override
	public String getName() {
		return "Datos de empresa";
	}
	
	@Override
	public boolean isPageCorrectUntil(int seconds) {
		return state(Present, XPATH_FOR_ID_PAGE_NEW).wait(seconds).check();
	}
}
