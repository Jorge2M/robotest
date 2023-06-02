package com.mng.robotest.domains.ficha.pageobjects;



import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

import com.mng.robotest.domains.base.PageBase;

public class ModEnvioYdevolNew extends PageBase {

	private static final String XPATH_ASPA_FOR_CLOSE = "//div[@role='document']//*[@data-testid='modal.close.button']";
	
	public boolean isVisible(int seconds) {
		return state(Present, XPATH_ASPA_FOR_CLOSE).wait(seconds).check();
	}

	public void clickAspaForClose() {
		click(XPATH_ASPA_FOR_CLOSE).exec();
	}
}
