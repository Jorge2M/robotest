package com.mng.robotest.testslegacy.pageobject.shop;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

import com.mng.robotest.tests.domains.base.PageBase;

public class PageRecADomic extends PageBase {

	private static final String XP_IS_PAGE_RECOGIDA ="//*[@data-testid[contains(.,'noPurchasesFound.page')]]";
	private static final String XP_NO_HAY_PEDIDOS = "//*[@data-testid[contains(.,'noPurchasesFound.page')]]";
	private static final String XP_IS_PAGE_RECOGIDA_PRO ="//*[@class[contains(.,'devoluciones_misPedidos')]]";
	private static final String XP_NO_HAY_PEDIDOS_PRO = "//*[@class[contains(.,'noOrders')]]";	

	public boolean isPage(int seconds) {
		return state(PRESENT, XP_IS_PAGE_RECOGIDA + " | " + XP_IS_PAGE_RECOGIDA_PRO).wait(seconds).check();
	}

	public boolean noHayPedidos() {
		return state(PRESENT, XP_NO_HAY_PEDIDOS + " | " + XP_NO_HAY_PEDIDOS_PRO).check();
	}
}
