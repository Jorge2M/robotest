package com.mng.robotest.domains.footer.pageobjects;

import com.mng.robotest.domains.transversal.PageBase;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

public class PageMultimarcasOutlet extends PageBase implements PageFromFooter {
	
	private static final String XPATH_FOR_IDPAGE = "//title[text()[contains(.,'OUTLET')] or text()[contains(.,'Outlet')]]";
	
	@Override
	public String getName() {
		return "Multimarcas Outlet";
	}
	
	@Override
	public boolean isPageCorrectUntil(int maxSeconds) {
		return state(Present, XPATH_FOR_IDPAGE).wait(maxSeconds).check();
	}
}
