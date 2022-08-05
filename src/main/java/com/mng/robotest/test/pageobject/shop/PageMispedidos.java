package com.mng.robotest.test.pageobject.shop;

import org.openqa.selenium.By;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;


public class PageMispedidos extends PageObjTM {

	private final static String XPATH_PANEL_PEDIDOS = "//div[@id[contains(.,'panelPedidos')]]";
	
	private String getXPathListaPedidosVacia() {
		return ("//tr[last()]//td[contains(.,'digo pedido')]");
	}

	public boolean isPage() {
		return (state(Present, By.xpath(XPATH_PANEL_PEDIDOS)).check());
	}

	public boolean elementContainsText(String text) {
		String xpath = "//*[text()[contains(.,'" + text.toUpperCase() + "')] or text()[contains(.,'" + text.toLowerCase() + "')]]";
		return (state(Present, By.xpath(xpath)).check());
	}

	public boolean listaPedidosVacia() {
		String xpath = getXPathListaPedidosVacia();
		return (state(Present, By.xpath(xpath)).check());
	}

}
