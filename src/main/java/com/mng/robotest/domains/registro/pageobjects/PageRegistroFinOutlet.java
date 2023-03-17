package com.mng.robotest.domains.registro.pageobjects;



import static com.github.jorge2m.testmaker.service.webdriver.pageobject.TypeClick.*;

import com.mng.robotest.domains.base.PageBase;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;


public class PageRegistroFinOutlet extends PageBase {

	private static final String XPATH_BUTTON_IR_SHOPPING = "//div[@class[contains(.,'ir-de-shopping')]]/input[@type='submit']";
	
	public boolean isPageUntil(int seconds) {
		return state(Present, XPATH_BUTTON_IR_SHOPPING).wait(seconds).check();
	}
	
	public void clickIrDeShopping() {
		waitLoadPage(); //Para evitar StaleElement Exception
		click(XPATH_BUTTON_IR_SHOPPING).type(javascript).exec();
		if (isVisibleButtonIrDeShopping()) {
			click(XPATH_BUTTON_IR_SHOPPING).exec();
		}
	}
	
	public boolean isVisibleButtonIrDeShopping() {
		return state(Visible, XPATH_BUTTON_IR_SHOPPING).check();
	}
}
