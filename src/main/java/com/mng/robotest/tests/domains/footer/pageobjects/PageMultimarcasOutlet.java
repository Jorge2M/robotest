package com.mng.robotest.tests.domains.footer.pageobjects;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

import com.mng.robotest.tests.domains.base.PageBase;

public class PageMultimarcasOutlet extends PageBase implements PageFromFooter {
	
	private static final String XP_FOR_IDPAGE = "//title[text()[contains(.,'OUTLET')] or text()[contains(.,'Outlet')]]";
	
	@Override
	public String getName() {
		return "Multimarcas Outlet";
	}
	
	@Override
	public boolean isPageCorrectUntil(int seconds) {
		return state(PRESENT, XP_FOR_IDPAGE).wait(seconds).check();
	}
}
