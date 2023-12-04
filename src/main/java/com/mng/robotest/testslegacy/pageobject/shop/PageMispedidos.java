package com.mng.robotest.testslegacy.pageobject.shop;

import org.openqa.selenium.By;

import com.mng.robotest.tests.domains.base.PageBase;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;


public class PageMispedidos extends PageBase {

	private static final String XP_PANEL_PEDIDOS = "//div[@id[contains(.,'panelPedidos')]]";
	
	private String getXPathListaPedidosVacia() {
		return ("//tr[last()]//td[contains(.,'digo pedido')]");
	}

	public boolean isPage() {
		return (state(PRESENT, By.xpath(XP_PANEL_PEDIDOS)).check());
	}

	public boolean elementContainsText(String text) {
		String xpath = "//*[text()[contains(.,'" + text.toUpperCase() + "')] or text()[contains(.,'" + text.toLowerCase() + "')]]";
		return state(PRESENT, xpath).check();
	}

	public boolean listaPedidosVacia() {
		return state(PRESENT, getXPathListaPedidosVacia()).check();
	}

}
