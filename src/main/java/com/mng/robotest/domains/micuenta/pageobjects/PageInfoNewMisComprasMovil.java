package com.mng.robotest.domains.micuenta.pageobjects;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.TypeClick.*;

import com.mng.robotest.domains.base.PageBase;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

public class PageInfoNewMisComprasMovil extends PageBase {

	private static final String XPATH_BUTTON_TO_MIS_COMPRAS = "//div[@class[contains(.,'button')] and @id='goToMyPurchases']";
	
	public boolean isPage() {
		return state(Visible, XPATH_BUTTON_TO_MIS_COMPRAS).wait(2).check();
	}
	
	public boolean isVisibleButtonToMisCompras() {
		return state(Visible, XPATH_BUTTON_TO_MIS_COMPRAS).check();
	}
	
	public void clickButtonToMisCompras() {
		click(XPATH_BUTTON_TO_MIS_COMPRAS).exec();
		if (isVisibleButtonToMisCompras()) {
			click(XPATH_BUTTON_TO_MIS_COMPRAS).type(javascript).exec();
		}
	}
}
