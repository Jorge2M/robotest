package com.mng.robotest.test.pageobject.shop.checkout.multibanco;

import com.mng.robotest.domains.transversal.PageBase;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

public class PageMultibancoEnProgreso extends PageBase {
	
	private static final String XPATH_CABECERA_EN_PROCESO = "//h2[text()[contains(.,'Pagamento em progresso')]]";
	private static final String XPATH_BUTTON_NEXT_STEP = "//input[@id='mainSubmit' and @type='submit']";
	
	public boolean isPageUntil(int seconds) {
		return state(Present, XPATH_CABECERA_EN_PROCESO).wait(seconds).check();
	}
	
	public boolean isButonNextStep() {
		return state(Present, XPATH_BUTTON_NEXT_STEP).check();
	}

	public void clickButtonNextStep() {
		click(XPATH_BUTTON_NEXT_STEP).exec();
	}
}
