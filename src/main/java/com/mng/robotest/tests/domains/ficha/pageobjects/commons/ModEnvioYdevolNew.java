package com.mng.robotest.tests.domains.ficha.pageobjects.commons;



import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

import com.mng.robotest.tests.domains.base.PageBase;

public class ModEnvioYdevolNew extends PageBase {

	private static final String XP_ASPA_FOR_CLOSE = "//div[@role='document']//*[@data-testid='modal.close.button']";
	
	public boolean isVisible(int seconds) {
		return state(PRESENT, XP_ASPA_FOR_CLOSE).wait(seconds).check();
	}

	public void clickAspaForClose() {
		click(XP_ASPA_FOR_CLOSE).exec();
	}
}
