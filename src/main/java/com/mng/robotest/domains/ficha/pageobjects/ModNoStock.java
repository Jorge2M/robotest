package com.mng.robotest.domains.ficha.pageobjects;

import com.mng.robotest.domains.transversal.PageBase;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

public class ModNoStock extends PageBase {

	private static final String XPATH_MODAL_NO_STOCK = "//div[@class='modalNoStock show']";
	
	public boolean isModalNoStockVisibleFichaNew(int maxSeconds) {
		return (state(Visible, XPATH_MODAL_NO_STOCK).wait(maxSeconds).check());
	}
	
}
