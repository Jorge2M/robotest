package com.mng.robotest.testslegacy.pageobject.shop;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

import com.mng.robotest.tests.domains.base.PageBase;

public class PageRecADomic extends PageBase {

	private static final String XP_PAGE_NO_HAY_PEDIDOS = "//*[@data-testid[contains(.,'noPurchasesFound')]]";
	private static final String XP_PAGE_SI_HAY_PEDIDOS = "//micro-frontend[@id='myReturns']";

	public boolean isPage(int seconds) {
		return state(PRESENT, XP_PAGE_NO_HAY_PEDIDOS + " | " + XP_PAGE_SI_HAY_PEDIDOS).wait(seconds).check();
	}

	public boolean noHayPedidos() {
		return state(PRESENT, XP_PAGE_NO_HAY_PEDIDOS).check();
	}
}
