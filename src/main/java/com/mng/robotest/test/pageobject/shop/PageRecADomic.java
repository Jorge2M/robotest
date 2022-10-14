package com.mng.robotest.test.pageobject.shop;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

import com.mng.robotest.domains.transversal.PageBase;

public class PageRecADomic extends PageBase {

	private static final String XPATH_IS_PAGE_RECOGIDA ="//micro-frontend[@id='myReturns']";
	private static final String XPATH_NO_HAY_PEDIDOS = "//*[@data-testid[contains(.,'noPurchasesFound')]]";

	public boolean isPage() {
		return state(Present, XPATH_IS_PAGE_RECOGIDA).check();
	}

	public boolean noHayPedidos() {
		return state(Present, XPATH_NO_HAY_PEDIDOS).check();
	}
}
