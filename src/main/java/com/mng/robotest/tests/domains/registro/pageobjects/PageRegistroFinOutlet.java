package com.mng.robotest.tests.domains.registro.pageobjects;



import static com.github.jorge2m.testmaker.service.webdriver.pageobject.TypeClick.*;

import com.mng.robotest.tests.domains.base.PageBase;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;


public class PageRegistroFinOutlet extends PageBase {

	private static final String XP_BUTTON_IR_SHOPPING = "//div[@class[contains(.,'ir-de-shopping')]]/input[@type='submit']";
	
	public boolean isPageUntil(int seconds) {
		return state(Present, XP_BUTTON_IR_SHOPPING).wait(seconds).check();
	}
	
	public void clickIrDeShopping() {
		waitLoadPage(); //Para evitar StaleElement Exception
		click(XP_BUTTON_IR_SHOPPING).type(javascript).exec();
		if (isVisibleButtonIrDeShopping()) {
			click(XP_BUTTON_IR_SHOPPING).exec();
		}
	}
	
	public boolean isVisibleButtonIrDeShopping() {
		return state(Visible, XP_BUTTON_IR_SHOPPING).check();
	}
}
