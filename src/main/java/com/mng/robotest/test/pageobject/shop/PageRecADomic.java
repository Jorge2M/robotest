package com.mng.robotest.test.pageobject.shop;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

import com.mng.robotest.domains.transversal.PageBase;

public class PageRecADomic extends PageBase {

	private static final String XPATH_IS_PAGE_RECOGIDA ="//h1[text()[contains(.,'RECOGIDA A DOMICILIO')]]";
	private static final String XPATH_TABLE_DEVOLUCIONES = "//table[@class[contains(.,'devoluciones_table')]]";
	private static final String XPATH_NO_HAY_PEDIDOS = "//p[text()[contains(.,'no tienes ningún pedido')]]";

	public boolean isPage() {
		return state(Present, XPATH_IS_PAGE_RECOGIDA).check();
	}

	public boolean isTableDevoluciones() {
		return state(Present, XPATH_TABLE_DEVOLUCIONES).check();
	}

	public boolean hayPedidos() {
		return state(Present, XPATH_NO_HAY_PEDIDOS).check();
	}
}
