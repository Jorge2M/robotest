package com.mng.robotest.domains.ficha.pageobjects;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

import com.mng.robotest.domains.base.PageBase;

public class ModNoStock extends PageBase {

	private static final String XPATH_MODAL_NO_STOCK = "//div[@class='modalNoStock show']";
	
	public boolean isModalNoStockVisibleFichaNew(int seconds) {
		return (state(Visible, XPATH_MODAL_NO_STOCK).wait(seconds).check());
	}
	
}
