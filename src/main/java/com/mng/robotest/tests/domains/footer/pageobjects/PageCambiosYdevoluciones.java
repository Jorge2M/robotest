package com.mng.robotest.tests.domains.footer.pageobjects;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

import com.mng.robotest.tests.domains.base.PageBase;

public class PageCambiosYdevoluciones extends PageBase implements PageFromFooter {
	
	private static final String XP_FOR_ID_PAGE = "//*[text()[contains(.,'Devoluciones, cambios y reembolsos')]]";
	
	@Override
	public String getName() {
		return "Cambios y devoluciones";
	}
	
	@Override
	public boolean isPageCorrectUntil(int seconds) {
		return state(Present, XP_FOR_ID_PAGE).wait(seconds).check();
	}
}
