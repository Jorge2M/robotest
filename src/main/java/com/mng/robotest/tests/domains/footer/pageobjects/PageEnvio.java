package com.mng.robotest.tests.domains.footer.pageobjects;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

import com.mng.robotest.tests.domains.base.PageBase;

public class PageEnvio extends PageBase implements PageFromFooter {
	
	@Override
	public String getName() {
		return "Tipos de envío y plazos de entrega";
	}
	
	@Override
	public boolean isPageCorrectUntil(int seconds) {
		return state(PRESENT, "//h1[text()[contains(.,'tipos de envío')]]").wait(seconds).check();
	}
}
